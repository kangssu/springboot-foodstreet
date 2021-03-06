$(document).ready(function(){
	// 비밀번호 수정 이벤트
	$('.pw_modify_all_box').hide();
	$('#pw_modify_btn').click(function(){
		$('.pw_modify_all_box').toggle();
	});
	
	// 이미지 수정 이벤트
	$('.join_modify_form_img_all').hide();
	$('#img_modify_btn').click(function(){
		$('.join_modify_form_img_all').toggle();
	});
	
	// 썸머노트 에디터 사용!
	$('#summernote').summernote({
		placeholder : '내용을 입력해주세요.',
		height : 400, 
		minHeight : null, // set minimum height of editor
		maxHeight : null, // set maximum height of editor
	});
	
	$('#summernote').on('summernote.enter', function(we, e) {
		$(this).summernote('pasteHTML', '<br><br>');
		e.preventDefault();
	});
	
	//이미지 한개 미리보기!
	function readURL(input) {
		if (input.files && input.files[0]) {
		var reader = new FileReader();
       
		reader.onload = function (e) {
			$('#image_section').attr('src', e.target.result);  
		}
		reader.readAsDataURL(input.files[0]);
		}
	}
    	  
	$(".thumbnail_form").change(function(){
        readURL(this);
     });
});

function submitRequest(){
	var name = $('.name_form').val().length;
	var food_store = $('.food_store_form').val().length;
	var place_name = $('.place_name_form').val().length;
	var address2 = $('.address2_form').val().length;
	var food_img = $('.food_img_form').val().length;
	var comment = $('.comment_form').val().length;
	var reason = $('.reason_form').val().length;
		
	if(name == 0){
		alert("이름을 작성해주세요.");
		return false;
	}else if(food_store == 0){
		alert("가게이름을 작성해주세요.");
		return false;
	}else if(place_name == 0){
		alert("지점명을 작성해주세요.");
		return false;
	}else if(address2 == 0){
		alert("주소 검색으로 주소를 입력해주세요.");
		return false;
	}else if(food_img == 0){
		alert("음식 사진을 추가해주세요.");
		return false;
	}else if(comment == 0){
		alert("한줄평을 작성해주세요.");
		return false;
	}else if(reason == 0){
		alert("추천이유를 작성해주세요.")
		return false;
	}else{
		true;
	}
};
