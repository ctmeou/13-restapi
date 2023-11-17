import {NavLink} from "react-router-dom";

function MyPageNavbar() {

    return (
        <div className="mypage-navbar-div">
            <ul className="mypage-navbar-ul">
                <li><NavLink to="/">메인</NavLink></li>
                <li><NavLink to="/member/mypage/profile">회원 정보</NavLink></li>
                <li><NavLink to="/member/mypage/payment">결제 정보</NavLink></li>
            </ul>
        </div>
    );

}

export default MyPageNavbar;