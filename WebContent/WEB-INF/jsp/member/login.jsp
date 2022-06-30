<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/head.jsp"/>
<link rel="stylesheet" href="css/style.css">	
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<!-- 로그인 메인 -->
    <main class="login-main">
    <form method="post">
	        <ul>
	            <li>
	                <label for="id">아이디</label>
	                <input type="text" name="id" id="id" autocomplete="off" placeholder="아이디를 입력하세요">
	            </li>
	            <li>
	                <label for="pwd">비밀번호</label>
	                <input type="password" name="pwd" id="pwd" autocomplete="off" placeholder="비밀번호를 입력하세요">
	            </li>
	        </ul>
	        <p><label><input type="checkbox" name="savedId" id="savedId">아이디 저장</label></p>
	        
	        <div class="loginbox"><button>로그인</button></div>
    </form>

    </main>
	<jsp:include page="../common/footer.jsp"></jsp:include>
	<script>
		$(function() {
			if($.cookie("savedId")) {
				$("#id").val($.cookie("savedId"));
				$("#savedId").prop("checked", true);
			}
			
		});
	</script>
</body>
</html>