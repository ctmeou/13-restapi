import {useNavigate} from "react-router-dom";

function ProductListItem({ product : { productCode, productImageUrl, productName, productPrice } }) {

    const navigate = useNavigate();

    const onClickProductHandler = () => {
        navigate(`/product/${productCode}`); //상세 페이지 이동
    }


    return (
        <div
            onClick={ onClickProductHandler }
            className="product-div"
        >
            <img src={ productImageUrl } alt={ productName }/>
            <h5>{ productName }</h5>
            <h5>{ productPrice }</h5>
        </div>
    )

}

export default ProductListItem;