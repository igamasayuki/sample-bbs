package jp.co.runy.bbs.joined.domain;

import java.util.List;

/**
 * 記事を表すエンティティ.
 * 
 * @author igamasayuki
 */
public class JoinedArticle {

	/** id */
	public Integer id;

	/** 名前 */
	public String name;

	/** 内容 */
	public String content;

	/** コメント一覧 */
	public List<JoinedComment> commentList;

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

	public List<JoinedComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<JoinedComment> commentList) {
		this.commentList = commentList;
	}
	
}
