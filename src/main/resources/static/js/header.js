
// * JS 실행 확인 
console.log( "header.js open");

// [1] 내정보 요청해서 헤더 메뉴 나누기.
const myinfo = async() =>{
    const logMenu = document.querySelector('#log-menu'); // (1) 어디에
    let html ='' // (2) 무엇을
    try{
        // 1. fetch 실행 
        const option = { method : "GET"}
        const response = await fetch( "/member/info" , option );
        const data = await response.json();   console.log( data );
        // **비로그인시 응답자료가 null 이라서 .json() 타입변환 함수 에서 오류 발생해서 catch 로 이동 **
        // 2. [로그인중]로그인 했을때 정상 통신 fetch
        html += ` <li> <sapn> ${ data.mid } 님 </sapn> </li>
            <li> <a href="/member/info.jsp"> 내정보 </a> </li>
            <li> <a href="#" onclick="logout()"> 로그아웃 </a> </li>`
    }catch{
        // 2. [비로그인중]로그인 안했을때 비정상 통신 fetch 
        html += `<li> <a href="/member/login.jsp"> 로그인 </a></li>
               <li> <a href="/member/signup.jsp"> 회원가입 </a></li>`
    }
    logMenu.innerHTML = html; // (3) 출력
} // func end 
myinfo(); // header.jsp 열릴때마다 1번 최초 실행 

// [2] 로그아웃. 
const logout = async() =>{
    try{
        // 1. fetch 실행 
        const option = { method : "GET"}
        const response = await fetch( "/member/logout" , option );
        const data = await response.json();
        // 2. fetch 통신 결과
        if( data == true ){
            alert('로그아웃 했습니다');
            location.href="/index.jsp"; // 로그아웃 성공시 메인페이지로 이동
        }else{
            alert('비정상 요청 및 관리자에게문의');
        }
    }catch{ }
} // func end 


// [3] 클라이언트 웹소켓의 객체를 생성하고 서버소켓 으로 부터 접속 연동 시도.
const alarmSocket = new WebSocket('/alarmsocket');
// [4] 클라이언트 웹소켓이 서버소켓으로 부터 메시지를 받았을때
alarmSocket.onmessage = ( msgEvent ) => {
	console.log( msgEvent.data ); // 알람 메시지를 console.log() 띄우기
	// 부트스트랩을 이용한 '부트스트랩의 토스트'
	// 1. 어디에
	const alarmbox = document.querySelector('.alarmbox')
	// 2. 무엇을
	let html = `<div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
				  <div class="toast-header">
				    <strong class="me-auto">${ msgEvent.data }</strong>
				    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
				  </div>
				</div>`
	// 3. 출력
	alarmbox.innerHTML = html

} // f end


