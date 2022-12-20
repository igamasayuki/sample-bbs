package com.example.joined.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.joined.domain.JoinedComment;
import com.example.joined.repository.JoinedCommentRepository;

/**
 * コメント関連サービス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class JoinedCommentService {

	@Autowired
	private JoinedCommentRepository commentRepository;

	/**
	 * コメントを登録します.
	 * 
	 * @param comment コメント情報
	 * @return 登録したコメント情報
	 */
	public void save(JoinedComment comment) {
		commentRepository.insert(comment);
	}
}
