import {useNavigate} from "react-router-dom";

function ProductTable({ data }) {

    const navigate = useNavigate();

    /* 테이블 상품 행 클릭 시 상품 상세 및 수정 페이지로 이동 */
    const onClickTableTr = (productCode) => {
        navigate(`/product-modify/${ productCode }`);
    }

    return (
        <table className="product-table">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>상품명</th>
                    <th>상품 가격</th>
                    <th>상태</th>
                    <th>카테고리</th>
                    <th>재고</th>
                </tr>
            </thead>
            <tbody>
                {
                    data.map(product => (
                        <tr
                            key={ product.productCode} //반복되는 코드에는 key 값 필요
                            onClick={ () => onClickTableTr(product.productCode) }
                        >
                            <td>{ product.productCode }</td>
                            <td>{ product.productName }</td>
                            <td>{ product.productPrice }</td>
                            <td>{ product.status === 'usable' ? '판매 중' : '판매 중단' }</td>
                            <td>{ product.categoryName }</td>
                            <td>{ product.productStock }</td>
                        </tr>
                    ))
                }
            </tbody>
        </table>
    );

}

export default ProductTable;