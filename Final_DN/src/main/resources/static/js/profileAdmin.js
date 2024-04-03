$(document).ready(function(){
	// click event button Cap nhật thông tin
	$('.btnCapNhatThongTin').on("click", function(event) {
		event.preventDefault();
		$('.formCapNhat #capNhatModal').modal();
	});






	$('.btnDoiMatKhau').on("click", function(event) {
		event.preventDefault();
		$('.formDoiMatKhau #doiMKModal').modal();
	});
})