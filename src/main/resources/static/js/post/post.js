// [1] URL(경로상의 cno ) 매개변수 값 구하기.
const cno = new URL(location.href).searchParams.get('cno');
let page = new URL(location.href).searchParams.get('page');
if (!page) {
    page = 1; // 만약에 page 매개변수가 없으면 1페이지로 설정
}

// [2] 지정한 카테고리별 게시물 조회 요청 (async/await 사용)
const findall = async () => {
    try {
        // fetch 요청을 await로 대기하고, 응답을 response 변수에 저장
        const response = await fetch(`/post?cno=${cno}&page=${page}`);
        // JSON 파싱을 await로 대기하고, 결과를 result 변수에 저장
        const result = await response.json();

        console.log(result);

        const postlistTbody = document.querySelector('.postlist > tbody');
        let html = ``;

        // 동기식으로 받아온 데이터를 사용하여 HTML 렌더링

        result.data.forEach((post) => {
         let nowDate = formatDateTime( post.pdate )
            html += `<tr>
                        <td>${post.pno}</td>
                        <td><a href="view.jsp?pno=${post.pno}">${post.ptitle}</a></td>
                        <td>${post.mid}</td>
                        <td> ${ nowDate } </td>
                        <td>${post.pview}</td>
                     </tr>`;
        });
        postlistTbody.innerHTML = html;

        // 페이징 버튼 생성 함수 실행
        getPageBtn(result);

    } catch (e) {
        console.error(e); // 에러 발생 시 콘솔에 출력
    }
};

// [3] 페이지 버튼 생성 함수
const getPageBtn = (response) => {
    const currentPage = parseInt(response.page);
    const pagebtnbox = document.querySelector('.pagebtnbox');
    let html = ``;

    // (1) '이전' 버튼
    html += `<li class="page-item">
               <a class="page-link" href="post.jsp?cno=${cno}&page=${currentPage <= 1 ? 1 : currentPage - 1}">이전</a>
             </li>`;

    // (2) 페이지 번호 버튼
    for (let i = response.startbtn; i <= response.endbtn; i++) {
        html += `<li class="page-item">
                   <a class="page-link ${currentPage === i ? 'active' : ''}" href="post.jsp?cno=${cno}&page=${i}">
                     ${i}
                   </a>
                 </li>`;
    }

    // (3) '다음' 버튼
    html += `<li class="page-item">
               <a class="page-link" href="post.jsp?cno=${cno}&page=${currentPage >= response.totalpage ? response.totalpage : currentPage + 1}">다음</a>
             </li>`;

    pagebtnbox.innerHTML = html;
};


/**
 * [NEW] 날짜 포맷팅 함수
 * - 이 함수는 게시물 목록(post.js)이나 상세조회(view.js) 등 날짜를 표시하는 곳에서 재사용할 수 있습니다.
 * @param {string} dateTimeString - 'YYYY-MM-DD HH:mm:ss' 형식의 날짜 문자열
 * @returns {string} - 변환된 시간 문자열
 */
const formatDateTime = (dateTimeString) => {
    const now = new Date();
    // 서버에서 받은 날짜 문자열로 Date 객체 생성 (브라우저의 로컬 시간대 기준)
    const postDate = new Date(dateTimeString);

    // 현재 시간과 게시물 작성 시간의 차이 (밀리초)
    const diffMillis = now.getTime() - postDate.getTime();
    // 차이를 분, 시간 단위로 변환
    const diffMinutes = diffMillis / (1000 * 60);
    const diffHours = diffMillis / (1000 * 60 * 60);

    if (diffMinutes < 1) {
        return "방금 전";
    } else if (diffMinutes < 60) { // 1시간 미만일 경우
        return `${Math.floor(diffMinutes)}분 전`;
    } else if (diffHours < 24) { // 24시간 미만일 경우
        return `${Math.floor(diffHours)}시간 전`;
    } else { // 24시간이 지났을 경우
        // YYYY-MM-DD 형식으로 날짜만 반환
        const year = postDate.getFullYear();
        const month = String(postDate.getMonth() + 1).padStart(2, '0');
        const day = String(postDate.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }
};


// 페이지가 열리면 함수 실행
findall();


