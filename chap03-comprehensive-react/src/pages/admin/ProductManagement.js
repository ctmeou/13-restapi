import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {callAdminProductListAPI} from "../../apis/ProductAPICalls";
import PagingBar from "../../components/common/PagingBar";
import ProductTable from "../../components/items/ProductTable";
import {useNavigate} from "react-router-dom";

function ProductManagement() {

    //최초 로드 시 callAdminProductListAPI 요청
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [currentPage, setCurrentPage] = useState(1);
    const { adminProducts } = useSelector(state => state.productReducer);

    useEffect(() => {
        dispatch(callAdminProductListAPI({ currentPage }));
    }, [currentPage]); //기본 요청은 currentPage이고 변화하면 다시 currentPage 요청한다.

    const onClickProductInsert = () => {
        navigate('/product-regist'); //navigate로 화면 이동
    }

    return (
        <>
            { adminProducts &&
                <div className="management-div">
                    <ProductTable data={ adminProducts.data }/>
                    <PagingBar pageInfo={ adminProducts.pageInfo } setCurrentPage={ setCurrentPage }/>
                    <button onClick={ onClickProductInsert }>상품 등록</button>
                </div>
            }
        </>
    );

}

export default ProductManagement;