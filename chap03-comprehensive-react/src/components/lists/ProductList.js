import ProductListItem from "../items/ProductListItem";

function ProductList({ data }) {

    return (
        <div className="products-div"> {/*반복적으로 처리하고 있기 때문에 key 값 이용*/}
            {
                data &&
                data.map(product => <ProductListItem key={ product.productCode } product={ product }/>)
            }
        </div>
    );

}

export default ProductList;