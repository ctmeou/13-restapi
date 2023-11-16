import {request} from "./Api";
import {loginFailure, loginSuccess, signupFailure, signupSuccess} from "../modules/MemberModule";
import {toast} from "react-toastify";
import {saveToken} from "../utils/TokenUtils";

export const callSignupAPI = ({ signupRequest }) => {

    return async (dispatch, getState) => {
        const result = await request(
            'POST',
            '/member/signup',
            { 'Content-Type' : 'application/json' },
            JSON.stringify(signupRequest) //자바스크립트를 JSON 문자열로 변환해준다.
        );

        console.log('callSignupAPI result : ', result);

        //성공 실패 여부 store에 저장 후 구독하면서 handling할 것이고, store에 저장하려면 member module를 추가 생성 후 관리한다.
        if (result?.status === 201) { //?는 undifined일 경우 안의 속성을 읽어오지 않고 dispatch(signupFailure())
            dispatch(signupSuccess()) //함수 호출 시 만들어지는 액션 객체가 리듀서로 전달한다.
        } else {
            dispatch(signupFailure())
            toast.warning("회원 가입에 실패했습니다. 다시 시도해주세요 ♡")
        }
    }

}

export const callLoginAPI = ({ loginRequest }) => {

    return async (dispatch, getState) => {
        const result = await request(
            'POST',
            '/member/login',
            { 'Content-Type' : 'application/json' },
            JSON.stringify(loginRequest) //자바스크립트를 JSON 문자열로 변환해준다.
        );

        console.log('callLoginAPI result : ', result);

        if (result?.status === 200) {
            saveToken(result.headers);
            dispatch(loginSuccess());
        } else {
            dispatch(loginFailure());
        }

    }

}