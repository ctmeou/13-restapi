import {useEffect, useState} from "react";
import PagingBar from "../../components/common/PagingBar";
import {ToastContainer} from "react-toastify";
import {useNavigate, useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {callReviewsAPI} from "../../apis/ReviewAPICalls";

function Reviews() {

    const { productCode } = useParams();
    const { reviews } = useSelector(state => state.reviewReducer);
    const [currentPage, setCurrentPage] = useState(1);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        dispatch(callReviewsAPI({ productCode, currentPage }));
    }, [currentPage]); //currentPage가 변화하게 되면 review를 가져올 수 있도록 설정

    const onClickTableTr = (reviewCode) => {
        navigate(`/review/${ reviewCode }`);
    };

    console.log('productCode', productCode);

    return (
        <>
            { reviews &&
                <div className="review-table-div">
                    <ToastContainer hideProgressBar={ true } position="top-center"/>
                    <table className="review-table">
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>상품명</th>
                            <th>리뷰 제목</th>
                            <th>리뷰 작성일</th>
                            <th>작성자</th>
                        </tr>
                        </thead>
                        <tbody>
                        { reviews.data.map(review => (
                            <tr
                                key={ review.reviewCode }
                                onClick={ () => onClickTableTr(review.reviewCode) } //몇 번 리뷰인지
                            >
                                <td>{ review.reviewCode }</td>
                                <td>{ review.productName }</td>
                                <td>{ review.reviewTitle }</td>
                                <td>{ review.createdAt }</td>
                                <td>{ review.memberName }</td>
                            </tr>
                        ))
                        }
                        </tbody>
                    </table>
                    <PagingBar pageInfo={ reviews.pageInfo } setCurrentPage={ setCurrentPage } />
                </div>
            }
        </>
    );
}

export default Reviews;