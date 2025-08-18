<%@ page language = "java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>


</head>
<body>
    
    <jsp:include page="/header.jsp"></jsp:include>
    
    <div id="container">
        <h3> 제품등록 </h3>
        <form id="productForm">
            제품명 : <input name="pname"/>  <br/>
            제품가격 : <input name="pprice" /> <br/>
            제품설명 : <textarea name="pcomment" /></textarea> <br/>
            제품이미지 : <input name="uploads" type="file" multiple /> <br/>

            <h4> 판매위치 </h4>
            <div id="map" style="width:100%;height:350px;"></div>

            <button type="button" onclick="onCreate()"> 제품등록 </button>
        </form>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1ac4a57d8a5927d34020a891fcdbbcbd"></script>

    <script src="/js/kakao/position.js"></script>
    <script src="/js/product/create.js"></script>

</body>
</html>