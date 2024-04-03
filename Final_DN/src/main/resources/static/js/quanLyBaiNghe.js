$(document).ready(function() {

	


	// event khi click duyệt bài nghe
	$(document).on('click', '#btnDuyetBaiNghe', function(event) {
		$('.baiNgheTable tbody tr').remove();
		$('.pagination li').remove();
	});

	// click event button Thêm mới bài nghe
	$('.btnThemBaiNghe').on("click", function(event) {
		event.preventDefault();
		$('#baiNgheModal').modal();
		$('.baiNgheForm #id').prop("disabled", true);
		$('#formBaiNghe').removeClass().addClass("addForm");
		$('#formBaiNghe #btnSubmit').removeClass().addClass("btn btn-primary btnSaveForm");
	});
	
	// click event button cập nhật bài nghe
	$(document).on('click', '.btnCapNhatBaiNghe', function(event) {
		event.preventDefault();
		var baiNgheId = $(this).parent().find('input').val();
		$('#formBaiNghe').removeClass().addClass("updateForm");
		$('#formBaiNghe #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateForm");
		var href = "http://localhost:8080/webtoeic/api/admin/bai-nghe/"+baiNgheId;
		$.get(href, function(baiNghe) {
            console.log(baiNghe);
            $('#id').val(baiNghe.id);
            $('#tenBaiNghe').val(baiNghe.tenBaiNghe);
            $('#doKho').val(baiNghe.doKho);
            $('#phanThi').val(baiNghe.part);
            $('#script').val(baiNghe.script);
            
//            $('#photoBaiNghe').val("http://localhost:8080/webtoeic/file/images/baiNgheId="+ baiNghe.id+".png");
            $("img").attr("src", "http://localhost:8080/webtoeic/file/images/baiNgheId="+ baiNghe.id+".png");
            $("#previewImage").removeClass("hidden");
            $("#previewAudio").attr("src", "http://localhost:8080/webtoeic/file/audio/baiNgheId="+ baiNghe.id+".mp3");
            $("#previewAudio").removeClass("hidden");
            $("#linkExcel").attr("href", "http://localhost:8080/webtoeic/file/excel/baiNgheId="+ baiNghe.id+".xlsx");
            $("#linkExcel").removeClass("hidden");           
		});	
		$('#baiNgheModal').modal();
	});
	
	// event khi hide modal
	$('#baiNgheModal').on('hidden.bs.modal', function() {
		$('#formBaiNghe').removeClass().addClass("baiNgheForm");
		$('#formBaiNghe #btnSubmit').removeClass().addClass("btn btn-primary");
		resetForm();
	});






	// validate form trước khi submit
	$("#formBaiNghe").validate({
		errorElement : "p",
		errorClass : "error-message",
		rules : {
			tenBaiNghe : {
				required : true,
				maxlength : 100
			},
			photoBaiNghe : {
				required : true
			},
			audioBaiNghe : {
				required : true
			},
			fileCauHoi : {
				required : true
			},
//			script : { // nếu là part 3 hoặc 4 thì ko đc để trống
//				required : {
//					depends : function() {
//						return $("#phanThi").val() === '3' || $("#phanThi").val() === '4';
//					}
//				}
//			}
		},
		messages : {
			tenBaiNghe : {
				required : "Bạn không được để trống phần này",
				maxlength : "Tiêu đề dài nhất là 100 chữ cái"
			},
			photoBaiNghe : {
				required : "Bạn không được để trống phần này"
			},
			audio : {
				required : "Bạn không được để trống phần này"
			},
			fileExcelCauHoi : {
				required : "Bạn không được để trống phần này"
			},
			script : {
				required : "Bạn không được để trống phần này"
			}
		},
		submitHandler : function(form) {
			var form = $('#formBaiNghe')[0];
			var formData = new FormData(form);
			saveFunction(formData);
		}
	});
	

	$(document).on('click', '.btnSaveForm', function(event) {
		event.preventDefault();
		if ($("#formBaiNghe").valid()) {
			$("#formBaiNghe").submit();
		}
	});
	

	// validate các trường file input và preview file ảnh/ audio
	$("#audioBaiNghe").change(function() {
		var fileExtension = [ 'mp3' ];
		if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
			alert("Chỉ cho phép định dạng audio mp3 ");
			$("#audioBaiNghe").wrap('<form>').closest('form').get(0).reset();
			$("#audioBaiNghe").unwrap();
			$("#previewAudio").removeClass().addClass("hidden");
		} else {
			var files = event.target.files;
			$("#previewAudio").attr("src", URL.createObjectURL(files[0]));
			$("#previewAudio").removeClass("hidden");
		}
		changeAudio = true;
	});

	$("#photoBaiNghe").change(function() {
		var fileExtension = [ 'jpg', 'png' ];
		if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
			alert("Chỉ cho phép ảnh định dang JPEG, PNG");
			$("#photoBaiNghe").wrap('<form>').closest('form').get(0).reset();
			$("#photoBaiNghe").unwrap();
			$("#previewImage").removeClass().addClass("hidden");
		} else {
			var files = event.target.files;
			$("#previewImage").attr("src", URL.createObjectURL(files[0]));
			$("#previewImage").removeClass("hidden");
			// $("#previewImage").load();
		}
		changeImage = true;
	});

	$("#fileCauHoi").change(function() {
		var fileExtension = [ 'xlsx' ];
		if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
			alert("Chỉ cho phép file Excel định dang xlsx");
			$("#fileCauHoi").wrap('<form>').closest('form').get(0).reset();
			$("#fileCauHoi").unwrap();

		}
		changeExcel = true;
	});

	// reset form
	function resetForm() {
		$("#id").val("");
		$("#tenBaiNghe").val("");
		$("#script").val("");
		$("#fileCauHoi").wrap('<form>').closest('form').get(0).reset();
		$("#fileCauHoi").unwrap();
		$("#photoBaiNghe").wrap('<form>').closest('form').get(0).reset();
		$("#photoBaiNghe").unwrap();
		$("#previewImage").addClass("hidden");
		$("#audioBaiNghe").wrap('<form>').closest('form').get(0).reset();
		$("#audioBaiNghe").unwrap();
		$("#previewAudio").addClass("hidden");
		$("#linkExcel").addClass("hidden");
		$("#linkExcel").attr("href", "");
		changeImage = false;
		changeExcel = false;
		changeAudio = false;
	}

});

