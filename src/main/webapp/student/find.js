// * 
console.log( "find.js open")

// [1] 전체 조회 fecth 
const find = async ( ) => { // async ( ) => {} : 동기화 함수 
    // 1. 매개변수 준비[x]
    // 2. fetch option
    const option = { method : "GET" }
    // 3. fetch 동기화 실행
    // 통신 응답이 올때까지 JS는 기다려!! 
    const response = await fetch( "/student/find", option )
    // 4. 응답객체를 json 변환
    const data = await response.json();
    // --- // 강의 6월에 했던 코드 
    // 1. 어디에 : studentTbody에
    const studentTbody = document.querySelector("#studentTbody"); 
    // 2. 무엇을 : fetch로부터 받은 리스트를 HTML 구성 
    let html = "";
        // -- 반복문을 이용한 목록내 객체들을 HTML <tr> 로 구성하기
    for( let i = 0 ; i < data.length ; i++  ){
        const student = data[i];
        html += `<tr>
                    <td> ${ student.sno } </td>  <td> ${ student.sname } </td>
                    <td> ${ student.skor } </td> <td> ${ student.smath } </td>
                </tr>`
    }
    // 3. 출력 : .innerHTML
    studentTbody.innerHTML = html;
} // func end 
find() ; // ************* JS 실행시 최초 1번 자동 함수 호출********* 






