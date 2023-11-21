import {createActions, handleActions} from "redux-actions";

/* 초기값 */
const initialState = {};

/* 액션 타입 */
const GET_REVIEWS = 'review/GET_REVIEWS';
const GET_REVIEW = 'review/GET_REVIEW';

/* 액션 함수 */
export const { review : { getReviews, getReview } } = createActions({
    [GET_REVIEWS] : result => ({ reviews : result.data }),
    [GET_REVIEW] : result => ({ review : result.data }),
});

/* 리듀서 */
const reviewReducer = handleActions({
    [GET_REVIEWS] : (state, { payload }) => payload,
    [GET_REVIEW] : (state, { payload }) => payload,
}, initialState);

export default reviewReducer;