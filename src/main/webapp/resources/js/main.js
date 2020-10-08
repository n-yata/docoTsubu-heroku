/**
 *
 */
function doGet(){
	$.ajax({
		contentType: "Content-Type: application/json; charset=UTF-8",
		url: "/MainController",
		type: "GET"

	}).done(function(data, status, xhr){
		var isLogin = data.isLogin;
		var mutterList = data.mutterList;


		if(isLogin){
			// ログイン済みの場合
			if(mutterList != null && mutterList != ""){
				// つぶやきリストを表示
				var mutterListHtml = "";
				$.each(mutterList, function(i, val){
					mutterListHtml += val.userName + " : " + val.text + "<a href="" id="delete">削除</a></br>";
				});
				$("#mutterList").html(mutterListHtml);
				$("#errorMsg").hide();

			}
		}else{
				// リダイレクト
				location.href = "/";
		}
	});
}

/**
 *
 */
function doPost(param){
	$.ajax({
		contentType: "Content-Type: application/json; charset=UTF-8",
		url: "/MainController",
		type: "POST",
		data : JSON.stringify(param)

	}).done(function(data, status, xhr){
		var mutterList = data.mutterList;
		var errorMsg = data.errorMsg;

		if(mutterList != null && mutterList != ""){
			var mutterListHtml = "";
			$.each(mutterList, function(i, val){
				mutterListHtml += val.userName + " : " + val.text + "</br>";
			});
			$("#mutterList").html(mutterListHtml);
			$("#errorMsg").hide();

			// 入力フォームのクリア
			$("#text").val("");
		}

		if(errorMsg != null && errorMsg != ""){
			$("#errorMsg").html(errorMsg);
			$("#errorMsg").show();
		}
	});
}


/**
 *
 */
$(function(){
	// ユーザー名を表示
	var storage = sessionStorage;
	var userName = storage.userName + "さん、ログイン中";
	$("#userName").html(userName);

	doGet();

	$("#update").click(function(){
		doGet();
	});

	$("#btn").click(function(){
		var param = {
				text: $("#text").val()
		};
		doPost(param);
	});
});