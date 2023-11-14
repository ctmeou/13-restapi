import {NavLink} from "react-router-dom";

function Navbar() {

    return(
        <div className="navbar-div">
            <ul className="nav-list-ul">
                <li><NavLink to="/">모든 음식</NavLink></li>
                <li><NavLink to="/product/categories/1">식사</NavLink></li>
                <li><NavLink to="/product/categories/2">디저트</NavLink></li>
                <li><NavLink to="/product/categories/3">음료</NavLink></li>
                { false && <li><NavLink to="/product-management">상품등록</NavLink></li> } {/*admin 계정으로만 등록할 수 있따. 현재 false로 숨겨놓은 상태*/}
            </ul>
        </div>
    );

}

export default Navbar;