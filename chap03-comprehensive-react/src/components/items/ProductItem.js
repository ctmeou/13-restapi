import {useNavigate} from "react-router-dom";
import {useState} from "react";

function ProductItem ({ product }) {

    const navigate = useNavigate();
    const [amount, setAmount] = useState(1); //구매 수량에 대해 state로 관리

    /* 구매 수량 변경 이벤트 */
    const onChangeAmountHandler = e => {
        setAmount(e.target.value);
    }

    /* 구매하기 버튼 이벤트 */
    //구매하기 버튼 클릭 시 order 요청하고 넘어가는 정보는 state 키 값을 통해 넘기면 넘어간 component를 통해 받아올 수 있다.
    const onClickOrderHandler = () => {
        /* 이동하는 component로 정보를 전달하기 위해 2번째 인자로 객체에 state 속성으로 전달할 수 있다.*/
        navigate('/order', { state : { product, amount } });
    }

    /* 리뷰 보기 버튼 이벤트 */
    const onClickReviewHandler = () => {
        navigate(`/review/product/${ product.productCode }`); //특정 상품에 대해 이동
    }

    return (
        <>
            <div className="img-div">
                <img src={ product.productImageUrl } alt={ product.productName }/>
                <button
                    onClick={ onClickReviewHandler }
                    className="review-btn"
                >
                    리뷰 보기
                </button>
            </div>
            <div className="description-div">
                <table className="description-table">
                    <tbody>
                    <tr>
                        <th>상품 코드</th>
                        <td>{ product.productCode }</td>
                    </tr>
                    <tr>
                        <th>상품명</th>
                        <td>{ product.productName }</td>
                    </tr>
                    <tr>
                        <th>상품 가격</th>
                        <td>{ product.productPrice }</td>
                    </tr>
                    <tr>
                        <th>상품 설명</th>
                        <td>{ product.productDescription }</td>
                    </tr>
                    <tr>
                        <th>구매 가능 수량</th>
                        <td>{ product.productStock }</td>
                    </tr>
                    <tr>
                        <th>구매 수량</th> {/*수량에 대해 관리하고 있는 input 양식*/}
                        <td><input type="number" min="1" onChange={ onChangeAmountHandler } value={ amount }/></td>
                    </tr>
                    </tbody>
                </table>
                <button
                    onClick={ onClickOrderHandler }
                    className="product-buy-btn"
                >
                    구매 하기
                </button>
            </div>
        </>
    );

}

export default ProductItem;