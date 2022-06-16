package jp.co.runy.bbs.separated.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.runy.bbs.separated.domain.SeparatedArticle;

/**
 * 記事処理関連のレポジトリ.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class SeparatedArticleRepository {

	/**
	 * ＤＢから参照した記事の情報をドメインにセットするRowMappaer.
	 */
	private static final RowMapper<SeparatedArticle> ARTICLE_RESULT_SET_EXTRACTOR = (rs, i) -> {
		SeparatedArticle separatedArticle = new SeparatedArticle();
		separatedArticle.setId(rs.getInt("id"));
		separatedArticle.setName(rs.getString("name"));
		separatedArticle.setContent(rs.getString("content"));
		separatedArticle.setCommentList(new ArrayList<>());
		return separatedArticle;
	};

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 記事を全件表示するメソッド.
	 * 
	 * @return 記事全件
	 */
	public List<SeparatedArticle> findAll() {
		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC";
		List<SeparatedArticle> articleList = jdbcTemplate.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);
		return articleList;
	}

	/**
	 * 記事を追加するメソッド.
	 * 
	 * @param article 追加する記事オブジェクト
	 */
	public void insert(SeparatedArticle article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		String insertSql = "INSERT INTO articles (name,content) VALUES(:name,:content)";

		jdbcTemplate.update(insertSql, param);
	}

	/**
	 * 記事を削除するメソッド.
	 * 
	 * @param id 削除したい記事のＩＤ
	 */
	public void deleteById(int id) {
		String deleteSql = "DELETE FROM articles WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		jdbcTemplate.update(deleteSql, param);
	}

	/**
	 * 記事投稿者の名前で前方一致検索をする.
	 * 
	 * @param name 検索したい名前
	 * @return 該当の記事とコメントのリスト
	 */
	public List<SeparatedArticle> findByUserName(String name) {
		SqlParameterSource sqlParam = new MapSqlParameterSource().addValue("name", name + "%");
		String sql = "SELECT a.id, a.name, a.content, com.id com_id, com.name com_name, com.content com_content,com.article_id "
				+ "FROM articles a LEFT JOIN comments com ON a.id = com.article_id WHERE a.name LIKE :name ORDER BY a.id DESC, com.id;";
		List<SeparatedArticle> articleList = jdbcTemplate.query(sql, sqlParam, ARTICLE_RESULT_SET_EXTRACTOR);

		return articleList;
	}

}
