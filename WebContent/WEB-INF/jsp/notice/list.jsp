<%@page import="service.BoardServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>    
<!DOCTYPE html>
<html>
<head>
	<title>MainStream! 공지사항</title>
	<jsp:include page="../common/head.jsp"/>
	<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:include page="../common/header.jsp"/>
	<!-- 공지사항 메인 -->
    <main class="notice-main">
        <div class="top-space"></div>
        <table class="notice-Table">
            <caption><strong>Main Stream 공지사항</strong></caption>
            <thead>
                <tr>
                    <th class="col-no">연번</th>
                    <th class="col-title">제목</th>
                    <th class="id">작성자</th>
                    <th class="date">작성일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${list}" var="b">
	               	<tr>
	               		
	               		<td class="no">${b.bno}</td>
	               		<td class="title"><p><a href="detail?bno=${b.bno}"><c:out value="${b.title}"/></a></p></td>
	               		<td>${b.id}</td>
	               		<td><fmt:formatDate value="${b.regDate}" pattern="yy/MM/dd"/> </td>
	               	</tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
        </div>
        <%	
        	// 로그인 한 상태에서만 글쓰기 버튼 활성화
        	if(session.getAttribute("member") != null){
        		out.print("<div class='notice-writebox'><a class='linkButton' href='/board/write?category=0'>글쓰기</a></div>");
        	}
        	else {
        		out.print("<div class='notice-writebox'></div>");
        	}
        %>
        <div class="notice-index">
	            <div class="box">
		        	<c:if test="${page.prev == true }">
		                <a class="linkButton" href="list?pageNum=${page.startPage-1 }&amount=${page.cri.amount}">이전</a>
		        	</c:if>
	            </div>
        	<c:forEach begin="${page.startPage }" end="${page.endPage }" var="i"> <!-- step생략하면 1씩 증가  -->
	            <div class="box">
		            <a class="linkButton${i == page.cri.pageNum ? '-active' : '' }" href="list?pageNum=${i}&amount=${page.cri.amount}">${i}</a>
	            </div>
			</c:forEach>
            <div class="box">
	            <c:if test="${page.next == true }">
	                <a class="linkButton" href="list?pageNum=${page.endPage+1 }&amount=${page.cri.amount}">다음</a>
	            </c:if>
            </div>
        </div>
    </main>
	<jsp:include page="../common/footer.jsp"/>
</body>
</html>