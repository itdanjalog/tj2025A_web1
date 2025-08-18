
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
                <div class="제품명" > ${ product.pno } </div>
                <div class="제품명" > <a href="#" onclick="getProductDetail( ${ product.pno } )" > ${ product.pname } </a> </div>
                <div class="가격"> ${ product.pprice  } </div>
                <div class="설명"> ${ product.pcomment } </div>
			</div>
		`
	})


	sidebar.innerHTML = html;

}

getAPI();

const getProductDetail = async( pno ) => {

	const response = await fetch( `/product?pno=${ pno }` );
	const product = await response.json();
	
	let html2 = ``;
	const sidebar = document.querySelector('#sidebar')

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
}