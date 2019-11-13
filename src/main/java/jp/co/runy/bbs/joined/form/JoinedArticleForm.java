package jp.co.runy.bbs.joined.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 記事のリクエストパラメータが入るフォーム.
 * 
 * @author igamasayuki
 *
 */
public class JoinedArticleForm {
	/** 投稿ID. */
	private int id;
	
	/** 投稿者名. */
	@NotNull(message = "投稿者名は値を入力してください")
	@Size(min = 1, max = 127, message = "投稿者名は1桁以上127桁以下で入力してください")
	private String name;
	
	/** 投稿内容. */
	@NotNull(message = "投稿内容は値を入力してください")
	@Size(min = 1, message = "値を入力してください")
	private String content;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	@Override
	public String toString() {
		return "JoinedArticleForm [id=" + id + ", name=" + name + ", content=" + content + "]";
	}
	
}
