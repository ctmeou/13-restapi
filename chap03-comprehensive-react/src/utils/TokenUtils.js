//토큰과 관련된 함수 작성
import {jwtDecode} from "jwt-decode";

const BEARER = 'Bearer ';

export const saveToken = (headers) => {
    localStorage.setItem("access-token", headers['access-token']);
    localStorage.setItem("refresh-token", headers['refresh-token']);
}

export const removeToken = () => {
    localStorage.removeItem("access-token");
    localStorage.removeItem("refresh-token");
}

//로그인 판단 근거, access-token과 refresh-token이 반환되었을 때 없으면 false이고 존재하면 true, 두 개의 값이 존재하는지 확인
const getAccessToken = () => window.localStorage.getItem('access-token');
const getRefreshToken = () => window.localStorage.getItem('refresh-token');
const getDecodeAccessToken = () => {
    return getAccessToken() && jwtDecode(getAccessToken());
} //accessToken이 있을 경우 진행하고 디코딩된 결과를 만들어서 함수로 반환한다.
const getDecodeRefreshToken = () => {
    return getRefreshToken() && jwtDecode(getRefreshToken());
}

//원하는 문자열이 만들어진다. token의 값을 header에 보낼 경우 호출된다.
export const getAccessTokenHeader = () => BEARER + getAccessToken();
export const getRefreshTokenHeader = () => BEARER + getRefreshToken();

//isLogin은 화면상에서 로그인했을 경우의 UI와 로그인하지 않았을 때의 UI를 보여주기 위한 판단을 하기 위해 사용
export const isLogin = () => {
                                                       //getDecodeRefreshToken은 초 기준이기 때문에 1000초로 적었고 현재 시간이 만료 시간보다 작아야 한다.(작은 경우 UI상에서 로그인되어 있다.)
    return getAccessToken() && getRefreshToken() && (Date.now() < getDecodeRefreshToken().exp * 1000);

} //두 개의 토큰이 있을 경우 true로 반환한다.

//AccessToken을 기반으로 admin인지 확인
export const isAdmin = () => {
    return isLogin() && (getDecodeAccessToken().memberRole === 'ROLE_ADMIN');
}