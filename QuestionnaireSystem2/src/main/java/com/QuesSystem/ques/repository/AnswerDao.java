package com.QuesSystem.ques.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QuesSystem.ques.entity.Answer;

@Repository
@Transactional
public interface AnswerDao extends JpaRepository<Answer, String>{

	/* ���o����
	 * �ϥ�answerId
	 */
	@Query("select ans from Answer ans where ans.answerId = :inputAnswerId")
	public List<Answer> findByAnswerId(@Param("inputAnswerId") String answerId);
	
	/* ���o����
	 * �ϥ�userId
	 */
	@Query("select ans from Answer ans where ans.userId = :inputUserId")
	public List<Answer> findByUserId(@Param("inputUserId") String userId);
	
	/* ���o����
	 * �ϥ�questionnaireId
	 */
	@Query("select ans from Answer ans where ans.questionnaireId = :inputQuesId")
	public List<Answer> findByQuestionnaireId(@Param("inputQuesId") String questionnaireId);
	
	/* �R������
	 * �ϥ�questionnaireId
	 */
	@Query("delete from Answer ans where ans.questionnaireId = :inputQuesId")
	public void deleteByQuestionnaireId(@Param("inputQuesId") String questionnaireId);
}
