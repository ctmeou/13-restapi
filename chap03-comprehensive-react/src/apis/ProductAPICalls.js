import {request} from "./Api";
                                        //꺼낼 때도 객체 안에서 꺼내야 한다.
export const callProductListAPI = ({ currentPage = 1}) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/products?page=${ currentPage }`);
        console.log('callProductListAPI result : ', result);

    }

};