package com.example.separated.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.separated.domain.SeparatedArticle;
import com.example.separated.repository.SeparatedArticleRepository;

/**
 * 記事関連サービスクラス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class SeparatedArticleService {

	@Autowired
	private SeparatedArticleRepository articleRepository;

	/**
	 * 投稿一覧を取得します.
	 * 
	 * @return 記事一覧情報
	 */
	public List<SeparatedArticle> findAll() {
		return articleRepository.findAll();
	}

	/**
	 * 記事を登録します.
	 * 
	 * @param article 記事情報
	 * @return 登録した記事情報
	 */
	public void save(SeparatedArticle article) {
		articleRepository.insert(article);
	}

	/**
	 * 記事を削除します.
	 * 
	 * @param id 削除したい記事ID
	 */
	public void delete(int id) {
		articleRepository.deleteById(id);
	}

	/**
	 * 記事投稿者名で前方一致検索します.
	 * 
	 * @param name 記事投稿者名
	 * @return 記事とコメントのリスト
	 */
	public List<SeparatedArticle> findByUserName(String name) {
		return articleRepository.findByUserName(name);
	}
}
