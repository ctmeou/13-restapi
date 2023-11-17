import axios from "axios";
import {getAccessTokenHeader, getRefreshTokenHeader, saveToken} from "../utils/TokenUtils";

const SERVER_IP = `${ process.env.REACT_APP_RESTAPI_SERVER_IP }`;
const SERVER_PORT = `${ process.env.REACT_APP_RESTAPI_SERVER_PORT }`;
const DEFAULT_URL = `http://${ SERVER_IP }:${ SERVER_PORT }`;

/* 인증이 불필요한 기능을 요청할 때 사용하는 메소드 */
//`${ DEFAULT_URL }${ url }` : 기본 주소 + 추가 주소
export const request = async (method, url, headers, data) => {

    return await axios({ //axios에게 객체를 전달해서 하고 싶은 요청을 전달했다면
        method,
        url : `${ DEFAULT_URL }${ url }`,
        headers,
        data
    })
        .catch(error => console.log(error));

}

/* 인증이 필요한 기능을 요청할 때 사용하는 객체 */
export const authRequest = axios.create({ //미리 axios.create를 만들어놓고 get 방식 요청을 할 경우, post 방식 요청을 할 경우의 작성 방식이 있다. 미리 요청해놓고
                                                                        //authRequest.get(url), authRequest.post(url, data) 호출을 하면 된다.
   baseURL : DEFAULT_URL

});

authRequest.interceptors.request.use((config) => {
    config.headers['Access-Token'] = getAccessTokenHeader(); //header에 'Access-Token'을 항상 설정해두고 작성 시 Bearer 을 작성해야 하기 때문에 TokenUtils에 미리 선언해둔다.
    return config;
});

//첫 번째 매개변수 정상 수행했을 경우, 두 번째 매개변수 실패했을 경우
authRequest.interceptors.response.use(
    /* 첫 번째 인자로 사용되는 콜백 함수는 정상 수행 시 동작이므로 별도의 동작 없이 진행하도록 한다. */
    (response) => {
        return response;
    },
    /* 두 번째 인자로 사용되는 콜백 함수는 오류 발생 시 동작이므로 로직을 작성한다. */
    async (error) => {

        console.log("error : ", error);

        const { //구조 분해 할당
            config,
            response : { status }
        } = error;

        if (status === 401) { //인증 실패 시 401
            const originRequest = config;
            // refresh token 전달하여 토큰 재발급 요청
            const response = await postRefreshToken();

            console.log("response : ", response);

            if (response.status === 200) {
                // 토큰 재발급에 성공했을 때
                saveToken(response.headers); //로그인 시 header에 token 저장
                // 실패했던 요청을 다시 요청 //요청이 무엇인지 알아야 한다. config를 이용해서 처리
                originRequest.headers['Access-Token'] = getAccessTokenHeader(); //재발급에 성공한 token에 setting을 다시 한다.
                return axios(originRequest); //axios 객체로 originRequest(필요한 요청 정보가 들어있고 url이나 다른 것들은 그대로 둔 채로 accessToken만 변경해서 요청)을 한다.
            }
        }

        return Promise.reject(error); //if문을 타지 않는 경우 발생한 error를 그대로 보여준다.
    });

/* refresh token 전달하여 토큰 재발급 요청하는 api */
export async function postRefreshToken() {

    return await request(
        'POST',
        "/api/v1/refresh-token", //url은 인증 필터를 통과할 수 있으면 된다.(인증 필터를 통과해야 refresh token을 넘길 수 있다.) header 안에 있으면 재발급이 가능하다.
        { 'Refresh-Token' : getRefreshTokenHeader() }
    );

}