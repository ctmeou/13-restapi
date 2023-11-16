import SignupForm from "../../components/form/SignupForm";
import {ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css"
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

function Signup() {

    const navigate = useNavigate();

    //MemberModule의 액션함수의 값을 받아 감지한다.
    const { signupSuccess } = useSelector(state => state.memberReducer);

    useEffect(() => {
        if (signupSuccess === true) {
            navigate('/member/login');
        }
    }, [signupSuccess]);

    return (
        <>
            <ToastContainer hideProgressBar={ true } position="top-center"/> {/*alert 설정*/}
            <div className="background-div">
                <div className="signup-div">
                    <SignupForm/> {/*합성*/}
                </div>
            </div>
        </>
    );

}

export default Signup;