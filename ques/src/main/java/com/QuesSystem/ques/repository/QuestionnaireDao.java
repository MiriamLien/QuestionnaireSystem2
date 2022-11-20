package com.QuesSystem.ques.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QuesSystem.ques.entity.Questionnaire;

@Repository
@Transactional
public interface QuestionnaireDao extends JpaRepository<Questionnaire, String> {
	
	/* 問卷清單搜尋
	 * 使用questionnaireId
	 */
	public List<Questionnaire> findListByQuestionnaireId(String questionnaireId);
	
	/* 問卷搜尋
	 * 使用questionnaireId
	 */
	@Query("select q from Questionnaire q where q.questionnaireId = :inputQuesId")
	public void findByQuestionnaireId(@Param("inputQuesId") String questionnaireId);
	
	/* 問卷搜尋
	 * 使用questionnaireTitle
	 */
	@Query("select q from Questionnaire q where q.questionnaireTitle = :inputQuesTitle")
	public void findByQuestionnaireTitle(@Param("inputQuesTitle") String questionnaireTitle);
	
	/* 問卷搜尋(List)
	 * 使用questionnaireStates = 1
	 */
	@Query("select q from Questionnaire q where q.questionnaireStates = :inputQuesStates")
	public List<Questionnaire> findByQuesStates(@Param("inputQuesStates") boolean questionnaireStates);
	
	/* 問卷搜尋(List)
	 * 使用startDate或是endDate
	 */
//	@Query("select q from Questionnaire q where q.startDate = :inputStartDate or q.endDate = :inputEndDate")
//	public List<Questionnaire> findByStartDateOrEndDate(@Param("inputStartDate") Date startDate,
//			                                            @Param("inputEndDate") Date endDate);
//	
//	/* 問卷搜尋(List)
//	 * 使用startDate和endDate
//	 */
//	@Query("select q from Questionnaire q where q.startDate = :inputStartDate and q.endDate = :inputEndDate")			
//	public List<Questionnaire> findByStartDateAndEndDate(@Param("inputStartDate") Date startDate,
//			                                             @Param("inputEndDate") Date endDate);
//	
//	/* 問卷更新
//	 * 使用questionnaireId
//	 */
//	@Modifying
//	@Query("update Questionnaire q set q.questionnaireTitle = :inputQuesTitle, q.questionnaireBody = :inputQuesBody"
//			+ " ,q.startDate = :inputStartDate, q.endDate = :inputEndDate, q.questionnaireStates = :inputQuesStates where q.questionnaireId = :inputQuesId")
//	public void updateByQuestionnaireId(@Param("inputQuesTitle") String questionnaireTitle,
//			                            @Param("inputQuesBody") String questionnaireBody,
//			                            @Param("inputStartDate") Date startDate,
//                                      @Param("inputEndDate") Date endDate,
//			                            @Param("inputQuesStates") boolean questionnaireStates,
//			                            @Param("inputQuesId") String questionnaireId);	
	
	/* 刪除問卷
	 * 使用questionnaireId
	 */
	@Modifying
	@Query("delete from Questionnaire q where q.questionnaireId = :inputQuesId")
	public void deleteByQuestionnaireId(@Param("inputQuesId") String questionnaireId);
	
	
	/* 前台問卷搜尋
	 * 使用questionnaireStates(問卷狀態)
	 */
	@Query("select q from Questionnaire q where q.questionnaireStates = :inputQuesStates")
	 public Page<Questionnaire> findByPageableFront(Pageable pageable,
	                          						@Param("inputQuesStates") boolean questionnaireStates);
	 
	/* 前台問卷搜尋
	 * 使用問卷標題(questionnaireTitle)和問卷狀態
	 */
	 @Query("select q from Questionnaire q where q.questionnaireTitle like %:title% and q.questionnaireStates = :inputQuesStates")
	 public Page<Questionnaire> findByQuestionnaireTitleFront(Pageable pageable,
			 										 		  @Param("title") String questionnaireTitle,
			 										 		  @Param("inputQuesStates") boolean questionnaireStates);
	 
	 /*
	  * 後台問卷 & 常用問題的搜尋功能
	  * 使用questionnaireTitle
	  */
	 @Query("select q from Questionnaire q where q.questionnaireTitle like %:title%")
	 public Page<Questionnaire> findByQuestionnaireTitle(Pageable pageable,
			 							 				 @Param("title") String questionnaireTitle);
	 
	 /*
	  * 搜尋功能
	  * 使用開始與結束日期(startDate&endDate)
	  */
	 @Query("select q " + "from Questionnaire q"
	      + " where q.endDate is null and q.startDate >= :startDate and q.startDate < :endDatePlus"
	      + " or q.startDate >= :startDate and q.endDate < :endDatePlus")
	 public Page<Questionnaire> findByStartDateAndEndDate(Pageable pageable, 
	                                     				  @Param("startDate") Date startDate, 
	                                     				  @Param("endDatePlus") Date endDatePlus);
}
