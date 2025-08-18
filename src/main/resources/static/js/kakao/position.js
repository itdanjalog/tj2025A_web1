const myPosition = async () =>{

	// 📌 position 변수를 선언하고, Promise가 처리(resolve/reject)될 때까지 기다린 후 그 결과를 담음
// 'await' 키워드를 사용했으므로, 이 줄이 끝나야 다음 코드로 넘어감
// Promise는 '비동기 작업'을 표현하는 객체
//   - 성공 시 resolve() 호출 → then() 또는 await에 결과 전달
//   - 실패 시 reject() 호출 → catch() 또는 try-catch에서 처리
		const position = await new Promise((resolve, reject) => {

			/*
				navigator.geolocation.getCurrentPosition() 설명:
				- 첫 번째 매개변수: 성공 시 호출할 콜백 함수
				- 두 번째 매개변수: 실패 시 호출할 콜백 함수
				- 세 번째 매개변수: 옵션 객체

				여기서는 첫 번째 매개변수로 resolve, 두 번째 매개변수로 reject를 전달
				=> 위치 정보 요청이 성공하면 resolve(위치데이터) 실행
				=> 위치 정보 요청이 실패하면 reject(에러정보) 실행

				즉, getCurrentPosition의 콜백 기반 방식을
				'Promise' 형태로 감싸서 await 사용이 가능하도록 만든 것
			*/
			navigator.geolocation.getCurrentPosition(
				resolve, // ✅ 위치 가져오기 성공 시 → Promise의 resolve() 실행
				reject,  // ❌ 위치 가져오기 실패 시 → Promise의 reject() 실행
				{
					enableHighAccuracy: true, // 가능한 가장 정확한 위치(배터리 소모 증가 가능)
					timeout: 5000,            // 5초 안에 위치 못 가져오면 실패 처리
					maximumAge: 0             // 캐시된 위치 정보 사용 안 함(항상 새로 요청)
				}
			);
		});

        console.log("위도:", position.coords.latitude);
        console.log("경도:", position.coords.longitude);
        console.log("정확도(m):", position.coords.accuracy);


	return position;
}