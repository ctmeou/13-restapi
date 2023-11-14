import {request} from "./Api";
import {getProducts} from "../modules/ProductModule";
                                        //꺼낼 때도 객체 안에서 꺼내야 한다.
export const callProductListAPI = ({ currentPage = 1 }) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/products?page=${ currentPage }`);
        console.log('callProductListAPI result : ', result);

        if (result.status === 200) {
            dispatch(getProducts(result)); //action 객체를 생성하기 위해 action 함수 호출
        }

    }

};

export const callProductCategoryListAPI = ({ categoryCode, currentPage = 1 }) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/products/categories/${ categoryCode }?page=${ currentPage }`);
        console.log('callProductCategoryListAPI result : ', result);

        if (result.status === 200) {
            dispatch(getProducts(result)); //action 객체를 생성하기 위해 action 함수 호출
        }

    }

};