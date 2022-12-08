package com.QuesSystem.ques.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.entity.Questionnaire;

@Transactional
@Repository
public interface OftenUseQuestionDao extends JpaRepository<OftenUseQuestion, String> {
	
	/*
	 * ���o�`�ΰ��D
	 * �ϥ�oftenuseTitle(�`�ΰ��D���D)
	 */
	@Query("select often from OftenUseQuestion often where often.oftenuseTitle like %:title%")
	public Page<OftenUseQuestion> findByOftenuseTitle(Pageable pageable,
			                                   @Param("title") String oftenuseTitle);	
}
