import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callProductCategoryListAPI} from "../../apis/ProductAPICalls";
import ProductList from "../../components/lists/ProductList";
import PagingBar from "../../components/common/PagingBar";

function CategoryMain() {

    const dispatch = useDispatch();
    const { categoryCode } = useParams();
    const { products } = useSelector(state => state.productReducer);
    const { currentPage, setCurrentPage } = useState(1);

    useEffect(() => {
        dispatch(callProductCategoryListAPI({ categoryCode, currentPage }));
    }, [categoryCode, currentPage]); //categoryCode나 currentPage가 변화하면 다시 요청한다.

    return (
        <>
            { products
                &&
                <>
                    <ProductList data={ products.data }/>
                    <PagingBar pageInfo={ products.pageInfo} setCurrentPage={ setCurrentPage }/>
                </>
            }
        </>
    );

}

export default CategoryMain;