import {createActions, handleActions} from "redux-actions";

/* 초기값 */
const initialState = {};

/* 액션 타입 */
const GET_PRODUCTS = 'product/GET_PRODUCTS';

/* 액션 함수 */
//createActions : 액션 객체를 만들어서 반환한다.
//키 값은 액션의 타입이 되고, 반환 값은 payload의 값이다.
export const { product : { getProducts } } = createActions({
    [GET_PRODUCTS] : () => {}
});

/* 리듀서 */
const productReducer = handleActions({
    [GET_PRODUCTS] : () => {}
}, initialState);

export default productReducer;