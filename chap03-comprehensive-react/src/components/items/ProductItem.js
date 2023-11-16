function ProductItem ({ product }) {

    return (
        <>
            <div className="img-div">
                <img src={product.productImageUrl} alt={product.productName}/>
                <button className="review-btn">리뷰보기</button>
            </div>
            <div className="description-div">
                <table className="description-table">
                    <tbody>
                    <tr>
                        <th>상품코드</th>
                        <td>{product.productCode}</td>
                    </tr>
                    <tr>
                        <th>상품명</th>
                        <td>{product.productName}</td>
                    </tr>
                    <tr>
                        <th>상품 가격</th>
                        <td>{product.productPrice}</td>
                    </tr>
                    <tr>
                        <th>상품 설명</th>
                        <td>{product.productDescription}</td>
                    </tr>
                    <tr>
                        <th>구매 가능 수량</th>
                        <td>{product.productStock}</td>
                    </tr>
                    <tr>
                        <th>구매 수량</th>
                        <td><input type="number" min="1"/></td>
                    </tr>
                    </tbody>
                </table>
                <button className="product-buy-btn">구매 하기</button>
            </div>
        </>
    );
}

export default ProductItem;