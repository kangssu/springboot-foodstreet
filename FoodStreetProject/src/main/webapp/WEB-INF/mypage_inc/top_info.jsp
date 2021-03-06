<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<div class="top_sub_title">
	<h2>마이페이지</h2>
</div>
<div class="top_info_box">
	<div class="info_content_box">
		<c:choose>
			<c:when test="${member.id eq 'admin'}">
				<img src="/images/${member.img_name}">
			</c:when>
			<c:when test="${member.img_name == null}">
				<img src="/img/user_noimg.png" alt="소셜 프로필 이미지">
			</c:when>
			<c:otherwise>
				<img src="/images/${img_name}" alt="프로필 이미지">
			</c:otherwise>
		</c:choose>
	</div>
	<div class="info_content_box info_content_box_gride">
		<p><span>${member.name}님,</span> 푸드스트릿에 오신걸 환영합니다.</p>
		<div class="info_all_button">
			<c:choose>
				<c:when test="${member.member_level == 30}">
					<button type="button" onclick="location.href='/admin/request/list';">신청내역</button>
					<button type="button" onclick="location.href='/admin/executive/list';">운영진 계정 관리</button>
					<button type="button" onclick="location.href='/admin/executive/form';">운영진 계정 생성</button>
				</c:when>
				<c:when test="${member.member_level == 20}">
					<button type="button" onclick="location.href='/mypage/my';">회원정보수정</button>
					<button type="button" onclick="location.href='/executive/story/form';">스토리 등록</button>
					<button type="button" onclick="location.href='/executive/mypage';">스토리 리스트</button>
				</c:when>
				<c:otherwise>
					<button type="button" onclick="location.href='/mypage/my';">회원정보수정</button>
					<button type="button" onclick="location.href='/mypage/request/list';">내가 신청한 맛집</button>
					<button type="button" onclick="location.href='/mypage/review/list';">내가 등록한 리뷰</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>