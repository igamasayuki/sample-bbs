package com.example.joined.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.joined.domain.JoinedArticle;
import com.example.joined.domain.JoinedComment;
import com.example.joined.form.JoinedArticleForm;
import com.example.joined.form.JoinedCommentForm;
import com.example.joined.service.JoinedArticleService;
import com.example.joined.service.JoinedCommentService;

import jakarta.servlet.ServletContext;

/**
 * 掲示板を操作するコントローラ.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/joinedbbs")
@Transactional
public class JoinedBbsController {
	@Autowired
	private JoinedArticleService articleService;

	@Autowired
	private JoinedCommentService commentService;

	@Autowired
	private ServletContext application; // ←いいね件数を保存するため

//	/**
//	 * 記事のフォームを初期化します.
//	 * 
//	 * @return 記事フォーム
//	 */
//	@ModelAttribute
//	public JoinedArticleForm setUpArticleForm() {
//		return new JoinedArticleForm();
//	}

//	/**
//	 * コメントのフォームを初期化します.
//	 * 
//	 * @return コメントフォーム
//	 */
//	@ModelAttribute
//	public JoinedCommentForm setUpCommentForm() {
//		return new JoinedCommentForm();
//	}

	/**
	 * 掲示板を表示します.
	 * 
	 * @param model             モデル
	 * @param joinedArticleForm 記事フォーム リクエストスコープ格納用
	 * @param joinedCommentForm コメントフォーム リクエストスコープ格納用
	 * @return 掲示板画面
	 */
	@GetMapping("")
	public String index(Model model, JoinedArticleForm joinedArticleForm, JoinedCommentForm joinedCommentForm) {
		// 計測スタート
		LocalDateTime time = LocalDateTime.now();

		List<JoinedArticle> articleList = articleService.findAll();

		// 記事サイズをスコープに格納する
		model.addAttribute("listSize", articleList.size());
		// 件数が多いと表示は時間がかかるので最初の10個のみスコープへ格納する
		if (articleList.size() >= 10) {
			articleList = articleList.subList(0, 10);
		}
		// 記事リストをスコープに格納する
		model.addAttribute("articleList", articleList);

		// 計測開始からここまでの時間の差分を取得しスコープへ格納
		Long lapTime = ChronoUnit.MILLIS.between(time, LocalDateTime.now());
		model.addAttribute("lapTime", lapTime);

		return "joined/joinedbbsview";
	}

	/**
	 * 記事を投稿します.
	 * 
	 * @param joinedArticleform フォーム
	 * @param result            リザルト
	 * @param model             モデル
	 * @param joinedCommentForm コメントフォーム
	 * @return 掲示板画面
	 */
	@PostMapping("/postarticle")
	public String postarticle(@Validated JoinedArticleForm joinedArticleform, BindingResult result, Model model,
			JoinedCommentForm joinedCommentForm) {
		if (result.hasErrors()) {
			return index(model, joinedArticleform, joinedCommentForm);
		}
		JoinedArticle article = new JoinedArticle();
		BeanUtils.copyProperties(joinedArticleform, article);
		articleService.save(article);
		return "redirect:/joinedbbs";
	}

	/**
	 * コメントを投稿します.
	 * 
	 * @param joinedCommentForm フォーム
	 * @param result            リザルト
	 * @param model             モデル
	 * @param joinedArticleForm 記事フォーム
	 * @return 掲示板画面
	 */
	@PostMapping("/postcomment")
	public String postcomment(@Validated JoinedCommentForm joinedCommentForm, BindingResult result, Model model,
			JoinedArticleForm joinedArticleform) {
		if (result.hasErrors()) {
			return index(model, joinedArticleform, joinedCommentForm);
		}
		JoinedComment comment = new JoinedComment();
		BeanUtils.copyProperties(joinedCommentForm, comment);
		comment.setArticleId(joinedCommentForm.getArticleId());
		commentService.save(comment);
		return "redirect:/joinedbbs";
	}

	/**
	 * 記事を削除します.
	 * 
	 * @param form 記事フォーム
	 * @return 記事登録画面
	 */
	@PostMapping(value = "/deletearticle")
	public String deletearticle(JoinedArticleForm form) {
		articleService.delete(form.getId());
		return "redirect:/joinedbbs";
	}

	/**
	 * いいね！された記事のいいね件数を1増やして、JSON形式で返す.<br>
	 * 複数ブラウザからアクセスされても正しく動くようにsynchronizedをつけて排他制御をしています。
	 * 
	 * @param articleId 記事ID
	 * @return １つ増えたいいね件数をJSON形式で(Mapで返すとJSON形式で返る)
	 */
	@ResponseBody
	@GetMapping("/like")
	synchronized public Map<String, Integer> like(String articleId) {

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
