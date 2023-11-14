import {useDispatch} from "react-redux";
import {useEffect, useState} from "react";
import {callProductListAPI} from "../../apis/ProductAPICalls";

function Main() {

    //store에 저장하는 것은 dispatch로 실행
    const dispatch = useDispatch();
    const [currentPage, setCurrentPage] = useState(1);

    //요청은 useEffect 내부에서 하면 된다.
    useEffect(() => {
        /* 모든 상품에 대한 정보 요청 */ //함수를 보내서 비동기적 통신을 이용할 것이다. API호출
        dispatch(callProductListAPI({ currentPage })); //함수 호출(객체 안에 currentPage가 있기 때문에)
    }, []);

    return (
        <>
            메인입니다.
        </>
    );

}

export default Main;