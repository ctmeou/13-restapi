import {useNavigate, useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {callReviewAPI} from "../../apis/ReviewAPICalls";

function ReviewDetail() {

    const { reviewCode } = useParams();
    const navigate = useNavigate();
    const { review } = useSelector(state => state.reviewReducer);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(callReviewAPI({ reviewCode }));
    }, []); //의존성 배열 전달 없이 최초 한 번만 전달

    return (
        <>
            { review &&
                <div className="review-detail-table-div">
                    <table className="review-detail-table">
                        <tbody>
                        <tr>
                            <th>제목</th>
                            <td>{ review.reviewTitle }</td>
                        </tr>
                        <tr>
                            <th>작성자</th>
                            <td>{ review.memberName }</td>
                        </tr>
                        <tr>
                            <th>작성일</th>
                            <td>{ review.createdAt }</td>
                        </tr>
                        <tr>
                            <td colSpan={ 2 }> { review.reviewContent }</td>
                        </tr>
                        </tbody>
                    </table>
                    <div className="product-button-div">
                        <button
                            className="back-btn"
                            onClick={ () => navigate(-1) } //navigate -1은 history가 있기 때문에 바로 전으로 돌아간다.
                        >
                            돌아가기
                        </button>
                    </div>
                </div>
            }
        </>
    );
}

export default ReviewDetail;