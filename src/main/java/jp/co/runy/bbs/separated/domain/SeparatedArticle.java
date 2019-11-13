package jp.co.runy.bbs.separated.domain;

import java.util.List;

/**
 * 記事情報を表すドメインクラス.
 * 
 * @author igamasayuki
 *
 */
public class SeparatedArticle {

	/** 記事番号 */
	private Integer id;
	
	/** 投稿者名 */
	private String name;
	
	/** 投稿内容 */
	private String content;
	
	/** 記事についたコメントリスト */
	private List<SeparatedComment> commentList;
	
	public SeparatedArticle() {}
	public SeparatedArticle(Integer id, String name, String content, List<SeparatedComment> commentList) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.commentList = commentList;
	}
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
	public List<SeparatedComment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<SeparatedComment> commentList) {
		this.commentList = commentList;
	}
	@Override
	public String toString() {
		return "SeparatedArticle [id=" + id + ", name=" + name + ", content=" + content + ", commentList=" + commentList
				+ "]";
	}

}

