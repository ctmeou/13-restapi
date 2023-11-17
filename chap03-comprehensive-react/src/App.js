import './style.css';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import Layout from "./layouts/Layout";
import Main from "./pages/products/Main";
import CategoryMain from "./pages/products/CategoryMain";
import Signup from "./pages/member/Signup";
import ProductDetail from "./pages/products/ProductDetail";
import SearchMain from "./pages/products/SearchMain";
import Login from "./pages/member/Login";
import ProtectedRoute from "./components/router/ProtectedRoute";
import Error from "./pages/error/Error";
import MyPageLayout from "./layouts/MyPageLayout";
import Profile from "./pages/member/Profile";
import ProductManagement from "./pages/admin/ProductManagement";
import ProductRegist from "./pages/admin/ProductRegist";
import ProductModify from "./pages/admin/ProductModify";

function App() {
  return (
      <BrowserRouter>
        <Routes>            {/*레이아웃 엘리먼트 생성-header, footer, navbar*/}
          <Route path="/" element={ <Layout/> }>
              <Route index element={ <Main/> }/>
              <Route path="product"> {/*로그인 여부와 상관없이 보여줄 수 있지만*/}
                  <Route path="categories/:categoryCode" element={ <CategoryMain/> }/>
                  <Route path="search" element={ <SearchMain/> }/>
                  <Route path=":productCode" element={ <ProductDetail/> }/>
              </Route>
              <Route
                  path="product-management"
                  element={
                  <ProtectedRoute authCheck={ true }>
                      <ProductManagement/>
                  </ProtectedRoute>
                  }
              />
              <Route
                  path="product-regist"
                  element={
                      <ProtectedRoute authCheck={ true }>
                          <ProductRegist/> {/*admin으로 로그인한 계정만 이용할 수 있으므로 어스체크*/}
                      </ProtectedRoute>
                  }
              />
              <Route
                  path="product-modify/:productCode"
                  element={
                    <ProtectedRoute authCheck={ true }>
                        <ProductModify/>
                    </ProtectedRoute>
                  }
              />
          </Route>
            <Route path="/member"> {/*로그인 여부와 상관있는 UI는 checking할 수 있게 설정해야 한다.*/}
                <Route path="signup" element={ <ProtectedRoute loginCheck={ false }><Signup/></ProtectedRoute> }/>
                <Route path="login" element={  <ProtectedRoute loginCheck={ false }><Login/></ProtectedRoute> }/>
                <Route path="mypage" element={ <ProtectedRoute loginCheck={ true }><MyPageLayout/></ProtectedRoute> }>
                    <Route index element={ <Navigate to="/member/mypage/profile" replace/> }/> {/*현재 프로필은 /member/mypage/profile 주소인데 member/mypage로만 검색해도 프로필 페이지가 뜰 수 있게 설정*/}
                    <Route path="profile" element={ <Profile/> }/>
                </Route>
            </Route>
            <Route path="*" element={ <Error/> }/> {/*정의한 요청 외에 다른 요청이 올 경우 error 페이지를 보여준다.*/}
        </Routes>
      </BrowserRouter>
  );
}

export default App;
