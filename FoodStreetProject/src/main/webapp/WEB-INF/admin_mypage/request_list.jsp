<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="info_content_list">
	<div class="info_content_list_title">
		<h2>신청내역<span>맛집신청 페이지에서 신청된 내역입니다.</span></h2>
	</div>
	<div class="info_content_list_title more_button_text">
		<a href="/admin/mypage">
			<span>홈으로 돌아가기<i class="fa fa-angle-right" aria-hidden="true"></i></span>
		</a>
	</div>
	<div class="info_content_all_table">
		<table class="info_list_table">
			<thead>
				<tr>
					<th width="10%">No</th>
					<th width="10%">신청자</th>
					<th width="20%">가게이름</th>
					<th width="10%">지점명</th>
					<th width="20%">주소</th>
					<th width="10%">신청일</th>
					<th width="20%">비고</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${list.size()==0}">
						<tr>
							<td colspan="7">맛집 신청내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="r" items="${list}" varStatus="i">
							<tr>
								<td>${no}</td>
								<c:set var="no" value="${no-1}"/>
								<td>${r.name}</td>
								<td>${r.food_store}</td>
								<td>${r.place_name }</td>
								<td>${r.address2}</td>
								<td><fmt:formatDate value="${r.reporting_date}" pattern="yyyy-MM-dd"/></td>
								<td>
									<c:choose>
										<c:when test="${r.state == '대기중'}">
											<button type="button" class="request_view" onclick="location.href='/admin/request/view?num=${r.num}&page=${currentPage}';">확인</button>
										</c:when>
										<c:when test="${r.state == '평가탈락'}">
											<button type="button" class="request_view request_fail" onclick="location.href='/admin/request/view?num=${r.num}&page=${currentPage}';">탈락</button>
										</c:when>
										<c:otherwise>
											<button type="button" class="request_view_modify" onclick="location.href='/admin/request/view?num=${r.num}&page=${currentPage}';">수정</button>
										</c:otherwise>
									</c:choose>
									<button type="button" class="request_del" onclick="showDelPopup(${r.num})">삭제</button>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<ul class="paging">
			<c:if test="${paging.prev}">
				<li><a href="/admin/request/list?page=${paging.startPage-1}"><i class="fa fa-angle-left" aria-hidden="true"></i></a></li>
			</c:if>
			<c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="num">
				<c:choose>
					<c:when test="${currentPage == num}">
						<li class="active"><a href="/admin/request/list?page=${num}">${num}</a></li>
					</c:when>
					<c:otherwise>
						<li class="select"><a href="/admin/request/list?page=${num}">${num}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${paging.next && paging.endPage>0}">
				<li><a href="/admin/request/list?page=${paging.endPage+1}"><i class="fa fa-angle-right" aria-hidden="true"></i></a></li>
			</c:if>	
		</ul>
	</div>
</div>
<!-- 삭제 클릭하면 나오는 팝업! -->
<div id="popup" class="hide">
	<div class="content">
		<h3 class="popup_title"><i class="fa fa-bell-o" aria-hidden="true"></i>삭제 확인</h3>
		<p class="popup_text">
			맛집 신청 내역을 삭제하시겠습니까?<br>
			삭제하시면 다시 원복하실 수 없습니다.<br>
		</p>
		<span>※ 신중히 생각하시고 진행부탁드립니다.</span>	
		<input type="hidden" id="num">
		<input type="hidden" id="nowpage">
		<div class="popup_btn">
			<button type="button" id="btn_ok_del">삭제</button>
			<button type="button" id="btn_close" onclick="closeDelPopup()" >취소</button>
		</div>
	</div>
</div>