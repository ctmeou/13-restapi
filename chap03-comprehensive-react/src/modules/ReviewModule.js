import {createActions, handleActions} from "redux-actions";

/* 초기값 */
const initialState = {};

/* 액션 타입 */
const GET_REVIEWS = 'review/GET_REVIEWS';
const GET_REVIEW = 'review/GET_REVIEW';
const POST_SUCCESS = 'review/POST_SUCCESS';

/* 액션 함수 */
export const { review : { getReviews, getReview, postSuccess } } = createActions({
    [GET_REVIEWS] : result => ({ reviews : result.data }),
    [GET_REVIEW] : result => ({ review : result.data }),
    [POST_SUCCESS] : () => ({ postSuccess : true }),
});

/* 리듀서 */
const reviewReducer = handleActions({
    [GET_REVIEWS] : (state, { payload }) => payload,
    [GET_REVIEW] : (state, { payload }) => payload,
    [POST_SUCCESS] : (state, { payload }) => payload,
}, initialState);

export default reviewReducer;