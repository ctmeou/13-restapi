import {useNavigate} from "react-router-dom";
import {useRef, useState} from "react";

function ProductRegist() {

    const navigate = useNavigate();
    const [form, setForm] = useState({});
    const imageInput = useRef();

    /* 입력 양식 값 변경 시 state 수정 */
    const onChangeHandler = e => {
        setForm({
            ...form,
            [e.target.name] : e.target.value
        })
    }

    /* 이미지 업로드 버튼 클릭 시 input type file이 클릭되도록 하는 이벤트 */
    const onClickImageUpload = () => {
        //react hook 사용자 입력 양식 방식 중 자동 입력 방식 useRef
        imageInput.current.click(); //버튼 클릭 시 input의 file을 올릴 수 있는 형태를 볼 수 있다.
    }

    /* 상품 등록 버튼 클릭 시 이벤트 */
    const onClickProductRegistrationHandler = () => {}

    return (
        <div>
            <div className="product-button-div">
                <button
                    onClick={ () => navigate(-1) } //navigate에 url을 줄 수도 있지만 숫자를 줄 수도 있다.(history 관리되고 있고 숫자로 표현)
                >
                    돌아가기
                </button>
                <button
                    onClick={ onClickProductRegistrationHandler }
                >
                    상품 등록
                </button>
            </div>
            <div className="product-section">
                <div className="product-info-div">
                    <div className="product-img-div">
                        {/*{ imageUrl &&*/}
                        {/*    <img*/}
                        {/*        className="product-img"*/}
                        {/*        alt="preview"*/}
                        {/*        src={ imageUrl }*/}
                        {/*    />*/}
                        {/*}*/}
                        <input
                            style={ { display: 'none' } }
                            type="file"
                            name='productImage'
                            accept='image/jpg,image/png,image/jpeg,image/gif'
                            ref={ imageInput }
                            // onChange={ onChangeImageUpload }
                        />
                        <button
                            className="product-image-button"
                            onClick={ onClickImageUpload }
                        >
                            이미지 업로드 {/*이미지 업로드를 클릭하면 숨겨진 input 태그가 클릭되는 것처럼 보여져 이미지 등록하게끔 설정한다.*/}
                        </button>
                    </div>
                </div>
                <div className="product-info-div">
                    <table>
                        <tbody>
                        <tr>
                            <td><label>상품 이름</label></td>
                            <td>
                                <input
                                    name='productName'
                                    placeholder='상품 이름'
                                    className="product-info-input"
                                    onChange={ onChangeHandler }
                                />
                            </td>
                        </tr>
                        <tr>
                            <td><label>상품 가격</label></td>
                            <td>
                                <input
                                    name='productPrice'
                                    placeholder='상품 가격'
                                    type='number'
                                    className="product-info-input"
                                    onChange={ onChangeHandler }
                                />
                            </td>
                        </tr>
                        <tr>
                            <td><label>상품 종류</label></td>
                            <td>
                                <label><input type="radio" onChange={ onChangeHandler } name="categoryCode" value="1"/> 식사</label> &nbsp;
                                <label><input type="radio" onChange={ onChangeHandler } name="categoryCode" value="2"/> 디저트</label> &nbsp;
                                <label><input type="radio" onChange={ onChangeHandler } name="categoryCode" value="3"/> 음료</label>
                            </td>
                        </tr>
                        <tr>
                            <td><label>상품 재고</label></td>
                            <td>
                                <input
                                    placeholder='상품 재고'
                                    type='number'
                                    name='productStock'
                                    className="product-info-input"
                                    onChange={ onChangeHandler }
                                />
                            </td>
                        </tr>
                        <tr>
                            <td><label>상품 설명</label></td>
                            <td>
                                    <textarea
                                        className="textarea-style"
                                        name='productDescription'
                                        onChange={ onChangeHandler }
                                    ></textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );

}

export default ProductRegist;