console.log( 'signup.js open');

let signPass = [ false , false ];
// [1] 회원가입 요청 함수 
const onSignUp = ( ) =>{
	if( signPass[0] == false || signPass[1] == false ) return;
		// * form-data 로 전송할 경우에는 속성명들을 'name' 속성으로 사용된다.
	const signupform = document.querySelector('#signupform');  // form 전체 가져오기 
		console.log( signupform );
	// *. Fetch 함수 이용한 'multipart/form-data'(대용량) 타입으로 전송하는 방법 
		// (1) 전송 할 폼을 바이트(바이너리/스트림) 데이터로 변환 , FormData 클래스 , new FormData( 폼DOM );
	const signupformData = new FormData( signupform );
			//signupformData.append( "속성명" , 값 ); // 만일 html 폼에 없는 데이터를 폼데이터에 추가하는 방법
		// (2) fetch 옵션 , content-type 생략하면 자동으로 'multipart/form-data' 적용된다.


	const obj = {
		mid : signupformData.get('mid'),
		mpwd : signupformData.get('mpwd'),
		mname : signupformData.get('mname'),
		mphone : signupformData.get('mphone'),
	}


	const option = { 
		method : 'POST',
		headers : { 'Content-Type' : 'application/json'},
		body : JSON.stringify( obj )
	}
		// (3) fetch 요청 과 응답 
	fetch( '/member/signup' , option )
		.then( response => response.json() ) // 응답 자료를 'application/json' 타입으로 변환 
		.then( data => { 
			if( data > 0 ){ alert('회원가입 성공'); location.href="/member/login.jsp"; } //회원가입 성공시 메시지후 로그인페이지 이동
			else{ alert('회원가입 실패'); }
		}) // 응답 자료 
		.catch( error => { console.log(error); }) // fetch 통신 간 오류 발생시
} // f end 


const 아이디중복검사 = async () => {
	const mid = document.querySelector("#floatingInput1").value;

	if( mid.length < 8 ){
		document.querySelector(".idcheck").innerHTML = "<span style='color:red;'>8글자 이상으로 해삼.</span>";
		signPass[0] = false;
		return;
	}

	
	
	const response = await fetch(`/member/checkid?mid=${mid}&type=mid`);
	const data = await response.json();
	if( data == true  ){
		document.querySelector(".idcheck").innerHTML = "<span style='color:red;'>중복된 아이디입니다.</span>";
		signPass[0] = false;

	}else{
		
		document.querySelector(".idcheck").innerHTML = "<span style='color:green;'>사용가능한 아이디입니다.</span>";
		signPass[0] = true;
	}
}

const 연락처검사 = async () => {
	const mid = document.querySelector("#floatingInput5").value;

	if( mid.length != 13 ){
		document.querySelector(".phonecheck").innerHTML = "<span style='color:red;'> - 포함 13자리 입니다. </span>";
		signPass[1] = false;
		return;
	}

	
	
	const response = await fetch(`/member/checkid?mid=${mid}&type=mid`);
	const data = await response.json();
	if( data == true  ){
		document.querySelector(".phonecheck").innerHTML = "<span style='color:red;'>중복된 연락처 입니다. .</span>";
		signPass[1] = false;

	}else{
		
		document.querySelector(".phonecheck").innerHTML = "<span style='color:green;'>사용가능한 연락처 입니다.</span>";
		signPass[1] = true;

	}

}
