$(document).ready(function() {
	var questionnaireSession = '[[${session.questionnaire}]]';

	if (questionnaireSession != null) {
		$('#Link-Questionnaire-tab').removeClass('active');
		$('#formQuestionnaire').removeClass('active show');

		$('#Link-Questions-tab').addClass('active');
		$('#formQuestions').addClass('active show');
	}
})