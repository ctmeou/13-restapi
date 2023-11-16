import {useState} from "react";
import {useDispatch} from "react-redux";
import {callLoginAPI} from "../../apis/MemberAPICalls";

function LoginForm() {

    const dispatch = useDispatch();
    const [form, setForm] = useState({});

    const onChangeHandler = e => {
        setForm({
            ...form,
            [e.target.name] : e.target.value
        });
    }

    const onClickHandler = () => {
        dispatch(callLoginAPI({ loginRequest : form }));
    }

    return (
        <>
            <h1>로그인</h1>
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
            <button
                onClick={ onClickHandler }
            >
                로그인
            </button>
        </>
    );

}

export default LoginForm;