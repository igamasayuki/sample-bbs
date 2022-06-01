package jp.co.runy.bbs.joined.domain;

/**
 * コメントを表すエンティティ.
 * 
 * @author igamasayuki
 */
public class JoinedComment {

	/** id */
	private Long id;

	/** 名前 */
	private String name;

	/** コメント */
	private String content;

	/** 記事ID */
	private Long articleId;

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

	@Override
	public String toString() {
		return "JoinedComment [id=" + id + ", name=" + name + ", content=" + content + ", articleId=" + articleId + "]";
	}
	
}
