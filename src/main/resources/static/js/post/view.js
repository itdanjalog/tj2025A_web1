// [*] 조회할 pno(게시물번호) 가져오기
const pno = new URL(location.href).searchParams.get('pno');

// [1] 게시물 1개 조회 (동기식)
const findBypno = async () => {
    try {
        const response = await fetch(`/post/view?pno=${pno}`);
        const data = await response.json();

        console.log(data);
        document.querySelector('.titlebox').innerHTML = data.ptitle;
        document.querySelector('.contentbox').innerHTML = data.pcontent;
        document.querySelector('.midbox').innerHTML = data.mid;
        document.querySelector('.viewbox').innerHTML = data.pview;
        document.querySelector('.datebox').innerHTML = data.pdate;

        // 작성자 본인일 경우 버튼 출력
        if (data.host) {
            document.querySelector('.btnbox').innerHTML = `
                <button type="button" class="btn btn-warning" onclick="location.href='update.jsp?pno=${pno}'">수정</button>
                <button type="button" class="btn btn-danger" onclick="onDelete()">삭제</button>
            `;
        }



    } catch (e) {
        console.error(e);
    }
};

// [2] 댓글 쓰기 (동기식)
const onRplyWrite = async () => {
    try {
        // 1. 입력받은 값 가져오기
        const rcontentinput = document.querySelector('.rcontentinput');
        const rcontent = rcontentinput.value;

        // 2. 보낼 자료를 객체로 만들기
        const obj = { rcontent: rcontent, pno: pno };

        // 3. fetch 옵션 설정
        const option = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(obj)
        };

        // 4. 서버에 요청하고 응답 대기
        const response = await fetch('/post/reply', option);
        const result = await response.json();

        // 5. 결과 처리
        if (result) {
            alert('댓글쓰기 성공');
            rcontentinput.value = ''; // 입력창 초기화
            replyFindAll(); // 댓글 목록 새로고침
        } else {
            alert('댓글쓰기 실패: 로그인 후 이용 가능합니다.');
        }

    } catch (e) {
        console.error(e);
    }
};

// [3] 현재 게시물의 댓글 전체 조회 (동기식)
const replyFindAll = async () => {
    try {
        const response = await fetch(`/post/reply?pno=${pno}`);
        const data = await response.json();

        console.log(data);
        const replybox = document.querySelector('.replybox');
        let html = ``;

        data.forEach(reply => {
            html += `<div class="card mt-3">
                       <div class="card-header">
                         ${reply.mid}
                       </div>
                       <div class="card-body">
                          ${reply.rcontent}
                       </div>
                     </div>`;
        });
        replybox.innerHTML = html;

    } catch (e) {
        console.error(e);
    }
};

// [NEW] 게시물 삭제 함수
const onDelete = async () => {
    if (!confirm('정말 삭제하시겠습니까?')) {
        return;
    }
    try {
        const option = { method: 'DELETE' };
        const response = await fetch(`/post?pno=${pno}`, option);
        const result = await response.json();

        if (result) {
            alert('삭제되었습니다.');
            // 삭제 후 목록 페이지로 이동 (1번 카테고리로 가정)
            location.href = 'post.jsp?cno=1';
        } else {
            alert('삭제에 실패했습니다. (작성자 본인만 가능)');
        }
    } catch (e) {
        console.error(e);
    }
};


// 페이지가 열리면 게시물 상세 정보와 댓글 목록을 가져옴
findBypno();
replyFindAll();
