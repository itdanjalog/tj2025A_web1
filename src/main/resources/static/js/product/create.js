
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


// [1] 제품등록 요청 함수
const onCreate = async( ) =>{
	const productForm = document.querySelector('#productForm');
		console.log( productForm );
	const productFormData = new FormData( productForm );
        productFormData.append("plat" , 위도경도.getLat()); //위도 - lat , y
        productFormData.append("plng" , 위도경도.getLng()); // 경도 - lon , x
        console.log(productFormData)

	const option = { 
		method : 'POST' , 
        //headers : { "Content-Type" : "multipart/form-data"},
	 	body : productFormData
	} 
		// (3) fetch 요청 과 응답 
	
    const response = await fetch( '/product/create' , option );
    const data = await response.json();
    if( data > 0 ){ alert('등록성공'); }
    else{ alert('등록실패'); }

} // f end 