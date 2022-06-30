<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/head.jsp"/>
<title>MainStream!-글작성</title>
<link rel="stylesheet" href="../css/style.css">
<style>
	textarea {white-space: pre-line;} /* 공백을 HTML규칙을 벗어나게 함 */
</style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<main class="write-main">
	<form method="post" enctype="multipart/form-data">
		<div class="top"></div>
        <div class="title"><input type="text" name="title" maxlength="30"></div>
        <div class="content"><textarea cols="90" rows="20" name="content"></textarea></div>
        <div class="fileAdd"><input type='file' name='file1' accept="image/png, image/jpeg, image/gif"></div>
        <div class="fileAdd"><input type='file' name='file2' accept="image/png, image/jpeg, image/gif"></div>
        <div class="fileAdd"><input type='file' name='file3' accept="image/png, image/jpeg, image/gif"></div>
        <div class="pass"><button type="submit">글쓰기</button></div>
        <div class="cancle"><button type="reset" onclick="history.go(-1)">취소</button></div> <!-- 목록페이지로 이동할것  -->
	</form>
</main>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>