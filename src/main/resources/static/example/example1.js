// 인텔리제이 커뮤니티에서는 JS 지원 안한다.
// [1] 인텔리제이 얼티메이터 유료 버전 , 비권장
// [2] 인텔리제이 플러그인에서 모든 언어 자동AI 추천 , 교육상 비권장
// [3] VSCODE( html/css/js) 켜기
// : VSCODE 상단메뉴 -> 파일 -> 폴더열기 -> 스프링프로젝트 폴더 선택
console.log("JS실행2");
// VSCODE 코드 작성시 인텔리제이 에도 +반영+ 된다.
// VSCODE 에서 HTML/JS 코드를 작성하고 SPRING 서버 실행은 인텔리제이 에서 하자.

// 1] JS 자료/값 
console.log( 3 );       console.log( 3.14 );        console.log( true );
console.log( null );    console.log( undefined );   
console.log( "안녕1" ); console.log('안녕2');       console.log(`안녕3`); //`백틱
console.log( [ 3 , 3.14 , true , '안녕4' ] ); // 리스트/배열 
console.log( function 함수명(){} ); // 함수 
console.log( {"속성명1" : 3 , "속성명2" : "안녕5" } ); // 객체 == JSON 

// 2] 변수/상수 선언 키워드 : let , const 
let var1 = 15;
const var2 = '유재석';

// 3] 함수 선언
function func1( a , b ){
    console.log( 'func1 exe ');
    let c = a + b; 
    return c;
} // func end 

// 4] 함수 실행1 , JS 실행 : 함수명( 인수값1,인수값2 );
let result = func1( 10 , 20 );      console.log( result );
// 4] 함수 실행2 , HTML 실행 : <마크업 onClick="함수명(인수값1,인수값2)" >

// 5] 익명 함수 선언 , 함수명이 없는 함수. 단] 주로 변수/상수 에 저장된다. 
const func2 = function( a , b ){  console.log( 'func2 exe' ); } // func end
func2( 10 , 20 ); // 익명함수 실행 

// 6] **람다식(화살표)** 함수 선언 , function키워드 를 => 화살표 이용한 표현식 , 단] 일회성함수 
const func3 = ( a , b ) => {  console.log( 'func3 exe' ); }
func3( 10 , 20 ); // 람다식함수 실행 
