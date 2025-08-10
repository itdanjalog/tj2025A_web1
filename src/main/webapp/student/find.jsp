<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
</head>
<body>

    <jsp:include page="/student/header.jsp"></jsp:include>
    <div>
        <h3> 전체 조회 페이지 </h3>
        <table border="1">
            <thead>
                <tr>    
                    <th> 번호 </th> <th> 이름 </th> 
                    <th> 국어 </th> <th> 수학 </th>   
                </tr>
            </thead>
            <tbody id="studentTbody">
            </tbody>
        </table>
    </div>

    <script src="/student/find.js"></script>

</body>
</html>
<!-- 
    1. .JSP 파일 생성한다.
    2. 파일내 최상단(첫번째 줄) 에서 컨트롤+스페이바 이용하여 html 샘플생성한다.
    3. 파일내 최상단(첫번재 줄) 에 JSP 설정 코드 작성한다.
-->