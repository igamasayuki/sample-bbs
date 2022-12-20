package com.example.joined.domain;

/**
 * コメントを表すエンティティ.
 * 
 * @author igamasayuki
 */
public class JoinedComment {

	/** id */
	private Integer id;

	/** 名前 */
	private String name;

	/** コメント */
	private String content;

	/** 記事ID */
	private Integer articleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

}
