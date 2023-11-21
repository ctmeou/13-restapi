import {authRequest, request} from "./Api";
import {
    getAdminProducts,
    getAdminProduct,
    getProduct,
    getProducts,
    postSuccess,
    putSuccess
} from "../modules/ProductModule";
import {toast} from "react-toastify";
                                        //꺼낼 때도 객체 안에서 꺼내야 한다.
export const callProductListAPI = ({ currentPage = 1 }) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/api/v1/products?page=${ currentPage }`);
        console.log('callProductListAPI result : ', result);

        if (result.status === 200) {
            dispatch(getProducts(result)); //action 객체를 생성하기 위해 action 함수 호출
        }

    }

};

//CategoryMain에서 dispatch 요청 관련
export const callProductCategoryListAPI = ({ categoryCode, currentPage = 1 }) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/api/v1/products/categories/${ categoryCode }?page=${ currentPage }`);
        console.log('callProductCategoryListAPI result : ', result);

        if (result.status === 200) {
            dispatch(getProducts(result)); //action 객체를 생성하기 위해 action 함수 호출
        }

    }

};

export const callProductSearchListAPI = ({ productName, currentPage = 1 }) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/api/v1/products/search?productName=${ productName }&page=${ currentPage }`);
        console.log('callProductSearchListAPI result : ', result);

        if (result.status === 200) { //200번이면 저장한다.
            dispatch(getProducts(result)); //action 객체를 생성하기 위해 action 함수 호출
        }

    }

};

export const callProductDetailAPI = ({ productCode }) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/api/v1/products/${ productCode }`);
        console.log('callProductDetailAPI result : ', result);

        if(result.status === 200) {
            dispatch(getProduct(result));
        }

    }

};

export const callAdminProductListAPI = ({ currentPage = 1 }) => {

    return async (dispatch, getState) => {

        const result = await authRequest.get(`/api/v1/products-management?page=${ currentPage }`);
        console.log('callAdminProductListAPI result : ', result);

        if (result.status === 200) {
            dispatch(getAdminProducts(result));
        }

    }

};

//등록하기 위한 API
export const callAdminProductRegistAPI = ({ registRequest }) => {

    return async (dispatch, getState) => {

        const result = await authRequest.post('/api/v1/products', registRequest); //넘길 데이터를 두 번째 인자로
        console.log('callAdminProductRegistAPI result : ', result);

        if (result.status === 201) {
            dispatch(postSuccess());
            toast.info("상품 등록이 완료되었습니다. 👏🏻");
        }

    }

};


export const callAdminProductAPI = ({ productCode }) => {

    return async (dispatch, getState) => {

        const result = await authRequest.get(`/api/v1/products-management/${ productCode }`);
        console.log('callAdminProductAPI result : ', result);

        if (result.status === 200) {
            dispatch(getAdminProduct(result));
        }

    }

};

export const callAdminProductModifyAPI = ({ productCode, modifyRequest }) => {

    return async (dispatch, getState) => {

        const result = await authRequest.put(`/api/v1/products/${ productCode }`, modifyRequest); //넘길 데이터를 두 번째 인자로
        console.log('callAdminProductModifyAPI result : ', result);

        if (result.status === 201) {
            dispatch(putSuccess());
            toast.info("상품 수정이 완료되었습니다. 👏🏻");
        }

    }

};