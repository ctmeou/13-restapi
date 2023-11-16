import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {useDispatch} from "react-redux";
import {callSignupAPI} from "../../apis/MemberAPICalls";

function SignupForm() {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [form, setForm] = useState({});

    const onChangeHandler = e => {
        setForm({
            ...form,
            [e.target.name] : e.target.value
        }); //기존의 값을 유지하면서 변화가 발생했을 때 변화가 발생한 입력 양식을 name 값으로 해서 덮어쓴다.
    }

    const onClickSignupHandler = () => {
        dispatch(callSignupAPI({ signupRequest : form })); //signupRequest로 현재 관리되고 있는 form 형태를 보낸다.
    }

    const onClickBackHandler = () => {
        navigate('/'); //메인으로 이동
    }

    return (
        <>
            <h1>회원가입</h1>
            <input
                type="text"
                name="memberId"
                placeholder="아이디"
                onChange={ onChangeHandler }
            />
            <input
                type="password"
                name="memberPassword"
                placeholder="비밀번호"
                onChange={ onChangeHandler }
            />
            <input
                type="text"
                name="memberName"
                placeholder="이름"
                onChange={ onChangeHandler }
            />
            <input
                type="text"
                name="memberEmail"
                placeholder="이메일"
                onChange={ onChangeHandler }
            />
            <button //위의 양식이 유효할 경우 회원가입 진행될 수 있도록
                onClick={ onClickSignupHandler }
            >
                회원가입
            </button>
            <button
                onClick={ onClickBackHandler }
            >
                메인
            </button>
        </>
    )

}

export default SignupForm;