import {authRequest} from "./Api";
import {toast} from "react-toastify";
import {getOrders, postSuccess} from "../modules/OrderModule";
import {getReview, getReviews} from "../modules/ReviewModule";

export const callReviewsAPI = ({ productCode, currentPage }) => { //어떤 상품에 대해 여러 개의 후기를 가져오기 때문에 상품 코드 또한 받아온다.(특정 상품 기준으로 리뷰를 페이징해온다.)

    return async (dispatch, getState) => {

        //인증이 필요할 때 authRequest, authRequest는 axios 객체이기 때문에 메소드를 사용해준다.
        const result = await authRequest.get(`/api/v1/reviews/product/${ productCode }?page=${ currentPage }`,
            {
                headers : { //세 번째 인자로 type 넘겨준다.
                    'Content-Type' : 'application/json'
                }
            }).catch(e => {
            console.log(e);
        });

        console.log('callReviewsAPI result : ', result); //잘 됐을 경우 결과 출력

        if (result?.status === 200) {
            dispatch(getReviews(result));
        }

    }

}

export const callReviewAPI = ({ reviewCode }) => {

    return async (dispatch, getState) => {

        const result = await authRequest.get(`/api/v1/reviews/${ reviewCode }`,
            {
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).catch(e => {
            console.log(e);
        });

        console.log('callReviewAPI result : ', result); //잘 됐을 경우 결과 출력

        if (result?.status === 200) {
            dispatch(getReview(result));
        }

    }

}