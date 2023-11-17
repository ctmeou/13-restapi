import {isLogin} from "../../utils/TokenUtils";
import {Navigate} from "react-router-dom";

function ProtectedRoute({ loginCheck, children }) {

    //loginCheck에서 true, false를 보내면 각각의 상태에서 볼 수 있는 기능을 설정한다.
    if (loginCheck) {
        /* 로그인해야만 볼 수 있는 기능(마이페이지) */
        //App.js에서 ProtectedRoute를 통해서 판단을 한다.
        //로그인하지 않았을 경우 children을 렌더링하고, 로그인했을 경우 root로 가게 한다.
        return isLogin() ? children : <Navigate to="/member/login"/>
    } else {
        /* 로그인하면 볼 수 없는 기능(로그인, 회원가입) */
        return !isLogin() ? children : <Navigate to="/"/>
    }

}

export default ProtectedRoute;