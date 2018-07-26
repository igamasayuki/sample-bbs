package jp.co.runy.bbs.separated.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.runy.bbs.separated.domain.SeparatedComment;

/**
 * コメント処理関連のレポジトリ.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class SeparatedCommentRepository {

	/**
	 * ＤＢから参照したコメントの情報をドメインにセットするRowMapper.
	 */
	private static final RowMapper<SeparatedComment> COMMENT_RESULT_SET_EXTRACTOR = (rs, i) -> {
		Long id = rs.getLong("id");
		String name = rs.getString("name");
		String content = rs.getString("content");
		Long articleId = rs.getLong("article_id");
		return new SeparatedComment(id, name, content, articleId);
	};

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 指定した記事のコメントを取り出すメソッド.
	 * 
	 * @param articleId
	 *            取り出したいコメントの記事番号
	 * @return 指定した記事番号のコメントリスト
	 */
	public List<SeparatedComment> findByArticleId(int articleId) {
		String sql = "SELECT id,name,content,article_id FROM comments WHERE article_id=:articleId ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<SeparatedComment> commentList = jdbcTemplate.query(sql, param, COMMENT_RESULT_SET_EXTRACTOR);
		return commentList;
	}

	/**
	 * コメントを追加するメソッド.
	 * 
	 * @param comment
	 *            追加するコメントオブジェクト
	 */
	public void insert(SeparatedComment comment) {
		String insertSql = "INSERT INTO comments (name,content,article_id) VALUES(:name,:content,:articleId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		jdbcTemplate.update(insertSql, param);
	}

	/**
	 * コメントを削除するメソッド.
	 * 
	 * @param id
	 *            削除したい記事のＩＤ
	 */
	public void deleteByArticleId(int id) {
		String deleteSql = "DELETE FROM comments WHERE article_id=:articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", id);

		jdbcTemplate.update(deleteSql, param);
	}

}
