import {combineReducers} from "redux";
import productReducer from "./ProductModule";
import memberReducer from "./MemberModule";

//가장 기본이 되는 리듀서 작성
const rootReducer = combineReducers({
    productReducer, memberReducer
});

export  default rootReducer;