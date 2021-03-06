/**
 *
 */
function doPost(param){
	$.ajax({
		contentType: "Content-Type: application/json; charset=UTF-8",
		url: "/Login",
		type: "POST",
		data : JSON.stringify(param)
	}).done(function(data, status, xhr){
		// 初期画面の非表示
		$("#index").css("display", "none");
		// ログイン結果の表示
		$("#loginResult").css("display", "block");

		if(data.isLogin){
			// ログイン成功
			$("#success").css("display", "block");
			$("#failed").css("display", "none");

			// ストレージにユーザー名を保存
			var storage = sessionStorage;
			storage.setItem("userName", data.userName)
			// ユーザー名を表示
			var userName = "ようこそ" + storage.userName + "さん";
			$("#userName").html(userName);
		}else{
			// ログイン失敗
			$("#success").css("display", "none");
			$("#failed").css("display", "block");
		}
	});
}

// ページ読み込み時の処理
$(function(){
	// ログイン結果の非表示
	$("#loginResult").css("display", "none");

	// 「ログイン」ボタンクリック時
	$("#btn").click(function(){
		var param = {
				name: $("#name").val(),
				pass : $("#pass").val()
		};
		doPost(param);
	});
});