
// [*] 썸머노트 실행
// $ : jquery( JS확장 라이브러리) 문법 , 
$(document).ready(function() {
    $('#summernote').summernote({
        lang: 'ko-KR' ,  // 썸머노트 메뉴들을 한글화 속성 
        minHeight : 300 , // 썸머노트 구역 최소높이
        placeholder : '여기에 내용 입력해주세요', // 가이드 라인
    });
});

console.log( "write.js open");

// [1] 글쓰기 요청
const onWrite = async()=>{
    // 1. 첨부파일 있는 게시물 : multipart/form vs 첨부파일은 없고 내용에 에디터( 텍스트+이미지 등) 포함
    const cno = document.querySelector('.cno').value;
    const ptitle = document.querySelector('.ptitle').value;
    const pcontent = document.querySelector('.pcontent').value;
    const obj = { cno , ptitle , pcontent };    console.log( obj );
    const option = {
        method : "POST",
        headers : { "content-type" : "application/json"},
        body : JSON.stringify(obj)
    }
    try{
        const response = await fetch( "/post" , option );
        const data = await response.json();
        if( data > 0 ){
            alert("등록성공");
        }else{
            alert("등록실패");    
        }
    }catch( e ){ console.log(e); }
}