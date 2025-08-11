
console.log( "write.js open");

// [1] 등록 
const boardWrite = async ( ) => { console.log( "boardWrite exe ")
    // 1. 입력받은 데이터 가져오기 
    const bcontent = document.querySelector('.bcontent').value;
    const bwriter = document.querySelector('.bwriter').value;
    // 2. 입력받은 데이터를 객체화 한다. // 속성명 과 속성값의변수명이 같으면 속성명 생략가능
    const obj = { 'bcontent' : bcontent , 'bwriter' : bwriter }; 
    // 3. fetch 옵션
    const option = {
        method : "POST" ,                                       // HTTP METHOD(보내는방법) 
        headers : { "Content-Type" : "application/json" },      // HTTP HEADER(부가정보/설정)
        body : JSON.stringify( obj )                            // HTTP BODY(보내는내용물)
    }
    // 4. fetch 실행 
    const response = await fetch( "/board" , option );
    // 5. 응답자료 타입변환 
    const data = await response.json();
    // 6. 응답자료 확인 
    if( data == true ){
        alert("글쓰기 성공");
        location.href="/board/list.jsp" // 페이지 이동 : location.href=""  vs <a href=""></a>
    }else{
        alert("글쓰기 실패");
    }
} // func end 
