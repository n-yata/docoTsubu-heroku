/**
 *
 */
	$(function(){
		// ユーザー名を表示
		var storage = sessionStorage;
		var userName = storage.userName + "さん、ログイン中";
		$("#userName").html(userName);

		$.ajax({
			contentType: "Content-Type: application/json; charset=UTF-8",
			url: "/docoTsubu/MainController",
			type: "GET"

		}).done(function(data, status, xhr){
			var isLogin = data.isLogin;
			var mutterList = data.mutterList;


			if(isLogin){
				if(mutterList != null && mutterList != ""){
					var mutterListHtml = "";
					$.each(mutterList, function(i, val){
						mutterListHtml += val.userName + " : " + val.text + "</br>";
					});
					$("#mutterList").html(mutterListHtml);
					$("#errorMsg").hide();

				}
			}else{
				// リダイレクト
				location.href = "/docoTsubu/";
			}
		});

		$("#update").click(function(){
			$.ajax({
				contentType: "Content-Type: application/json; charset=UTF-8",
				url: "/docoTsubu/MainController",
				type: "GET"

			}).done(function(data, status, xhr){
				var isLogin = data.isLogin;
				var mutterList = data.mutterList;


				if(isLogin){
					if(mutterList != null && mutterList != ""){
						var mutterListHtml = "";
						$.each(mutterList, function(i, val){
							mutterListHtml += val.userName + " : " + val.text + "</br>";
						});
						$("#mutterList").html(mutterListHtml);
						$("#errorMsg").hide();

					}
				}else{
					// リダイレクト
					location.href = "/docoTsubu/";
				}
			});
		});

		$("#btn").click(function(){
			var param = {
					text: $("#text").val()
			};
			$.ajax({
				contentType: "Content-Type: application/json; charset=UTF-8",
				url: "/docoTsubu/MainController",
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
		});
	});