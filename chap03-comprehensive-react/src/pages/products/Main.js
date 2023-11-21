import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {callProductListAPI} from "../../apis/ProductAPICalls";
import ProductList from "../../components/lists/ProductList";
import PagingBar from "../../components/common/PagingBar";

function Main() {

    //store에 저장하는 것은 dispatch로 실행
    const dispatch = useDispatch();
    const [currentPage, setCurrentPage] = useState(1); //pagingbar를 눌렀을 경우, 여기의 값이 변경됨으로써 수정이 되고, 수정이 됨으로 상품 정보 요청이 일어나고, productList pagingbar 렌더링이 다시 일어나야 한다.
    const { products } = useSelector(state => state.productReducer);

    //pagingbar에 따른 페이지 변화가 일어날 때 currentPage가 바뀔 때마다 리렌더링된다.
    //요청은 useEffect 내부에서 하면 된다.
    useEffect(() => {
        /* 모든 상품에 대한 정보 요청 */ //함수를 보내서 비동기적 통신을 이용할 것이다. API호출
        dispatch(callProductListAPI({ currentPage })); //함수 호출(객체 안에 currentPage가 있기 때문에)
    }, [currentPage]); //의존성 배열에 currentPage을 넘기면 다시 렌더링이 된다.

    //조건부 렌더링을 이용해 products가 존재할 때 보여준다.
    return (
        <>
            { products
                &&
                <>
                    <ProductList data={ products.data }/> {/*products 안에 있는 data 배열 값을 넘겨준다.*/}
                    <PagingBar pageInfo={ products.pageInfo } setCurrentPage={ setCurrentPage }/> {/*PagingBar가 가지고 있어야 하는 정보는 products내의 pageInfo, pageInfo를 PagingBar로 넘겨받아 처리한다.*/}
                </>
            }
        </>
    );

}

export default Main;