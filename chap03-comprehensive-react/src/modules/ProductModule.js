import {createActions, handleActions} from "redux-actions";

/* 초기값 */
const initialState = {};

/* 액션 타입 */
const GET_PRODUCTS = 'product/GET_PRODUCTS';
const GET_PRODUCT = 'product/GET_PRODUCT'

/* 액션 함수 */
//createActions : 액션 객체를 만들어서 반환한다.
//키 값은 액션의 타입이 되고, 반환 값은 payload의 값이다.
export const { product : { getProducts, getProduct } } = createActions({
    [GET_PRODUCTS] : result => ({ products : result.data }),
    [GET_PRODUCT] : result => ({ product : result.data })
});

/* 리듀서 */
//두 가지의 속성 state, action(action 객체 안의 payload를 꺼내서 사용하고 ({ products : result.data })이렇게 생겼다)
const productReducer = handleActions({
    [GET_PRODUCTS] : (state, { payload }) => payload,
    [GET_PRODUCT] : (state, { payload }) => payload
}, initialState);

export default productReducer;