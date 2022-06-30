<%@page import="vo.Attach"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/head.jsp"/>
<title>MainStream!-글작성</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/board/modify.css">
</head>
<body>
	<jsp:include page="../common/header.jsp"/>
	<main class="modify-main">
		<form method="post" enctype="multipart/form-data">
			<div class="top"></div>
	        <div class="title"><input type="text" name="title" maxlength="30" ></div>
	        <div class="content"><textarea cols="90" rows="20" name="content" ></textarea></div>
        	<input type="hidden" name="category" value="${param.category}">
	        <c:forEach var="i" begin="0" end="2" varStatus="stat">
        	<c:choose>
        		<c:when test="${ not empty oldAttachs[i] }">
      			<div class="fileAdd">
	        		<div class="oldFileData" title="기존 파일명" data-uuid="${oldAttachs[i].uuid}" data-index="${i}"><c:out value="${oldAttachs[i].origin}"/></div>
		       		<input type='file' name="file${stat.count}" id="file${stat.count}" style="display: none;">
		       		<button class="fileReset" type="button" >X</button>
       			</div>
        		</c:when>
        		<c:otherwise>
       			<div class="fileAdd">
		       		<input type='file' name="file${stat.count}" id="file${stat.count}" style="display: inline-block;">
		       		<button class="fileReset" type="button" >X</button>
	       		</div>
        		</c:otherwise>
        	</c:choose>
	        </c:forEach>
	        
	        <div class="pass"><button type="submit">수정</button></div>
	        <div class="cancle"><button type="reset" onclick="history.go(-1)">취소</button></div> <!-- 목록페이지로 이동할것  -->
	        <input type="hidden" name="bno" value="${board.bno}">
	        <input type="hidden" name="id" value="${board.id}">
		</form>
	</main>
	<script>
		var cp = "${pageContext.request.contextPath}";
		
		$(function(){
			$(".title").find("input").val("${board.title}");
			$(".content").find("textarea").val("${board.content}");
			
			// 첨부파일 삭
			var inputFiles = $("input");
			inputFiles.change(function(){
				var newFile = $(this);
				var oldFile = newFile.parent().children(".oldFileData");
				
				if(!newFile.val() && oldFile.hasClass("oldFileData")) {
					newFile.css("display", "none")
					oldFile.css("display", "inline-block");
				}
				if(newFile.val()) {
					newFile.css("display", "inline-block");
					oldFile.css("display", "none");
				}
			});
			
			// 첨부파일 삭제 view
			var fileReset = $(".fileAdd button");
			fileReset.click(function(){
				var btn = $(this);
				btn.parent().children("input").css("display", "inline-block").val("");
				
				var oldFileData = btn.parent().children(".oldFileData");
				if(oldFileData.hasClass("oldFileData")) {
					oldFileData.css("display", "none");
					removeAttach(oldFileData);					
				}
			});
			
			// 첨부파일 삭제 (비동기통신을 이용해 DB서버의 데이터를 삭제)
			function removeAttach(oldFileData){
				var data = oldFileData.data();				
				var uuid = JSON.stringify(data.uuid);
				
				$.ajax(cp + "/board/modify?bno=${board.bno}&index=" + data.index, {
					method:"delete",
					data : {"uuid" : uuid },
					success : function(data) {
						// DB상에서 삭제 성공시 html상에서도 삭제
						oldFileData.remove();
					}
				});
			}
			
			var fileName = $(".fileAdd .oldFileData");
			fileName.click(function(){
				$(this).next().trigger("click");
			});
			
		});
		
	</script>
	<jsp:include page="../common/footer.jsp"/>
</body>
</html>