function Header() {

    //로그인 전에 보여줄 컴포넌트
    function BeforeLogin() {

        return(
            <div>
                <button className="header-btn">로그인</button>
                <button className="header-btn">회원가입</button>
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
                <button className="logo-btn">OHGIRAFFERS</button>
                <input
                    className="input-style"
                    type="text"
                    placeholder="검색"
                />
                { false ? <AfterLogin/> : <BeforeLogin/> } {/*로그인 유무 확인하는 조건부 렌더링 현재 확인할 수 없어 false로 설정*/}
            </div>
        </>
    );

}

export default Header;