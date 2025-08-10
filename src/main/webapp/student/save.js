
// * 
console.log( 'save.js open' )

// [1] 등록 fetch , 동기
// 1) const save = () => { }
// 2) function save(){ }
const save = async () => {
    // 1. 입력받은 값을 가져오기 
    const snameInput = document.querySelector('.sname');
    const skorInput = document.querySelector('.skor');
    const smathInput = document.querySelector('.smath');
    const sname = snameInput.value;
    const skor = skorInput.value;
    const smath = smathInput.value;
    // 2. 입력받은 값을 객체화  , 속성명과 속성값변수명이 같으면 *둘중 하나 생략*가능
    const object = { sname : sname , skor : skor , smath : smath }
    // 3. fetch option , get/delete vs post/put
    const option = {
        method : "POST" ,
        headers : { "Content-Type" : "application/json"},
        body : JSON.stringify( object )
    } // option end 
    // 4. fetch 동기 실행 
    const response = await fetch( "/student/save" , option );
    const data = await response.json();
    // 5. 만약에 결과가 true이면
    if( data == true ){ 
        alert('등록 성공');
        location.href="/student/find.jsp"; // JS 에서 페이지 전환 
    }else{  alert('등록 실패'); }
} // func end 














