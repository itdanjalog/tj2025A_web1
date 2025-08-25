/**
 * [작성일: 2025-08-25]
 * 게시판 목록/검색/페이징 전용 스크립트
 * - 기능 요약:
 *   1) URL의 파라미터(cno, page, key, keyword)를 읽어 초기 상태를 구성
 *   2) 서버에 목록 데이터를 요청(fetch)하여 테이블에 렌더링
 *   3) '이전/다음/번호' 페이징 버튼을 동적으로 생성(검색 조건 유지)
 *   4) 검색 실행 시 항상 1페이지로 이동
 * - 주의:
 *   - 본 코드는 검색 파라미터 인코딩을 별도로 하지 않습니다(요청에 따라 제거).
 *   - key/keyword에 공백·특수문자가 포함될 수 있다면 서버/라우팅단에서 적절히 처리하세요.
 */

// ---------------------------------------------
// [작성일: 2025-08-25] URL 파라미터 값 가져오기
// ---------------------------------------------

// 현재 페이지의 URL에서 쿼리스트링을 파싱하기 위한 객체 생성
const params = new URL(location.href).searchParams;

// 카테고리 번호(cno): 목록 필수 기준값 (예: 카테고리별 게시판)
const cno = params.get('cno');

// 페이지 번호(page): 없으면 기본값 1로 설정
let page = params.get('page') || 1;

// 검색 필드명(key): 예) 'ptitle', 'mid' 등. 미지정 시 빈 문자열
let key = params.get('key') || '';

// 검색 키워드(keyword): 미지정 시 빈 문자열
let keyword = params.get('keyword') || '';


// ----------------------------------------------------
// [작성일: 2025-08-25] 게시물 목록 조회(렌더링) 메인 함수
// ----------------------------------------------------
const findAll = async () => {
    try {
        // 1) 서버에 전달할 요청 URL 구성
        //    - 요청사항에 따라 인코딩(encodeURIComponent) 미사용
        //    - 검색 조건(key/keyword)도 항상 포함하여 서버가 같은 규격으로 응답하게 함
        const url = `/post?cno=${cno}&page=${page}&key=${key}&keyword=${keyword}`;

        // 2) fetch로 비동기 요청 후 JSON 파싱
        const response = await fetch(url);
        const result = await response.json();

        // 3) 목록이 렌더링될 <tbody> 노드 선택
        const tbody = document.querySelector('.postlist');

        // 4) 서버에서 받아온 result.data(배열)를 순회하여 HTML 문자열 누적
        //    - map().join('') 대신 요청에 따라 일반 for문 사용
        let html = '';
        for (let i = 0; i < result.data.length; i++) {
            const post = result.data[i];

            // 게시물 작성일 포맷팅 (방금 전/분 전/시간 전/날짜)
            const nowDate = formatDateTime(post.pdate);

            // 한 행(tr) 렌더링: 번호/제목/작성자/작성일/조회수
            html += `
                <tr>
                    <td>${post.pno}</td>
                    <td><a href="view.jsp?pno=${post.pno}">${post.ptitle}</a></td>
                    <td>${post.mid}</td>
                    <td>${nowDate}</td>
                    <td>${post.pview}</td>
                </tr>`;
        }

        // 5) 누적한 HTML을 tbody에 적용
        tbody.innerHTML = html;

        // 6) 페이지 버튼(이전/번호/다음) 렌더링
        renderPageButtons(result);

    } catch (error) {
        // 네트워크 오류, JSON 파싱 오류 등 콘솔 출력
        console.error('[findAll:error]', error);
    }
};


// ----------------------------------------------------
// [작성일: 2025-08-25] 페이징 버튼 렌더링 함수
//   - response: 서버가 내려준 페이징 정보(currentPage, startBtn, endBtn, totalPage 등)
//   - 검색 조건 유지: key/keyword를 항상 링크에 포함
// ----------------------------------------------------
const renderPageButtons = (response) => {
    // 현재 페이지(숫자 변환)
    const currentPage = parseInt(response.currentPage);

    // 페이징 버튼이 들어갈 컨테이너 선택
    const box = document.querySelector('.pagebtnbox');

    // 검색 조건을 링크에 붙일 쿼리(인코딩 미사용 요청)
    const searchParams = `&key=${key}&keyword=${keyword}`;

    // 1) '이전' 버튼: currentPage가 1 이하이면 1로 고정
    let html = `
        <li class="page-item">
            <a class="page-link" href="post.jsp?cno=${cno}&page=${Math.max(currentPage - 1, 1)}${searchParams}">이전</a>
        </li>`;

    // 2) 페이지 번호 버튼: startBtn ~ endBtn 범위를 일반 for문으로 생성
    for (let i = response.startBtn; i <= response.endBtn; i++) {
        // 활성 페이지에는 'active' 클래스 부여(스타일은 CSS에서 정의)
        html += `
            <li class="page-item">
                <a class="page-link ${currentPage === i ? 'active' : ''}" href="post.jsp?cno=${cno}&page=${i}${searchParams}">
                    ${i}
                </a>
            </li>`;
    }

    // 3) '다음' 버튼: currentPage가 totalPage 이상이면 totalPage로 고정
    html += `
        <li class="page-item">
            <a class="page-link" href="post.jsp?cno=${cno}&page=${Math.min(currentPage + 1, response.totalPage)}${searchParams}">다음</a>
        </li>`;

    // 4) 완성된 버튼 HTML을 컨테이너에 삽입
    box.innerHTML = html;
};


// -----------------------------------------------------------------
// [작성일: 2025-08-25] 날짜 포맷팅 유틸리티
//   - 입력: 서버에서 받은 날짜 문자열(예: 'YYYY-MM-DD HH:mm:ss')
//   - 출력: '방금 전' / 'N분 전' / 'N시간 전' / 'YYYY-MM-DD'
//   - 비고: 클라이언트(브라우저) 로컬 타임존 기준으로 계산됨
// -----------------------------------------------------------------
const formatDateTime = (dateTimeString) => {
    // 현재 시각(Date)
    const now = new Date();

    // 게시물 작성 시각(Date) - 브라우저가 문자열을 파싱
    const postDate = new Date(dateTimeString);

    // 두 시각 차이(밀리초)
    const diffMillis = now - postDate;

    // 분/시간 단위로 변환
    const diffMinutes = diffMillis / (1000 * 60);
    const diffHours = diffMillis / (1000 * 60 * 60);

    // 1분 미만: '방금 전'
    if (diffMinutes < 1) return '방금 전';

    // 1시간 미만: 'N분 전'
    if (diffMinutes < 60) return `${Math.floor(diffMinutes)}분 전`;

    // 24시간 미만: 'N시간 전'
    if (diffHours < 24) return `${Math.floor(diffHours)}시간 전`;

    // 24시간 이상: 'YYYY-MM-DD' 형식
    const year = postDate.getFullYear();
    const month = String(postDate.getMonth() + 1).padStart(2, '0'); // 0~11 → +1
    const day = String(postDate.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
};


// ----------------------------------------------------
// [작성일: 2025-08-25] 검색 실행 핸들러
//   - 입력: 화면의 select(.key), input(.keyword) 값
//   - 동작: 검색 시 항상 page=1로 이동(검색 결과의 첫 페이지)
//   - 링크 이동 시 cno/key/keyword를 URL에 반영(인코딩 미사용 요청)
// ----------------------------------------------------
const onSearch = () => {
    // 화면에서 현재 선택된 검색 필드명/키워드 읽기
    const newKey = document.querySelector('.key').value;
    const newKeyword = document.querySelector('.keyword').value;

    // 검색 실행 시 항상 첫 페이지로 이동하도록 page=1 고정
    // 링크 이동 방식(풀리로드) 유지: 서버 사이드 렌더링 구조와 일관
    location.href = `/post/post.jsp?cno=${cno}&page=1&key=${newKey}&keyword=${newKeyword}`;
};


// ----------------------------------------------------
// [작성일: 2025-08-25] 초기 진입 시 목록 조회 수행
//   - 페이지 로드와 동시에 현재 파라미터 상태로 목록을 출력
// ----------------------------------------------------
findAll();
