import './style.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layouts/Layout";
import Main from "./pages/products/Main";
import CategoryMain from "./pages/products/CategoryMain";
import Signup from "./pages/member/Signup";
import ProductDetail from "./pages/products/ProductDetail";
import SearchMain from "./pages/products/SearchMain";
import Login from "./pages/member/Login";

function App() {
  return (
      <BrowserRouter>
        <Routes>            {/*레이아웃 엘리먼트 생성-header, footer, navbar*/}
          <Route path="/" element={ <Layout/> }>
              <Route index element={ <Main/> }/>
              <Route path="product">
                  <Route path="categories/:categoryCode" element={ <CategoryMain/> }/>
                  <Route path="search" element={ <SearchMain/> }/>
                  <Route path=":productCode" element={ <ProductDetail/> }/>
              </Route>
          </Route>
            <Route path="/member">
                <Route path="signup" element={ <Signup/> }/>
                <Route path="login" element={ <Login/> }/>
            </Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
