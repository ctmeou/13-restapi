import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {isLogin, removeToken} from "../../utils/TokenUtils";

function Header() {

    const navigate = useNavigate(); //main 페이지로 이동하게끔 사용할 수 있는 함수, useNavigate를 호출하면 전달 받는 함수가 navigate function이기 때문에 그것을 이용해서 main 페이지로 이동하게 작성할 수 있다.
    const [search, setSearch] = useState(''); //search와 setSearch를 State에서 받아오고 문자열을 기본값으로 설정한다.

    /* 로고 클릭 시 메인 페이지로 이동 */
    const onClickHandler = () => {
        navigate("/"); //navigate 함수를 이용해서 main 이동 설정
    }

    /* 검색어 입력 값 상태 저장 */
    //저장하기 위한 state가 필요하기 때문에 선언 먼저 한다.
    const onSearchChangeHandler = e => {
        setSearch(e.target.value); //onSearchChangeHandler 값이 이벤트가 바뀔 때마다 바뀔 수 있다.
    }

    /* Enter 입력 시 검색 결과 화면으로 이동 */
    //Enter 눌렀는지는 이벤트 겍체 안에서 어떤 이벤트가 일어났는지 확인할 수 있다.
    const onEnterKeyHandler = e => {
        if (e.key === 'Enter') { //이벤트 객체로부터 key 속성을 가지고 Enter가 맞으면 검색 결과에 대한 라우팅할 화면을 가져온다.
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

        //로그아웃 -> 토큰이 지워져야 한다.(TokenUtils에서 설정 후 요청)
        const onClickLogoutHandler = () => {
            removeToken(); //지워지고 나서 UI에서는 변동이 없음(reload를 해야 한다.) -> Main으로 reload될 수 있게 설정한다.
            window.location.replace("/") //root로 다시 갈 수 있도록 새로고침해주고 UI에서 로그아웃이 됐다고 판단할 수 있다.
        }

        const onClickMypageHandler = () => {
            navigate('member/mypage');
        }

        return(
            <div>
                <button
                    className="header-btn"
                    onClick={ onClickMypageHandler }
                >
                    마이페이지
                </button>
                <button
                    className="header-btn"
                    onClick={ onClickLogoutHandler }
                >
                    로그아웃
                </button>
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
                />                     {/*로그인 판단 유무는 토큰으로 할 수 있음 -> TokenUtils에서 호출해서 확인해본다.*/}
                { isLogin() ? <AfterLogin/> : <BeforeLogin/> } {/*로그인 유무 확인하는 조건부 렌더링 현재 확인할 수 없어 false로 설정했었고 TokenUtils에서 토큰 판단 유무를 설정했기 때문에 isLogin으로 변경*/}
            </div>
        </>
    );

}

export default Header;