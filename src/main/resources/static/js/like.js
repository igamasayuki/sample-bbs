'use strict';
$(function() {
	$("button").on("click", function() {
		let articleId = $(this).val();
		console.log(articleId);
		// ajaxでコントローラにリクエストを送信
		$.ajax({
			type : "GET", // GET or POST
			url : "/joinedbbs/like", // 送信先のコントローラURL
			data : {
				articleId : articleId
			}, // リクエストパラメータをJSON形式で送る
			dataType : "json", // レスポンスをJSON形式で受け取る
			async : true, // true:非同期(デフォルト), false:同期
		}).done(function(data) {
			// 通信が成功した場合に受け取るメッセージ
			console.dir(JSON.stringify(data));
			let likeCount = data["likeCount"];
			$("#likeCount" + articleId).text(likeCount)
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			// 検索失敗時には、その旨をダイアログ表示
			alert("リクエスト時にエラーが発生しました：" + textStatus + ":\n" + errorThrown);
		});
	});

});
