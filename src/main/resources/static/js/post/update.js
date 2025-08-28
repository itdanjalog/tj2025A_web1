console.log( "update.js open" );

// [*] 썸머노트 실행
// $ : jquery( JS확장 라이브러리) 문법 , 
$(document).ready(function() {
    $('#summernote').summernote({
        lang: 'ko-KR' ,  // 썸머노트 메뉴들을 한글화 속성 
        minHeight : 300 , // 썸머노트 구역 최소높이
    });
});

// [*] URL 주소상 쿼리스트링 PNO 가져오기 
const params = new URL(location.href).searchParams;
const pno = params.get('pno'); // 수정할 게시물번호 

// [1] 기존 게시물 정보 가져오기 
const getPost = async()=>{
    const response = await fetch( `/post/view?pno=${pno}` );
    const data = await response.json();
    document.querySelector('.cno').value = data.cno;
    document.querySelector('.ptitle').value = data.ptitle; // value vs innerHTML
    document.querySelector('.note-editable').innerHTML = data.pcontent; // .note-editable 썸머노트가 만들어낸 HTML의 div
}

getPost();

// [2] 수정처리 
const updatePost = async () => {
    // 1. 수정할 자료 준비 
    const cno = document.querySelector('.cno').value
    const ptitle = document.querySelector('.ptitle').value
    const pcontent = document.querySelector('.pcontent').value;
    const obj = { cno , ptitle , pcontent , pno  }
    // 2. 
    const option = { 
        method : "PUT", 
        headers : { "content-type" : "application/json"} , 
        body : JSON.stringify(obj) 
    }
    const response = await fetch( '/post' , option );
    const data = await response.json();
    if( data == 0 ){ alert('수정실패'); }
    else{ alert('수정성공'); location.href = `view.jsp?pno=${pno}` }
}
