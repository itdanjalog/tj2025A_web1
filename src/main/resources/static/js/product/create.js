
const myPosition = async () =>{
		const position = await new Promise((resolve, reject) => {
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
	return position;
}
const getMap = async () => {


	const position = await myPosition();

	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng( position.coords.latitude, position.coords.longitude ),
            level: 3 // 지도의 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 지도를 클릭한 위치에 표출할 마커입니다
    var marker = new kakao.maps.Marker({
        // 지도 중심좌표에 마커를 생성합니다
        position: map.getCenter()
    });
    // 지도에 마커를 표시합니다
    marker.setMap(map);

    // 지도에 클릭 이벤트를 등록합니다
    // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

        // 클릭한 위도, 경도 정보를 가져옵니다
        var latlng = mouseEvent.latLng;

        // 마커 위치를 클릭한 위치로 옮깁니다
        marker.setPosition(latlng);

    //    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
    //    message += '경도는 ' + latlng.getLng() + ' 입니다';

        위도경도 = latlng;

    //    var resultDiv = document.getElementById('clickLatlng');
    //    resultDiv.innerHTML = message;

    });

}
getMap();




let 위도경도 = '';


// [1] 회원가입 요청 함수 
const onCreate = async( ) =>{
	// 1. form 를 한번에 가져오기. application/json ---> multipart/form-data( 첨부파일 )
		// * form-data 로 전송할 경우에는 속성명들을 'name' 속성으로 사용된다.
	const signupform = document.querySelector('#productForm');  // form 전체 가져오기 
		console.log( signupform );
	// *. Fetch 함수 이용한 'multipart/form-data'(대용량) 타입으로 전송하는 방법 
		// (1) 전송 할 폼을 바이트(바이너리/스트림) 데이터로 변환 , FormData 클래스 , new FormData( 폼DOM );
	const signupformData = new FormData( signupform );
			//signupformData.append( "속성명" , 값 ); // 만일 html 폼에 없는 데이터를 폼데이터에 추가하는 방법
		// (2) fetch 옵션 , content-type 생략하면 자동으로 'multipart/form-data' 적용된다.

        signupformData.append("plat" , 위도경도.getLat()); //위도 - lat , y
        signupformData.append("plng" , 위도경도.getLng()); // 경도 - lon , x
        console.log(signupformData)

	const option = { 
		method : 'POST' , 
        //headers : { "Content-Type" : "multipart/form-data"},
	 	body : signupformData 
	} 
		// (3) fetch 요청 과 응답 
	
    const response = await fetch( '/product/create' , option );
    const data = await response.json();
    if( data > 0 ){ alert('등록성공'); }
    else{ alert('등록실패'); }

} // f end 