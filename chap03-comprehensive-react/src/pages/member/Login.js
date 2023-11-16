import {useNavigate} from "react-router-dom";
import LoginForm from "../../components/form/LoginForm";

function Login() {

    const navigate = useNavigate();

    const onClickSignupHandler = () => {
        navigate('/member/signup');
    }

    return (
        <>
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