package jp.co.runy.bbs.joined.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * いいね！を管理するコントローラ.
 * 
 * JSON形式でレスポンスするため、@Controllerではなく@RestControllerにしている
 * 
 * @author igamasayuki
 *
 */
@RestController
@RequestMapping("/like")
public class LikeController {

	@Autowired
	private ServletContext application;

	/**
	 * いいね！された記事のいいね件数を1増やして、JSON形式で返す.
	 * 
	 * @param articleId 記事ID
	 * @return １つ増えたいいね件数をJSON形式で(Mapで返すとJSON形式で返る)
	 */
	@RequestMapping("")
	public Map<String, Integer> like(String articleId) {

		// applicationスコープから記事IDについているいいね件数を取得
		Integer likeCount = (Integer) application.getAttribute(articleId);
		// 1件もなければ1件を登録する
		if (likeCount == null) {
			likeCount = 0;
		}
		// いいね件数を１増やす
		++likeCount;
		application.setAttribute(articleId, likeCount);
		
		// 増やした件数をMapとして返す→JSON形式で返る
		Map<String, Integer> likeMap = new HashMap<>();
		likeMap.put("likeCount", likeCount);
		return likeMap;
	}
}
