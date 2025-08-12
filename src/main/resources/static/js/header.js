//[1] 로그인 정보 요청 함수 
const getLoginInfo = async ( ) => {

	
	
	let loginmenu = document.querySelector('.loginmenu'); // (1)로그인 메뉴를 출력할 구역 가져오기 
	let html = ``; // (2) html 변수 선언 
	
	try{
	const option = { method : 'GET' } 

	const response = await fetch( '/member/info' , option );
	const data = await response.json();
	console.log('로그인상태');
		// (3) 각 상태에 따라 로그인 메뉴 구성
		html += `<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
						${ data.mid }님
					</a>
					<ul class="dropdown-menu">
						<li class="nav-item"> <a class="nav-link" href="/member/info.jsp">마이페이지</a> </li>
						<li class="nav-item"> <a class="nav-link" href="#" onclick="onLogOut()">로그아웃</a> </li>
					</ul>
				</li>
				`

	}catch{
		console.log('비로그인상태');
		html += `<li class="nav-item"> <a class="nav-link" href="/member/login.jsp">로그인</a> </li>
				<li class="nav-item"> <a class="nav-link" href="/member/signup.jsp">회원가입</a> </li>`
	}
	// (4) 구성한 메뉴들을 innerHTML 한다.
	loginmenu.innerHTML = html;

}; // f end 
getLoginInfo(); // JS가 열렸을때 최초 1번 실행

// [2] 로그아웃 요청 함수 
const onLogOut = ( ) => {
	const option = { method : 'GET' }
	fetch( '/member/logout' , option )
		.then( response =>  response.json() )
		.then( data => {
			if( data==true){ 
				alert('로그아웃합니다.'); 
				location.href="/member/login.jsp";
			}
		})
		.catch( e => {console.log(e); })
} // f end 






























































