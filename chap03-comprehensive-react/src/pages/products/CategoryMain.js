import {useParams} from "react-router-dom";
import {useEffect} from "react";
import {useDispatch} from "react-redux";
import {callProductCategoryListAPI} from "../../apis/ProductAPICalls";

function CategoryMain() {

    const dispatch = useDispatch();
    const { categoryCode } = useParams();

    useEffect(() => {
        dispatch(callProductCategoryListAPI({ categoryCode }));
    }, []);

    return (
        <>
            카테고리 메인
        </>
    );

}

export default CategoryMain;