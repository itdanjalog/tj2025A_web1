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

// ============= [4] 마커 클러스터러 사용하기 ==============//
//  https://apis.map.kakao.com/web/sample/basicClusterer/
// + jQuery(JS라이브러리) +&libraries=clusterer
const getMap = async () => {


	const position = await myPosition();

	// (1) 카카오지도 중심좌표( 지도 시작 좌표 ) 와 확대레벨 설정
   var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
       center : new kakao.maps.LatLng( position.coords.latitude, position.coords.longitude ), // 지도의 중심좌표 // 활용 : Geolocation API = 접속된 유저의 좌표
       level : 8 // 지도의 확대 레벨
   });

   // (2) 마커 클러스터( 여러개 마커들을 하나의 도형 )
   var clusterer = new kakao.maps.MarkerClusterer({
       map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
       averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
       minLevel: 1 // 클러스터 할 최소 지도 레벨
   });

		// 카카오지도 샘플 : { positions : [ { "lat" : "" , "lng" : "" } , { "lat" : "" , "lng" : "" }  ]  }
		// 공공데이터 : { data : [ { 위도 : "" , 경도 : "" } , { 위도 : "" , 경도 : "" } , { 위도 : "" , 경도 : "" } ] }
		// 인천 동구 약국 -> https://www.data.go.kr/data/15051492/fileData.do
	const response = await fetch( '/product/list' );
	const data = await response.json();
			// for vs .forEach( ( 반복변수명 ) => { } )  vs  .map( (반복변수명)=>{ return } )
			let markers = data.map( ( product ) => {
				// 1개 마커 생성 후 변수에 저장
				let marker = new kakao.maps.Marker({position : new kakao.maps.LatLng(product.plat, product.plng)});

				// 위 변수의 생성된 마커의 클릭 이벤트 등록
				kakao.maps.event.addListener(marker, 'click', function() {
					  // alert( `${ position.약국명 } 클릭 했군요.` );
					
					  let html2 = ``;
					  const sidebar = document.querySelector('#sidebar')

					  // 클릭한 마커 약국의 정보를 특정한(사이드바) html 에 대입하기.
					//   document.querySelector('.약국명').innerHTML = position.약국명;
					//   document.querySelector('.전화번호').innerHTML = position.전화번호;
					//   document.querySelector('.주소').innerHTML = position.소재지도로명주소;

                    let imghtml = ``;
                    if( product.images != null ){
                        product.images.forEach( ( image ) => {
                            imghtml += `<img style="width: 100px;  object-fit: cover;height: 100px;"  src="/upload/${  image  }" />`
                        })
                    }else{
                        imghtml += `<img style="width: 100px;  object-fit: cover;height: 100px;"  src="/upload/default.png" />`
                    }


						html2 += `
							<div style="magin-bottom:100px;">
								<button type="button" onclick="getAPI()">전체보기</button>
                                <div class="이미지"> 
                                ${ imghtml }
                                </div>
                                <div class="제품명"> ${ product.pno } </div>
                                <div class="제품명"> ${ product.pname } </div>
                                <div class="가격"> ${ product.pprice  } </div>
                                <div class="설명"> ${ product.pcomment } </div>
							</div>
						`
					  sidebar.innerHTML = html2;
					
				});

				// 위 변수의 생성된 마커 이벤트 등록후 반환/리턴
				return marker;
			}); // map end

			// 클러스터러에 (마커배열)마커들을 추가합니다
			clusterer.addMarkers(markers);

}
getMap();



const getAPI = async() =>{
    
	const response = await fetch( '/product/list' );
	const data = await response.json();
	console.log( data);
	const sidebar = document.querySelector('#sidebar')

	let html = ''

	data.forEach( ( product ) => {
		html += `
			<div style="magin-bottom:100px;">
                <div class="이미지"> 
                    <img style="width: 100px;  object-fit: cover;height: 100px;" src="/upload/${ product.images == null ? 'default.png' : product.images[0]  }" />
                </div>
                <div class="제품명"> ${ product.pno } </div>
                <div class="제품명"> ${ product.pname } </div>
                <div class="가격"> ${ product.pprice  } </div>
                <div class="설명"> ${ product.pcomment } </div>
			</div>
		`
	})


	sidebar.innerHTML = html;

}

getAPI();
