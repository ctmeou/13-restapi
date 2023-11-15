import {useParams, useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callProductCategoryListAPI, callProductSearchListAPI} from "../../apis/ProductAPICalls";
import ProductList from "../../components/lists/ProductList";
import PagingBar from "../../components/common/PagingBar";

function SearchMain() {

    const dispatch = useDispatch();
    const [searchParams] = useSearchParams();
    const productName = searchParams.get('value'); //get 메소드 이용해서 꺼내고 싶은 값을 꺼낸다.
    const { products } = useSelector(state => state.productReducer);
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        dispatch(callProductSearchListAPI({ productName, currentPage }));
    }, [productName, currentPage]);

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