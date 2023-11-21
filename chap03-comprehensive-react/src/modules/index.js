import {combineReducers} from "redux";
import productReducer from "./ProductModule";
import memberReducer from "./MemberModule";
import orderReducer from "./OrderModule";
import reviewReducer from "./ReviewModule";

//가장 기본이 되는 리듀서 작성
const rootReducer = combineReducers({
    productReducer, memberReducer, orderReducer, reviewReducer //combine해줘야 감지가 된다.
});

export  default rootReducer;