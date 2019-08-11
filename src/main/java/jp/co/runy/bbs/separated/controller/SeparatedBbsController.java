package jp.co.runy.bbs.separated.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.runy.bbs.separated.domain.SeparatedArticle;
import jp.co.runy.bbs.separated.domain.SeparatedComment;
import jp.co.runy.bbs.separated.form.SeparatedArticleForm;
import jp.co.runy.bbs.separated.form.SeparatedCommentForm;
import jp.co.runy.bbs.separated.service.SeparatedArticleService;
import jp.co.runy.bbs.separated.service.SeparatedCommentService;

/**
 * 掲示板処理関連のコントローラークラス.
 * 
 * @author igamasayuki
 *
 */
@Controller
@Transactional
@RequestMapping("/separatedbbs")
public class SeparatedBbsController {
	
	@Autowired
	private SeparatedArticleService articleService;
	@Autowired
	private SeparatedCommentService commentService;

	/**
	 * 記事フォームの初期化.
	 * 
	 * @return 記事フォーム
	 */
	@ModelAttribute
	public SeparatedArticleForm setArticleForm() {
		return new SeparatedArticleForm();
	}

	/**
	 * コメントフォームの初期化.
	 * 
	 * @return コメントフォーム
	 */
	@ModelAttribute
	public SeparatedCommentForm setCommentForm() {
		return new SeparatedCommentForm();
	}

	/**
	 * 記事とコメントを表示するメソッド.
	 * 
	 * @param model
	 *            モデル
	 * @return 掲示板画面
	 */
	@RequestMapping
	public String index(Model model) {
		// 計測スタート
		LocalDateTime time = LocalDateTime.now();
		List<SeparatedArticle> articleList = articleService.findAll();
		
		// 記事サイズをスコープに格納する
		model.addAttribute("listSize", articleList.size());
		
		for (SeparatedArticle article : articleList) {
			List<SeparatedComment> commentList = commentService.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}
		
		// 件数が多いと表示は時間がかかるので最初の10個のみスコープへ格納する
		if(articleList.size() >= 10) {
			articleList = articleList.subList(0, 10);
		}
		// 記事リストをスコープに格納する
		model.addAttribute("articleList", articleList);
		
		// 計測開始からここまでの時間の差分を取得しスコープへ格納
		Long lapTime = ChronoUnit.MILLIS.between(time, LocalDateTime.now());
		model.addAttribute("lapTime", lapTime);

		return "separated/separatedbbsview";
	}

	/**
	 * 記事を追加するメソッド.
	 * 
	 * @param form
	 *            記事フォーム
	 * @param model
	 *            モデル
	 * @return 掲示板画面
	 */
	@RequestMapping("/postarticle")
	public String insertArticle(@Validated SeparatedArticleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return index(model);
		}
		SeparatedArticle article = new SeparatedArticle();
		BeanUtils.copyProperties(form, article);
		articleService.save(article);
		return "redirect:/separatedbbs";
	}

	/**
	 * コメントを追加するメソッド.
	 * 
	 * @param form
	 *            コメントフォーム
	 * @param model
	 *            モデル
	 * @return 掲示板画面
	 */
	@RequestMapping("/postcomment")
	public String insertComment(@Validated SeparatedCommentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return index(model);
		}
		SeparatedComment comment = new SeparatedComment();
		BeanUtils.copyProperties(form, comment);
		commentService.save(comment);
		return "redirect:/separatedbbs";
	}

	/**
	 * 記事とコメントを削除するメソッド.
	 * 
	 * @param id
	 *            削除する記事のＩＤ
	 * @param model
	 *            モデル
	 * @return 掲示板画面
	 */
	@RequestMapping("/deletearticle")
	public String deletearticle(SeparatedArticleForm form) {
		commentService.deleteByArticleId(form.getId());
		articleService.delete(form.getId());
		return "redirect:/separatedbbs";
	}
}
