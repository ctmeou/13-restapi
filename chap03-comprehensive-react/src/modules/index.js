import {combineReducers} from "redux";
import productReducer from "./ProductModule";

//가장 기본이 되는 리듀서 작성
const rootReducer = combineReducers({
    productReducer
});

export  default rootReducer;