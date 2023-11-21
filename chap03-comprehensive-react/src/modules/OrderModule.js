import {createActions, handleActions} from "redux-actions";

/* 초기값 */
const initialState = {};

/* 액션 타입 */
const POST_SUCCESS = 'order/POST_SUCCESS';
const GET_ORDERS = 'order/GET_ORDERS';

/* 액션 함수 */
export const { order : { postSuccess, getOrders } } = createActions({
    [POST_SUCCESS] : () => ({ postSuccess : true }),
    [GET_ORDERS] : result => ({ orders : result.data }), //axios 객체를 그대로 넘겼기 때문에 객체 안의 값을 꺼내야 한다.
});

/* 리듀서 */
const orderReducer = handleActions({
    [POST_SUCCESS] : (state, { payload }) => payload,
    [GET_ORDERS] : (state, { payload }) => payload,
}, initialState);

export default orderReducer;