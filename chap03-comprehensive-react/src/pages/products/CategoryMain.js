import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callProductCategoryListAPI} from "../../apis/ProductAPICalls";
import ProductList from "../../components/lists/ProductList";
import PagingBar from "../../components/common/PagingBar";

function CategoryMain() {

    const dispatch = useDispatch();
    const { categoryCode } = useParams(); //App에 선언한 변수를 꺼내서 처리
    const { products } = useSelector(state => state.productReducer); //저장된 값을 구독하기 위해
    const { currentPage, setCurrentPage } = useState(1);

    //요청할 API를 useEffect 내에서 요청한다.(dispatch 함수 요청)
    useEffect(() => { //dispatch 함수 요청 시 넘겨줘야 하는 함수가 있고 API 함수와 관련된 함수이다. - ProductAPICalls 참고
        dispatch(callProductCategoryListAPI({ categoryCode, currentPage }));
    }, [categoryCode, currentPage]); //categoryCode나 currentPage가 변화하면 다시 요청한다.

    return (
        <>
            { products //products가 있을 시
                &&
                <>
                    <ProductList data={ products.data }/> {/*products의 data를 productList로 표현하고*/}
                    <PagingBar pageInfo={ products.pageInfo } setCurrentPage={ setCurrentPage }/> {/*그 값의 pageInfo를 paingBar로 표현하는데 setCurrentPage를 전달해야 하는데 state를 통해 요청과 변화를 일어날 수 있게 한다.*/}
                </>
            }
        </>
    );

}

export default CategoryMain;