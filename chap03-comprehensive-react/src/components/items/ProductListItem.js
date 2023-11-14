function ProductListItem({ product : { productImageUrl, productName, productPrice } }) {

    return (
        <div className="product-div">
            <img src={ productImageUrl } alt={ productName }/>
            <h5>{ productName }</h5>
            <h5>{ productPrice }</h5>
        </div>
    )

}

export default ProductListItem;