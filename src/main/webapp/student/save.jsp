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
        <h3> 등록페이지 </h3>
        이름 : <input type="text" class="sname" /> <br/>
        국어점수 : <input type="text" class="skor" /> <br/>
        수학점수 : <input type="text" class="smath" /> <br/>
        <button type="button" onclick="save()"> 등록 </button>
    </div>
    <script src="/student/save.js"></script>
</body>
</html>