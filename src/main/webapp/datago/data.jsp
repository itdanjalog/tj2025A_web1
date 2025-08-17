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

    <div id="container" >
        <div>
            <h3> 진위 여부 확인 </h3>
            <input class="bno" placeholder="-없이 사업자번호"/> 
            <button onclick=" dataAPI2()"> 확인 </button>
            <div id="result">

            </div>
        </div> 

        <div>
            <h3> 인천광역시 주유소 현황</h3>
            <table border="1">
                <thead>
                    <tr>
                        <th>상호</th>
                        <th>업종</th>
                        <th>연번</th>
                        <th>전화번호</th>
                        <th>주소</th>
                    </tr>
                </thead>
                <tbody id="dataTbody">

                </tbody>
            </table>
        </div>
    </div>

	<script src="/js/datago/data.js"></script>



</body>
</html>