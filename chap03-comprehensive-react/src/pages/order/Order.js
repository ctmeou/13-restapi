import {ToastContainer} from "react-toastify";
import {useLocation, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {callOrderRegistAPI} from "../../apis/OrderAPICalls";
import {useDispatch, useSelector} from "react-redux";

function Order() {

    /* navigate로 전달된 state 꺼내기 위해 사용 */
    const location = useLocation();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { product, amount } = { ...location.state }; //꺼내야 할 값이 locaton의 state에 있다고 생각
    const [form, setForm] = useState({
        productCode : product.productCode, //초기값 설정, 두 개의 값은 요청의 필드명과 동일하게 설정해 setting(orderCreate의 값과 일치하게 작성)
        orderAmount : amount
    });
    const { postSuccess } = useSelector(state => state.orderReducer); //컴포넌트에서 감지하고 있기위해 useSelector 사용(orderReducer로부터 postSuccess 감지)

    useEffect(() => {
        if (postSuccess === true) { //변화 시에 true이면 결제가 잘 되었다.
            navigate("/member/mypage/payment", { replace : true });
        }
    }, [postSuccess]);

    const onChangeHandler = (e) => {
        setForm({
            ...form,
            [e.target.name] : e.target.value
        })
    };

    //위의 onChangeHandler 폼을 전달하면서 요청하는 값
    const onClickPurchaseHandler = () => {
        dispatch(callOrderRegistAPI({ registRequest : form }))
    };

    return (
        <>
            <ToastContainer hideProgressBar={ true } position="top-center"/>
            <div className="purchase-div">
                <div className="purchase-info-div">
                    <h3>주문자 정보</h3>
                    <input
                        name="orderPhone"
                        placeholder="핸드폰번호"
                        autoComplete="off"
                        onChange={ onChangeHandler }
                        className="purchase-input"
                    />
                    <input
                        placeholder="이메일주소"
                        name="orderEmail"
                        autoComplete="off"
                        onChange={ onChangeHandler }
                        className="purchase-input"
                    />
                    <h3>배송 정보</h3>
                    <input
                        placeholder="받는사람"
                        name="orderReceiver"
                        autoComplete="off"
                        onChange={ onChangeHandler }
                        className="purchase-input"
                    />
                    <input
                        placeholder="배송주소"
                        name="orderAddress"
                        autoComplete="off"
                        onChange={ onChangeHandler }
                        className="purchase-input"
                    />
                </div>
                <div className="purchase-info-div">
                    <h3>결제 정보</h3>
                    <table>
                        <colgroup>
                            <col width="25%"/>
                            <col width="75%"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>상품명</th>
                            <td>{ product.productName }</td>
                        </tr>
                        <tr>
                            <th>상품 개수</th>
                            <td>{ amount }</td>
                        </tr>
                        <tr>
                            <th>결제 금액</th>
                            <td>{ amount * product.productPrice }</td>
                        </tr>
                        <tr>
                            <td colSpan={ 2 }>
                                <button onClick={ onClickPurchaseHandler }>구매하기</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );

}

export default Order;