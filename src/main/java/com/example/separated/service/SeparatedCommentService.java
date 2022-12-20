package com.example.separated.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.separated.domain.SeparatedComment;
import com.example.separated.repository.SeparatedCommentRepository;

/**
 * コメント関連サービス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class SeparatedCommentService {

	@Autowired
	private SeparatedCommentRepository commentRepository;

	/**
	 * コメント一覧を取得します.
	 * 
	 * @return コメント一覧情報
	 */
	public List<SeparatedComment> findByArticleId(int ArticleId) {
		return commentRepository.findByArticleId(ArticleId);
	}

	/**
	 * コメントを登録します.
	 * 
	 * @param comment コメント情報
	 * @return 登録したコメント情報
	 */
	public void save(SeparatedComment comment) {
		commentRepository.insert(comment);
	}

	/**
	 * 削除される記事のコメントを削除します.
	 * 
	 * @param id 削除したい記事ID
	 */
	public void deleteByArticleId(int articleId) {
		commentRepository.deleteByArticleId(articleId);
	}
}
