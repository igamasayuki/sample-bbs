package jp.co.runy.bbs.joined.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.runy.bbs.joined.domain.JoinedArticle;
import jp.co.runy.bbs.joined.domain.JoinedComment;

/**
 * articlesテーブル操作用のリポジトリクラス.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class JoinedArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * articlesとcommentsテーブルを結合したものからarticleリストを作成する.
	 * articleオブジェクト内にはcommentリストを格納する。
	 */
	private static final ResultSetExtractor<List<JoinedArticle>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {
		// 記事一覧が入るarticleListを生成
		List<JoinedArticle> articleList = new LinkedList<JoinedArticle>();
		List<JoinedComment> commentList = null;
		
		// 前の行の記事IDを退避しておく変数
		long beforeArticleId = 0;
		
		while (rs.next()) {
			// 現在検索されている記事IDを退避
			int nowArticleId = rs.getInt("id");
			
			// 現在の記事IDと前の記事IDが違う場合はArticleオブジェクトを生成
			if (nowArticleId != beforeArticleId) {
				JoinedArticle article = new JoinedArticle();
				article.setId(nowArticleId);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				// 空のコメントリストを作成しArticleオブジェクトにセットしておく
				commentList = new ArrayList<JoinedComment>();
				article.setCommentList(commentList);
				// コメントがセットされていない状態のArticleオブジェクトをarticleListオブジェクトにadd
				articleList.add(article);
			}
			
			// 記事だけあってコメントがない場合はCommentオブジェクトは作らない
			if (rs.getInt("com_id") != 0) {
				JoinedComment comment = new JoinedComment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(rs.getInt("com_article_id"));
				// コメントをarticleオブジェクト内にセットされているcommentListに直接addしている(参照型なのでこのようなことができる)
				commentList.add(comment);
			}
			
			// 現在の記事IDを前の記事IDを入れる退避IDに格納
			beforeArticleId = nowArticleId;
		}
		return articleList;
	};

	/**
	 * 記事一覧を取得します.記事に含まれているコメント一覧も同時に取得します.
	 * 
	 * @return コメントを含んだ記事一覧情報
	 */
	public List<JoinedArticle> findAll() {
		String sql = "SELECT a.id, a.name, a.content, com.id com_id, com.name com_name, com.content com_content,com.article_id com_article_id "
				+ "FROM articles a LEFT JOIN comments com ON a.id = com.article_id ORDER BY a.id DESC, com.id;";
		List<JoinedArticle> articleList = namedParameterJdbcTemplate.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);

		return articleList;
	}

	/**
	 * 記事をインサートします.
	 * 
	 * @param article
	 *            記事
	 * @return 記事
	 */
	public JoinedArticle insert(JoinedArticle article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "INSERT INTO articles(name, content) VALUES(:name, :content)";
		namedParameterJdbcTemplate.update(sql, param);
		return article;
	}

	/**
	 * 記事をDBから削除する. <br>
	 * コメントも同時に削除する。<br>
	 * 
	 * @param id
	 *            削除したい記事ID
	 */
	public void delete(int articleId) {
		SqlParameterSource sqlparam = new MapSqlParameterSource().addValue("id", articleId);
		// CREATE TABLEの際に「article_id integer REFERENCES articles (id) ON DELETE CASCADE」とした場合
		String sql = "DELETE FROM articles WHERE id = :id";
		
		// CREATE TABLEの際に「ON DELETE CASCADE」をしなかった場合
		// 参考URL http://aoyagikouhei.blog8.fc2.com/blog-entry-183.html
//		String sql = "WITH deleted AS (DELETE FROM articles WHERE id = :id RETURNING id)"
//				+ "DELETE FROM comments WHERE article_id IN (SELECT id FROM deleted)";
		
		namedParameterJdbcTemplate.update(sql, sqlparam);
	}
	
	/**
	 * 記事投稿者の名前で前方一致検索をする.
	 * 
	 * @param name　検索したい名前
	 * @return 該当の記事とコメントのリスト
	 */
	public List<JoinedArticle> findByUserName(String name) {
		SqlParameterSource sqlParam = new MapSqlParameterSource().addValue("name", name + "%");
		String sql = "SELECT a.id, a.name, a.content, com.id com_id, com.name com_name, com.content com_content,com.article_id "
				+ "FROM articles a LEFT JOIN comments com ON a.id = com.article_id WHERE a.name LIKE :name ORDER BY a.id DESC, com.id;";
		List<JoinedArticle> articleList = namedParameterJdbcTemplate.query(sql, sqlParam, ARTICLE_RESULT_SET_EXTRACTOR);

		return articleList;
	}
}