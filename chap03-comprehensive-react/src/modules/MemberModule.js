
/* 초기값 */
import {createActions, handleAction, handleActions} from "redux-actions";

const initialState = {};

/* 액션 타입 */
const SIGNUP_SUCCESS = 'member/SIGNUP_SUCCESS';
const SIGNUP_FAILURE = 'member/SIGNUP_FAILURE';
const LOGIN_SUCCESS = 'member/LOGIN_SUCCESS';
const LOGIN_FAILURE = 'member/LOGIN_FAILURE';
const GET_PROFILE = 'member/GET_PROFILE';

/* 액션 함수 */ //API적으로 통신을 했을 때 통신 결과가 있을 경우 store에 저장 시 dispatch에 action에 객체를 보내는데 객체를 반환하는 역할
export const { member : { signupSuccess, signupFailure, loginSuccess, loginFailure, getProfile } } = createActions({ //payload를 원하는 시그널로 생성
    [SIGNUP_SUCCESS] : () => ({ signupSuccess : true }), //두 개의 액션 함수가 MEMBERAPICALL에서 호출된다.
    [SIGNUP_FAILURE] : () => ({ signupSuccess : false }),
    [LOGIN_SUCCESS] : () => ({ loginSuccess : true }),
    [LOGIN_FAILURE] : () => ({ loginSuccess : false }),
    [GET_PROFILE] : (result) => ({ profileInfo : result.data }) //조회 결과가 넘어오고 data를 꺼내 key 값으로 profileInfo payload로 넘긴다.
});

/* 리듀서 함수 */
const memberReducer = handleActions({
    [SIGNUP_SUCCESS] : (state, { payload }) => payload, //action 객체에서 payload만 꺼내서 사용한다.(payload에는 signupSuccess : true나 false에 대한 값이 들어있고 그 값을 store에 저장한다.)
    [SIGNUP_FAILURE] : (state, { payload }) => payload,
    [LOGIN_SUCCESS] : (state, { payload }) => payload,
    [LOGIN_FAILURE] : (state, { payload }) => payload,
    [GET_PROFILE] : (state, { payload }) => payload,
}, initialState); //리듀서 함수는 초기값을 가지는 형태를 가지는 것이 좋다.

//rootReducer를 이용하고 있기 떄문에 rootReducer에 추가해주면 store를 구성하는 리듀서가 된다.
export default memberReducer;