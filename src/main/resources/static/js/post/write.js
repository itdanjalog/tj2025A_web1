// [*] 썸머노트 실행
$(document).ready(function() {
  $('#summernote').summernote({
    placeholder : '게시물 내용 입력해주세요.',
    height : 500,
    lang : 'ko-KR'
  });
});

// [1] 글쓰기 요청 메소드 (동기식)
const onWrite = async () => {
    try {
        // 1. 입력받은 값들을 가져오기
        const cno = document.querySelector('.cnoselect').value;
        const ptitle = document.querySelector('.titleinput').value;
        // Summernote 에디터의 내용을 가져오는 올바른 방법
        const pcontent = $('#summernote').summernote('code');

        console.log(pcontent);

        // 2. 전송할 데이터를 객체로 구성
        const postData = {
            cno: cno,
            ptitle: ptitle,
            pcontent: pcontent
        };

        // 3. fetch 옵션 설정
        const option = {
           method : 'POST',
           headers : { 'Content-Type' : 'application/json' },
           body : JSON.stringify(postData)
        };

        // 4. 서버에 요청하고 응답을 기다림
        const response = await fetch('/post', option);
        const result = await response.json();

        console.log(result);

        // 5. 결과에 따른 처리
        if (result) {
             alert('글쓰기 성공');
             location.href = `post.jsp?cno=${cno}`;
        } else {
             alert('글쓰기 실패');
        }

    } catch (e) {
        console.error(e);
    }
};
