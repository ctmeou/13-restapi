function PagingBar({ pageInfo, setCurrentPage }) { //하단의 페이징바, pageingbar가 생성될 때 전달

    const pageNumber = [...Array(pageInfo.endPage - pageInfo.startPage + 1).keys()] //Array 함수 호출 시 안에 있는 값만큼 배열 크기 생성
                                    .map(key => key + pageInfo.startPage);                         //keys 호출 시 버튼에 대한 배열이 생성된다.(6-1+1 -> [0,1,2,3,4,5])

    //반복문으로도 사용이 가능하다.
    //startPage로 시작해서 endPage까지 도는 반복문
    // const pageNumber = [];
    //
    // for (let i = pageInfo.startPage; i <= pageInfo.endPage; i++) {
    //     pageNumber.push(i);
    // }

    return (
        <ul className="paging-ul">
            <li>
                <button
                    className="paging-btn"
                    onClick={ () => setCurrentPage(pageInfo.currentPage - 1) } //왼쪽 버튼을 클릭 시 앞으로 가기 때문에 현재 페이지에서 -1을 setCurrentPage로 설정하고
                    disabled={ pageInfo.currentPage <= 1 } //1보다 작거나 같은 경우 disabled 설정으로 버튼이 먹지 않도록 설정한다.
                >
                    &lt; {/*왼쪽 버튼*/}
                </button>
            </li>
            {
                pageNumber.map(num => (
                    <li key={ num }>
                        <button
                            className="paging-btn"
                            style={ pageInfo.currentPage === num ? { background: 'lightpink' } : null }
                            onClick={ () => setCurrentPage(num) } //숫자 값을 setCurrentPage해준다.
                            disabled={ pageInfo.currentPage === num } //현재 페이지를 다시 요청하는 동작은 안 해도 된다.
                        >
                            { num }
                        </button>
                    </li>
                ))
            }
            <li>
                <button
                    className="paging-btn"
                    onClick={ () => setCurrentPage(pageInfo.currentPage + 1) } //오른쪽으로 이동
                    disabled={ pageInfo.currentPage >= pageInfo.maxPage } //현재 페이지가 maxPage를 넘어가면 넘어가지 못 하도록 설정
                >
                    &gt; {/*오른쪽 버튼*/}
                </button>
            </li>
        </ul>
    );

}

export default PagingBar;