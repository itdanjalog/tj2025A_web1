// [*] 수정할 게시물 번호 (URL 쿼리스트링에서 가져오기)
const pno = new URL(location.href).searchParams.get('pno');

// [*] 썸머노트 실행
$(document).ready(function() {
  $('#summernote').summernote({
    placeholder : '게시물 내용 입력해주세요.',
    height : 500,
    lang : 'ko-KR'
  });
});

// [1] 기존 게시물 정보 불러오기 (페이지 열렸을 때 최초 1번 실행)
const getPost = async () => {
    try {
        const response = await fetch(`/post/view?pno=${pno}`);
        const data = await response.json();

        // --- 응답받은 데이터를 각 input에 값으로 설정 ---
        document.querySelector('.cnoselect').value = data.cno;
        document.querySelector('.titleinput').value = data.ptitle;
        // 썸머노트 에디터에 내용 채우기
        $('#summernote').summernote('code', data.pcontent);

    } catch (e) {
        console.error(e);
    }
};
getPost(); // 페이지 열리면 함수 실행

// [2] 게시물 수정 요청 함수
const onUpdate = async () => {
    try {
        // 1. 수정할 데이터 가져오기
        const cno = document.querySelector('.cnoselect').value;
        const ptitle = document.querySelector('.titleinput').value;
        const pcontent = $('#summernote').summernote('code');

        // 2. 수정할 데이터를 객체로 구성 (pno 포함)
        const postData = {
            pno: pno,
            cno: cno,
            ptitle: ptitle,
            pcontent: pcontent
        };

        // 3. fetch 옵션 설정 (HTTP PUT 메소드 사용)
        const option = {
           method : 'PUT',
           headers : { 'Content-Type' : 'application/json' },
           body : JSON.stringify(postData)
        };

        // 4. 서버에 수정 요청
        const response = await fetch('/post', option);
        const result = await response.json();

        // 5. 결과 처리
        if (result) {
             alert('글 수정 성공');
             // 수정 성공 시 해당 게시물 조회 페이지로 이동
             location.href = `/post/view.jsp?pno=${pno}`;
        } else {
             alert('글 수정 실패 (작성자 본인만 가능)');
        }

    } catch (e) {
        console.error(e);
    }
};
