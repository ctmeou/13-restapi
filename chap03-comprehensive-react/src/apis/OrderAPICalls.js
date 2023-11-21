import {authRequest} from "./Api";
import {toast} from "react-toastify";
import {getOrders, postSuccess} from "../modules/OrderModule";

export const callOrderRegistAPI = ({ registRequest }) => { //callOrderRegistAPI 호출 시 넘어오는 값을 registRequest

    return async (dispatch, getState) => {

        //인증이 필요할 때 authRequest, authRequest는 axios 객체이기 때문에 메소드를 사용해준다.
        const result = await authRequest.post('/api/v1/order', JSON.stringify(registRequest), //현재 registRequest는 자바스크립트의 객체이기 때문에 JSON.stringify해서 JSON 문자열로 변경해서 넘겨줘야 한다.
            {
                headers : { //세 번째 인자로 type 넘겨준다.
                    'Content-Type' : 'application/json'
                }
            }).catch(e => { //error인 경우 catch
                if (e.response.status === 400) {
                    toast.error("주문 불가 상품입니다. 😿🙏🏻");
                } else if (e.response.status === 409) {
                    toast.error("재고 부족으로 상품 구매가 불가합니다. 😿🙏🏻");
                }
        });

        //error가 나지 않았을 경우의 응답 값
        console.log('callOrderRegistAPI result : ', result);

        if (result?.status === 201) {
            dispatch(postSuccess());
        }

    }

}

export const callOrdersAPI = ({ currentPage }) => { //페이지 정보 필요

    return async (dispatch, getState) => {

        const result = await authRequest.get(`/api/v1/order?page=${ currentPage }`,
            {
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).catch(e => {
            console.log(e); //오류 발생 시 console에서 확인
        });

        //error가 나지 않았을 경우의 응답 값
        console.log('callOrdersAPI result : ', result);

        if (result?.status === 200) { //수행이 잘 되었다면 200번 코드
            dispatch(getOrders(result)); //저장된 결과 값을 넘겨준다.
        }

    }

}