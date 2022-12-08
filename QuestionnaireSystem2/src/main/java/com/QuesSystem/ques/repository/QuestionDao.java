package com.QuesSystem.ques.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;

@Transactional
@Repository
public interface QuestionDao extends JpaRepository<Question, String> {

	/* 
	 * ���D�M��j�M(�ϥ�questionId)
	 */	
	@Query("select ques from Question ques where ques.questionId = :inputQId")
	public List<Question> findListByQuestionId(@Param("inputQId") String questionId);
	
	/* 
	 * ���D�M��j�M(�ϥ�questionnaireId)
	 * (�ϥΩ󭶭���ܰ��D)
	 */
	public List<Question> findListByQuestionnaireId(Questionnaire questionnaireId);
	/* 
	 * ���D�M��j�M(�ϥ�questionnaireId)
	 */
	@Query("select ques from Question ques where ques.questionnaireId = :questionnaireId")
	public List<Question> findByQuestionnaireId(@Param("questionnaireId") String questionnaireId);
	
	/* 
	 * ���D�j�M(�ϥ�questionId)
	 */
	@Query("select ques from Question ques where ques.questionId = :inputQId")
	public void findByQuestionId(@Param("inputQId") String questionId);
	
	/* 
	 * �R�����D(�ϥ�questionnaireId)
	 */
	@Modifying
	@Query("delete from Question ques where ques.questionnaireId = :inputQuesId")
	public void deletebyQuestionnaireId(@Param("inputQuesId") String[] questionnaireId);
}
