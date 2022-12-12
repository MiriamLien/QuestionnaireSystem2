## QuestionnaireSystem2 動態問卷(管理)系統
#### 基本功能
- 前台可以填寫、搜尋問卷以及觀看統計數據
- 登入後台後可以設計、管理問卷(編輯和刪除)以及觀看統計數據
- 後台可以觀看所有使用者作答的詳細內容，並匯出成表單(CSV檔)
- 後台可以設計、管理常用問題(編輯和刪除)，並套用至設計問題內
<br />

#### 資料庫(MySQL)
<br />

#### 起始頁面
`listPage.html`
<br />
<br />

#### 目錄結構描述
[../src/main/resources/templates]
前台頁面
>`listPage.html` 問卷列表頁面
>
>`formPage.html` 填寫問卷頁面
>
>`confirmPage.html` 確認填寫內容頁面
>
>`answerDetail.html` 統計頁面
>
>`login.html` (前往後台)登入頁面
<br />

後台頁面
>`backListPage.html` 問卷管理列表頁面
>
>`backAddquestionnaire.html` 新增/編輯問卷、問題頁面以及觀看填寫詳細內容和統計頁面
>
>`backOftenUse.html` 常用問題管理頁面
>
>`backAddOftenUse.html` 新增常用問題頁面
<br />

[../src/main/java/com/QuesSystem/ques]
entity
>`Account.java` 帳戶entity
>
>`Answer.java` 回答entity
>
>`OftenUseQuestion.java` 常用問題entity
>
>`Question.java` 問題entity
>
>`Questionnaire.java` 問卷entity
>
>`Userinfo.java` 使用者資訊entity
<br />

repository
>`AccountDao.java` 帳戶Dao
>
>`AnswerDao.java` 回答Dao
>
>`OftenUseQuestionDao.java` 常用問題Dao
>
>`QuestionDao.java` 問題Dao
>
>`QuestionnaireDao.java` 問卷Dao
>
>`UserinfoDao.java` 使用者資訊Dao
<br />

service - ifs
>`AccountService.java` 帳戶service
>
>`AmountService.java` 統計數量service
>
>`AnswerService.java` 回答service
>
>`OftenUseQuestionService.java` 常用問題service
>
>`QuestionService.java` 問題service
>
>`QuestionnaireService.java` 問卷service
>
>`UserinfoService.java` 使用者資訊service
<br />

service - impl
>`AccountServiceImpl.java` 帳戶service
>
>`AmountService.java` 統計數量service
>
>`AnswerServiceImpl.java` 回答service
>
>`OftenUseQuestionServiceImpl.java` 常用問題service
>
>`QuestionServiceImpl.java` 問題service
>
>`QuestionnaireServiceImpl.java` 問卷service
>
>`UserinfoServiceImpl.java` 使用者資訊service
<br />

controller
>`ListPageController.java` 前台列表頁controller
>
>`FormPageController.java` 前台填寫問卷頁controller
>
>`ConfirmPageController.java` 前台確認填寫內容頁controller
>
>`AnswerDetailController.java` 統計頁controller
>
>`LoginController.java` 登入頁controller
>
>`BackListPageController.java` 後台列表頁controller
>
>`OftenUseQuestionController.java` 常用問題controller
>
>`QuestionnaireController.java` 問卷controller
>
>`QuestionController.java` 問題controller
>
>`UserAndAnswerInfoController.java` 使用者和回答資訊controller
<br />

constant
>`AlertMessage.java` 提示訊息
>
>`UrlPath.java` Url網址
<br />

enums
>`QuestionnaireState.java` 問卷狀態
>
>`QuestionType.java` 問題類型
<br />

model
>`Amount.java` 統計數量Model
>
>`AnswerDetailToCSV.java` 詳細回答(轉成CSV檔)Model
>
>`Answers.java` 回答Model
>
>`ChoicesInfo.java` 回答資訊Model
>
>`QuestionnaireInfo.java` 問卷資訊Model
>
>`TotalAnswerVal.java` 答案統計Model
>
>`TotalAnswerVal2.java` 答案統計Model
<br />

[../src/test/java/com/QuesSystem/ques]
>`AccountTest.java` 新建帳戶資料
<br />

#### 後台登入帳密
- 帳號: admin
- 密碼: 12345678
