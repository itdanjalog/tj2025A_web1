<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정</title>

    <!-- 썸머노트 라이브러리 CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.9.1/summernote-bs5.min.css"  />

</head>
<body>
    <jsp:include page="/header.jsp"></jsp:include>
    <div class="container">
       <h3>게시물 수정</h3>
       <form>
          <select class="cnoselect form-select mb-3">
             <option value="1" > 뉴스 </option>
             <option value="2" > 이벤트 </option>
             <option value="3" > FAQ </option>
             <option value="4" > 튜토리얼 </option>
             <option value="5" > 사용자 리뷰 </option>
          </select>
          <input type="text" class="titleinput form-control mb-3" placeholder="제목을 입력해주세요." />
          <textarea id="summernote" class="contentinput form-control" ></textarea>

          <div class="d-flex justify-content-end mt-3">
            <button onclick="onUpdate()" type="button" class="btn btn-primary"> 글 수정 </button>
            <a href="/post/view.jsp?pno=<%= request.getParameter("pno") %>" class="btn btn-secondary ms-2">취소</a>
          </div>
       </form>
    </div>

    <!-- JQUERY 라이브러리 JS -->
    <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
    <!-- 썸머노트 라이브러리 JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.9.1/summernote-bs5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.9.1/lang/summernote-ko-KR.min.js"></script>

    <!-- 수정 페이지 전용 JS 파일 로드 -->
    <script src="/js/post/update.js" type="text/javascript"></script>
</body>
</html>
