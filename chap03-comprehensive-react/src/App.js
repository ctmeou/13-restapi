import './style.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layouts/Layout";
import Main from "./pages/products/Main";

function App() {
  return (
      <BrowserRouter>
        <Routes>            {/*레이아웃 엘리먼트 생성-header, footer, navbar*/}
          <Route path="/" element={ <Layout/> }>
              <Route index element={ <Main/> }/>
          </Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
