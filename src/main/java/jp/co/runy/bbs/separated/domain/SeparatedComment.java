package jp.co.runy.bbs.separated.domain;

/**
 * コメント情報を表すドメインクラス.
 * 
 * @author igamasayuki
 *
 */
public class SeparatedComment {

	/** id */
	private Long id;

	/** 名前 */
	private String name;

	/** コメント */
	private String content;

	/** 関連づく記事ID */
	private Long articleId;

	public SeparatedComment(){}
	
	public SeparatedComment(Long id, String name, String content, Long articleId) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.articleId = articleId;
	}

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
		return "SeparatedComment [id=" + id + ", name=" + name + ", content=" + content + ", articleId=" + articleId
				+ "]";
	}

}
