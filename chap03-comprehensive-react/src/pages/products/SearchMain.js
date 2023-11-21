import {useParams, useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callProductCategoryListAPI, callProductSearchListAPI} from "../../apis/ProductAPICalls";
import ProductList from "../../components/lists/ProductList";
import PagingBar from "../../components/common/PagingBar";

function SearchMain() {

    const dispatch = useDispatch();
    const [searchParams] = useSearchParams(); //반환하는 것은 객체가 아닌 배열이기 때문에 구조 분해 할당으로 꺼낼 때 배열의 행태로 꺼낸다.
    const productName = searchParams.get('value'); //get 메소드 이용해서 꺼내고 싶은 값을 꺼낸다. value로 key 값을 꺼낸다.
    const { products } = useSelector(state => state.productReducer);
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        dispatch(callProductSearchListAPI({ productName, currentPage }));
    }, [productName, currentPage]); //productName나 currentPage가 바뀌면 요청한다.

    return (
        <>
            { products
                &&
                <>
                    <ProductList data={ products.data }/>
                    <PagingBar pageInfo={ products.pageInfo } setCurrentPage={ setCurrentPage }/>
                </>
            }
        </>
    );

}

export default SearchMain;