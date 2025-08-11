
console.log('update.js open');

// [1] 개별 조회( 수정하기 전 내용물 확인용 )
const boardFind = async ( ) => { console.log( "boardFind exe")
    // 1. 현재 게시물 번호 가져오기 
    const bno = new URLSearchParams(location.search).get('bno');
    // 2. fetch 이용한 bno의 게시물정보 요청
    const response = await fetch( `/board/find?bno=${ bno }`);
    const data = await response.json();
    // 3. 현재 게시물의 수정하기 전 게시물 내용을 출력
    document.querySelector('.bcontent').innerHTML = data.bcontent;
}
boardFind(); // JS 실행시 1번 실행 < 버튼 없으므로 >

// [2] 개별 수정 
const boardUpdate = async ( ) => { console.log( "boardUpdate exe");
    // 1. 현재 수정할 게시물 번호 가져오기 
    const bno = new URLSearchParams( location.search ).get('bno');
    // 2. 수정할 입력받은 값 가져오기 
    const bcontent = document.querySelector('.bcontent').value;
    // 3. 객체화 
    const obj = { "bno" : bno , "bcontent" : bcontent };
    // 4. fetch 옵션 
    const option = {
        method : "PUT",
        headers : { "Content-Type" : "application/json"},
        body : JSON.stringify( obj )
    }
    // 5. fetch 실행 
    const response = await fetch( "/board" , option );
    const data = await response.json();
    // 6. fetch 결과
    if( data == true ){
        alert('수정 성공');
        location.href=`/board/view.jsp?bno=${ bno }` // view.jsp 와 update.jsp 는 bno를 쿼리스트링 매개변수 사용
    }else{
        alert('수정 실패');
    }
}




















