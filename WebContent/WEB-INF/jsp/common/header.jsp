<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 헤더 -->
<header>
    <div class="header-content">
        <!-- 좌상단 이미지 홈배너 -->
        <img src="<%=request.getContextPath() %>/images/MainStreamHome.jpg" alt="홈배너" usemap="#homebanner">
        <map name="homebanner">
            <area shape="rect" coords="0,0,200,70" href="${pageContext.request.contextPath}/index.html" alt="홈">
        </map>
        <h3>모든 취향의 음악 커뮤니티! Main Stream!</h3>
    </div>
    <nav>
        <div class="nav-content">
            <ul>
                <li><a href="${pageContext.request.contextPath}/notice/list">공지사항♪</a></li>
                <li><a href="${pageContext.request.contextPath}/board/list">자유게시판♪</a></li>
                <li><a href="${pageContext.request.contextPath}/gallery/list">갤러리♪</a></li>
            </ul>
        </div>
    </nav>
</header>