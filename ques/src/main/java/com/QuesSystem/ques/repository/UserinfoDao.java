package com.QuesSystem.ques.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;

@Repository
@Transactional
public interface UserinfoDao extends JpaRepository<Userinfo, String>{

	/* ���o�^���H�C��
	 * �ϥ�questionnaireId
	 */
	@Query("select user from Userinfo user where user.questionnaireId = :inputQuesId")
	public List<Userinfo> findListByQuestionnaireId(@Param("inputQuesId") String questionnaireId);
	
	public List<Userinfo> findByQuestionnaireId(Questionnaire questionnaireId);
	
	/* ���o�^���H
	 * �ϥ�userId
	 */
	@Query("select user from Userinfo user where user.userId = :inputUserId")
	public void findByUserId(@Param("inputUserId") String userId);
	
	/* �R���^���H
	 * �ϥ�userId
	 */
	@Modifying
	@Query("delete from Userinfo user where user.questionnaireId = :inputQuesId")
	public void deleteByQuestionnaireId(@Param("inputQuesId") String[] questionnaireId);
}
