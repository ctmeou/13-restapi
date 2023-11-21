import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callReviewRegistAPI} from "../../apis/ReviewAPICalls";
import {useNavigate} from "react-router-dom";

function ReviewWriteModal({ productCode, setProductReviewWriteModal }) {

    const [form, setForm] = useState({ productCode });
    const dispatch = useDispatch();
    const { postSuccess } = useSelector(state => state.reviewReducer);
    const navigate = useNavigate();

    useEffect(() => { //통신이 잘 되었는지 안 되었는지 판단하기 위해 사용하고 판단 근거는 postSuccess에서 온다.
        if (postSuccess === true) {
            navigate(`/review/product/${ productCode }`)
        }
    }, [postSuccess]);

    const onChangeHandler = (e) => {
        setForm({
            ...form,
            [e.target.name] : e.target.value
        })
    };

    const onClickProductReviewHandler = () => {
        dispatch(callReviewRegistAPI({ registRequest : form }));
    };

    return (
        <div className="modal">
            <div className="modal-container">
                <div className="product-review-modal-div">
                    <h1>리뷰</h1>
                    <input
                        type="text"
                        name="reviewTitle"
                        placeholder="리뷰 제목"
                        onChange={ onChangeHandler }
                    />
                    <textarea
                        placeholder="리뷰 본문"
                        name="reviewContent"
                        onChange={ onChangeHandler }
                    ></textarea>
                    <button onClick={ onClickProductReviewHandler }>리뷰 작성하기</button>
                    <button
                        style={{
                            border: "none",
                            margin: 0,
                            fontSize: "10px",
                            height: "10px",
                        }}
                        onClick={ () => setProductReviewWriteModal(false) }
                    >
                        닫기
                    </button>
                </div>
            </div>
        </div>
    );

}

export default ReviewWriteModal;