package com.QuesSystem.ques.repository;

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
import com.QuesSystem.ques.entity.Userinfo;

@Transactional
@Repository
public interface UserinfoDao extends JpaRepository<Userinfo, String>{
	
	/* 取得使用者資訊(清單)
	 * 使用questionnaireId
	 */
	@Query("select user from Userinfo user where user.questionnaireId = :questionnaireId")
	public List<Userinfo> findListByQuestionnaireId(@Param("questionnaireId")String questionnaireId);
	
	/*
	 * 取得使用者資訊(清單)
	 * (使用於頁面顯示)
	 */
	public List<Userinfo> findByQuestionnaireId(Questionnaire questionnaireId);
	
	/* 取得使用者資訊
	 * 使用userId
	 */
	@Query("select user from Userinfo user where user.userId = :inputUserId")
	public void findByUserId(@Param("inputUserId") String userId);
	
	/* 刪除使用者資訊
	 * 使用userId
	 */
	@Query("delete from Userinfo user where user.userId = :inputUserId")
	public void deleteByUserId(@Param("inputUserId") String userId);
	
	/* 刪除使用者資訊
	 * 使用questionnaireId
	 */
	@Modifying
	@Query("delete from Userinfo user where user.questionnaireId = :inputQuesId")
	public void deleteByQuestionnaireId(@Param("inputQuesId")String[] questionnaireId);
}
