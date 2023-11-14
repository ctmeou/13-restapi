import {applyMiddleware, legacy_createStore as createStore} from "redux";
import rootReducer from "./modules";
import {composeWithDevTools} from "redux-devtools-extension";
import ReduxThunk from 'redux-thunk';
import ReduxLogger from 'redux-logger';

const store = createStore( //createStore의 줄을 삭제하기 위해 import문에 legacy_createStore as 추가 작성
    rootReducer, //첫 번째로 전달할 인자 리듀서
    composeWithDevTools(applyMiddleware(ReduxThunk, ReduxLogger)) //Middleware는 적용할 순서대로 작성하면 된다.
);

export default store;