package com.QuesSystem.ques.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QuesSystem.ques.entity.Question;

@Repository
@Transactional
public interface QuestionDao extends JpaRepository<Question, String> {

	/* 問題清單搜尋
	 * 使用questionId
	 */
	@Query("select ques from Question ques where ques.questionId = :inputQId")
	public List<Question> findListByQuestionId(@Param("inputQId") String questionId);
	
	/* 問題清單搜尋
	 * 使用questionnaireId
	 */
	@Query("select ques from Question ques where ques.questionnaireId = :inputQuesId")
	public List<Question> findListByQuestionnaireId(@Param("inputQuesId") String questionnaireId);
	
	/* 刪除問題
	 * 使用questionnaireId
	 */
	@Modifying
	@Query("delete from Question ques where ques.questionnaireId = :inputQuesId")
	public void deleteByQuestionnaireId(@Param("inputQuesId") String[] questionnaireId);
}
