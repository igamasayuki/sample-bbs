package com.example.joined.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.joined.domain.JoinedArticle;
import com.example.joined.repository.JoinedArticleRepository;

/**
 * 記事関連サービスクラス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class JoinedArticleService {

	@Autowired
	private JoinedArticleRepository articleRepository;

	/**
	 * 記事一覧を取得します. <br>
	 * 記事に含まれているコメント一覧も同時に取得します
	 * 
	 * @return コメントを含んだ記事一覧情報
	 */
	public List<JoinedArticle> findAll() {
		return articleRepository.findAll();
	}

	/**
	 * 記事を登録します.
	 * 
	 * @param article 記事情報
	 * @return 登録した記事情報
	 */
	public JoinedArticle save(JoinedArticle article) {
		return articleRepository.insert(article);
	}

	/**
	 * 記事を削除します.
	 * 
	 * @param id 削除したい記事ID
	 */
	public void delete(int id) {
		articleRepository.delete(id);
	}

	/**
	 * 記事投稿者名で前方一致検索します.
	 * 
	 * @param name 記事投稿者名
	 * @return 記事とコメントのリスト
	 */
	public List<JoinedArticle> findByUserName(String name) {
		return articleRepository.findByUserName(name);
	}
}
