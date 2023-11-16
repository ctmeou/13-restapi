import {useNavigate} from "react-router-dom";
import LoginForm from "../../components/form/LoginForm";
import {useSelector} from "react-redux";
import {useEffect} from "react";
import {toast, ToastContainer} from "react-toastify";

function Login() {

    const navigate = useNavigate();
    const { loginSuccess } = useSelector(state => state.memberReducer);

    //해당 값을 감지하고 있다가 변화가 생기면
    useEffect(() => {
        if (loginSuccess === true) {
            window.location.replace("/") //온전한 새로고침, 모든 화면을 reload(일부가 아닌 전체) -> 리덕스 안의 값은 전부 날라간다.
        } else if (loginSuccess === false) {
            toast.warning('로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요. 😭💜');
        }
    }, [loginSuccess]);

    const onClickSignupHandler = () => {
        navigate('/member/signup');
    }

    return (
        <>
            <ToastContainer hideProgressBar={ true } position="top-center"/>
            <div className="background-div">
                <div className="login-div">
                    <LoginForm/>
                    <button
                        onClick={ onClickSignupHandler }
                    >
                        회원가입
                    </button>

                </div>
            </div>
        </>
    );

}

export default Login;