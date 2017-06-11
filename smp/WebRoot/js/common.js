function queryByAjax(selector, _url, requestType, params) {
	if (!selector) {
		return;
	}
	selector = "#" + selector;
	var container = $(selector);

	if (!_url) {
		container.html("url����û����������");
		return;
	}
	if (requestType == "") {
		requestType = "get";
	}
	container.html("���ڼ���...�����Ժ�");
	$.ajax({
		url : _url,
		dataType : "html",
		data : params,
		cache : false,
		error : function(data) {
			alert("�������");
			container.html("����ʧ�ܣ�");
		},
		type : requestType,
		success : function(data) {
			// ȡbody.html
			container.html(data);
		}
	});
};