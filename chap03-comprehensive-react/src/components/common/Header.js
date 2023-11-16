import {useNavigate} from "react-router-dom";
import {useState} from "react";

function Header() {

    const navigate = useNavigate();
    const [search, setSearch] = useState(''); //문자열을 기본값으로 설정한다.

    /* 로고 클릭 시 메인 페이지로 이동 */
    const onClickHandler = () => {
        navigate("/");
    }

    /* 검색어 입력 값 상태 저장 */
    //저장하기 위한 state가 필요하기 때문에 선언 먼저 한다.
    const onSearchChangeHandler = e => {
        setSearch(e.target.value); //onSearchChangeHandler 값이 이벤트가 발생할 때마다 바뀔 수 있다.
    }

    /* Enter 입력 시 검색 결과 화면으로 이동 */
    //Enter 눌렀는지는 이벤트 안에서 확인할 수 있다.
    const onEnterKeyHandler = e => {
        if (e.key === 'Enter') { //이벤트 객체로부터 key를 가지고 있고 Enter가 맞으면 라우팅할 화면을 가져온다.
            navigate(`/product/search?value=${ search }`); //검색에 대한 키워드는 search에 띄워준다.
        }
    }

    //로그인 전에 보여줄 컴포넌트
    function BeforeLogin() {

        const onClickLoginHandler = () => {
            navigate('/member/login');
        }

        const onClickSignupHandler = () => {
            navigate('/member/signup');
        }

        return(
            <div>
                <button
                    className="header-btn"
                    onClick={ onClickLoginHandler }
                >
                    로그인
                </button>
                <button
                    className="header-btn"
                    onClick={ onClickSignupHandler }
                >
                    회원가입
                </button>
            </div>
        );

    }

    //로그인 후에 보여줄 컴포넌트
    function AfterLogin() {

        return(
            <div>
                <button className="header-btn">마이페이지</button>
                <button className="header-btn">로그아웃</button>
            </div>
        );

    }

    //로그인 유무 상관 없이 공통적으로 보여줄 부분
    return(
        <>
            <div className="header-div">
                <button
                    className="logo-btn"
                    onClick={ onClickHandler }
                >
                    OHGIRAFFERS
                </button>
                <input
                    className="input-style"
                    type="text"
                    placeholder="검색"
                    onChange={ onSearchChangeHandler }
                    onKeyUp={ onEnterKeyHandler }
                />
                { false ? <AfterLogin/> : <BeforeLogin/> } {/*로그인 유무 확인하는 조건부 렌더링 현재 확인할 수 없어 false로 설정*/}
            </div>
        </>
    );

}

export default Header;