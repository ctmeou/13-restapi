import './style.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layouts/Layout";
import Main from "./pages/products/Main";
import CategoryMain from "./pages/products/CategoryMain";
import Signup from "./pages/member/Signup";
import ProductDetail from "./pages/products/ProductDetail";
import SearchMain from "./pages/products/SearchMain";
import Login from "./pages/member/Login";
import ProtectedRoute from "./components/router/ProtectedRoute";
import Error from "./pages/error/Error";

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
          </Route>
            <Route path="/member"> {/*로그인 여부와 상관있는 UI는 checking할 수 있게 설정해야 한다.*/}
                <Route path="signup" element={ <ProtectedRoute loginCheck={ false }><Signup/></ProtectedRoute> }/>
                <Route path="login" element={  <ProtectedRoute loginCheck={ false }><Login/></ProtectedRoute> }/>
            </Route>
            <Route path="*" element={ <Error/> }/> {/*정의한 요청 외에 다른 요청이 올 경우 error 페이지를 보여준다.*/}
        </Routes>
      </BrowserRouter>
  );
}

export default App;
