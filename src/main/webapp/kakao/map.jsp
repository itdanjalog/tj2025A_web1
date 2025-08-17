<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>

	<!-- header.jsp 임포트 -->
	<jsp:include page="/header.jsp"></jsp:include>

    <div id="container" style="    display: flex    ; height: 90vh;">
    	<div id="map" style="width:80%;"></div>
        <div id="sidebar" style="width:20%;    overflow-y: auto;    padding: 10px;">
            <div class="약국명"> </div>
            <div class="전화번호"> </div>
            <div class="주소"> </div>
        </div>
    </div>
	<!-- JQUERY 라이브러리 : 카카오지도에 필요한 라이브러리 -->
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

	<!-- 카카오 지도의 클러스터 기능을 사용하기 위해 앱키 뒤 &libraries=clusterer 추가  -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1ac4a57d8a5927d34020a891fcdbbcbd&libraries=clusterer"></script>

	<script src="/js/kakao/map.js"></script>



</body>
</html>