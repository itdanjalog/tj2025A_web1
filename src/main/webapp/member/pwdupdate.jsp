<%@ page language = "java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <link rel="stylesheet" href="/css/member/pwdupdate.css">
</head>
<body>
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <h3> 비밀번호 수정 페이지 </h3>
        <div> 기존 패스워드 : <input type="password" class="oldpwd" />     </div>
        <div> 새로운 패스워드 : <input type="password" class="newpwd" />   </div>
        <button type="button" onclick="onPwdUpdate()"> 비밀번호 변경 </button>
    </div>

    <script src="/js/member/pwdupdate.js"></script>
</body>
</html>