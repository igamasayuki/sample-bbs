package jp.co.runy.bbs.joined.domain;

/**
 * コメントを表すエンティティ.
 * 
 * @author igamasayuki
 */
public class JoinedComment {

	/** id */
	public Long id;

	/** 名前 */
	public String name;

	/** コメント */
	public String content;

	/** 関連づく記事ID */
	public Long articleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	
}
