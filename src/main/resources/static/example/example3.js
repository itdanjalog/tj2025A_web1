console.log("example3.js open");

// (1) 비동기 fetch : 여러 흐름
// 람다식함수 : const func1 = ( ) => { }
// 선언적함수 : function func1( ){ }
const func1 = ( ) => {
    console.log( '[1] fetch 전 ')
    //(fetch) fetch가 실행되고 결과(response)가 오기전에 다음코드로 이동 , 즉] JS는 *응답* 기다리지 않는다.
    const option = { method : "GET" }
    fetch( "/day06/exam1" , option )
        .then( response => response.json() )
        .then( data => {
            console.log( data );
            console.log( '[2] fetch 통신 결과')
        })
        .catch( error => { console.log( error )})
    console.log( '[3] fetch 후 ')
    // 코드 실행 예측 : [1] -> [2] -> [3] , 결과 : [1] -> [3] -> [2]
} // func1 end

// (2) 동기 fetch : 하나의 흐름
const func2 = async ( ) => {
    console.log( '[1] fetch 전');
    // (fetch)
    const option = { method : "get" }
    try{
        const response = await fetch( "/day06/exam1" , option )
        const data = await response.json();
        console.log( data );
        console.log( '[2] fetch 통신 결과');
    }catch( error ){
        console.log( error );
    }
    console.log( '[3] fetch 후 ')
}















