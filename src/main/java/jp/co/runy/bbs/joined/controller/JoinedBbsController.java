package jp.co.runy.bbs.joined.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.runy.bbs.joined.domain.JoinedArticle;
import jp.co.runy.bbs.joined.domain.JoinedComment;
import jp.co.runy.bbs.joined.form.JoinedArticleForm;
import jp.co.runy.bbs.joined.form.JoinedCommentForm;
import jp.co.runy.bbs.joined.service.JoinedArticleService;
import jp.co.runy.bbs.joined.service.JoinedCommentService;

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

	/**
	 * 記事のフォームを初期化します.
	 * 
	 * @return 記事フォーム
	 */
	@ModelAttribute
	public JoinedArticleForm setUpArticleForm() {
		return new JoinedArticleForm();
	}

	/**
	 * コメントのフォームを初期化します.
	 * 
	 * @return コメントフォーム
	 */
	@ModelAttribute
	public JoinedCommentForm setUpCommentForm() {
		return new JoinedCommentForm();
	}

	/**
	 * 掲示板を表示します.
	 * 
	 * @param model モデル
	 * @return 掲示板画面
	 */
	@GetMapping("")
	public String index(Model model) {
		// 計測スタート
		LocalDateTime time = LocalDateTime.now();
		
		List<JoinedArticle> articleList = articleService.findAll();
		
		// 記事サイズをスコープに格納する
		model.addAttribute("listSize", articleList.size());
		// 件数が多いと表示は時間がかかるので最初の10個のみスコープへ格納する
		if(articleList.size() >= 10) {
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
	 * @param form
	 *            フォーム
	 * @param result
	 *            リザルト
	 * @param model
	 *            モデル
	 * @return 掲示板画面
	 */
	@PostMapping("/postarticle")
	public String postarticle(@Validated JoinedArticleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return index(model);
		}
		JoinedArticle article = new JoinedArticle();
		BeanUtils.copyProperties(form, article);
		articleService.save(article);
		return "redirect:/joinedbbs";
	}

	/**
	 * コメントを投稿します.
	 * 
	 * @param form
	 *            フォーム
	 * @param result
	 *            リザルト
	 * @param model
	 *            モデル
	 * @return 掲示板画面
	 */
	@PostMapping("/postcomment")
	public String postcomment(@Validated JoinedCommentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return index(model);
		}
		JoinedComment comment = new JoinedComment();
		BeanUtils.copyProperties(form, comment);
		commentService.save(comment);
		return "redirect:/joinedbbs";
	}

	/**
	 * 記事を削除します.
	 * 
	 * @param form
	 *            記事フォーム
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
