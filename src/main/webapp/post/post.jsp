<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>

	<jsp:include page="/header.jsp"></jsp:include>

    <div class="container"> <!-- container: 중앙 정렬 / my-4: 상하 여백 -->

        <!-- 글작성 버튼 -->
        <div class="">
            <button onclick="location.href='write.jsp'" class="">글작성</button>
        </div>

        <!-- 게시글 목록 테이블 -->
        <table class="" > <!-- table-hover: 행 hover 효과 / table-bordered: 테두리 -->
            <thead class="" > <!-- table-light: 헤더 배경 색상 -->
                <tr>
                    <th >번호</th>
                    <th >제목</th>
                    <th >작성자</th>
                    <th >작성일</th>
                    <th >조회수</th>
                </tr>
            </thead>
            <tbody class="postlist">
                <!-- 동적으로 게시글 목록 출력 영역 -->
            </tbody>
        </table>

        <!-- 페이지네이션 영역 -->
        <div style="width: 300px;">
            <ul class="pagebtnbox" style="display: flex; justify-content: space-between;">
                <!-- 동적 페이지 버튼 영역 -->
            </ul>
        </div>

        <!-- 검색 영역: 중앙 정렬 + 사이즈 축소 -->
        <div class="" style="display: flex; "> <!-- justify-content-center: 가로 중앙 정렬 -->
            <div class="" > <!-- 너비 지정으로 사이즈 축소 -->
                <select class="key">
                    <option value="ptitle">제목</option>
                    <option value="pcontent">내용</option>
                </select>
            </div>
            <div class="" > <!-- 입력창 너비 지정 -->
                <input type="text" class="keyword" placeholder="검색어 입력">
            </div>
            <div class="">
                <button type="button" class="" onclick="onSearch()">검색</button>
            </div>
        </div>

    </div>

	<script src="/js/post/post.js" type="text/javascript"></script>

</body>
</html>


