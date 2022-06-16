package jp.co.runy.bbs.joined.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * コメントのリクエストパラメータが入るフォーム.
 * 
 * @author igamasayuki
 *
 */
public class JoinedCommentForm {
	/** 記事ID. */
	private Integer articleId;

	/** コメント者名. */
	@NotNull(message = "コメント者名は必須入力です")
	@Size(min = 1, max = 127, message = "コメント者名は1桁以上127桁以下で入力してください")
	private String name;

	/** コメント内容. */
	@NotNull(message = "コメントは必須入力です")
	@Size(min = 1, message = "コメントは必須入力です")
	private String content;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
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

	@Override
	public String toString() {
		return "JoinedCommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
	}

}
