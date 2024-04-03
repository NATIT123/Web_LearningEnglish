$(document).ready(function() {

	
	// click thêm tài khoản
    $(document).on('click', '.btnThemTaiKhoan', function (event) {
    	event.preventDefault();
        $("#taiKhoanModal").modal();
    });




    
	// event khi ẩn modal form
	$('#taiKhoanModal').on('hidden.bs.modal', function(e) {
		e.preventDefault();
		$('.taiKhoanForm input').next().remove();
	});

});