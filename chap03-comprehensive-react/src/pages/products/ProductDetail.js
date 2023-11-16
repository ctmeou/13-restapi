import {useParams} from "react-router-dom";
import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callProductDetailAPI} from "../../apis/ProductAPICalls";
import ProductItem from "../../components/items/ProductItem";

function ProductDetail () {

    const dispatch = useDispatch();
    const { productCode } = useParams();
    const { product } = useSelector(state => state.productReducer);

    useEffect(() => {
        dispatch(callProductDetailAPI({productCode}));
    }, []);


    return (
        <>
            {
                product &&
                <div className="detail-div">
                    <ProductItem product={product}/>
                </div>
            }
        </>
    )
}

export default ProductDetail;