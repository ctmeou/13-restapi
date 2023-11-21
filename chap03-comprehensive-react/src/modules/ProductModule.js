import {createActions, handleActions} from "redux-actions";

/* 초기값 */
const initialState = {};

/* 액션 타입 */
const GET_PRODUCTS = 'product/GET_PRODUCTS';
const GET_PRODUCT = 'product/GET_PRODUCT';
const GET_ADMIN_PRODUCTS = 'product/GET_ADMIN_PRODUCTS';
const GET_ADMIN_PRODUCT = 'product/GET_ADMIN_PRODUCT';
const POST_SUCCESS = 'product/POST_SUCCESS';
const PUT_SUCCESS = 'product/PUT_SUCCESS';

/* 액션 함수 */
//createActions : 액션 객체를 만들어서 반환한다.
//키 값은 액션의 타입이 되고, 반환 값은 payload의 값이다.
export const { product : { getProducts, getProduct, getAdminProducts, getAdminProduct, postSuccess, putSuccess } } = createActions({
    [GET_PRODUCTS] : result => ({ products : result.data }),
    [GET_PRODUCT] : result => ({ product : result.data }),
    [GET_ADMIN_PRODUCTS] : result => ({ adminProducts : result.data }),
    [GET_ADMIN_PRODUCT] : result => ({ adminProduct : result.data }),
    [POST_SUCCESS] : () => ({ postSuccess : true }),
    [PUT_SUCCESS] : () => ({ putSuccess : true }),
});

/* 리듀서 */
//두 가지의 속성 state, action(action 객체 안의 payload를 꺼내서 사용하고 ({ products : result.data })이렇게 생겼다)
const productReducer = handleActions({
    [GET_PRODUCTS] : (state, { payload }) => payload,
    [GET_PRODUCT] : (state, { payload }) => payload, //state의 원래 있던 값은 지우고 덮어쓴다.
    [GET_ADMIN_PRODUCTS] : (state, { payload }) => payload,
    [GET_ADMIN_PRODUCT] : (state, { payload }) => payload,
    [POST_SUCCESS] : (state, { payload }) => payload,
    [PUT_SUCCESS] : (state, { payload }) => payload,
}, initialState);

export default productReducer;