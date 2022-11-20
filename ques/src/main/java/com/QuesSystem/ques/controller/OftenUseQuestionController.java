package com.QuesSystem.ques.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.service.ifs.OftenUseQuestionService;
import com.QuesSystem.ques.service.ifs.QuestionService;

@Controller
public class OftenUseQuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private OftenUseQuestionService oftenUseQuestionService;

	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	// �s�W�`�ΰ��D
	@PostMapping(value = { "/backAddOftenUse" }, params = "create")
	public String createQuestion(Model model,
								 RedirectAttributes redirectAttrs,
								 @RequestParam("title") String title,
								 @RequestParam("choices") String choices,
								 @RequestParam("type") int type,
								 @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin) throws ParseException {

		if (title.isEmpty() && choices.isEmpty()) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "�|����J���D���D�H�ΰ��D�^��");
			return "redirect:/backAddOftenUse";
		}

		try {
			// �`�ΰ��D���b
			String ErrorMsg = oftenUseQuestionService.ErrorMsg(title, choices, type);
			if (!ErrorMsg.isEmpty()) {
				model.addAttribute("ErrorMsg", ErrorMsg);
				return "backAddOftenUse";
			}

			OftenUseQuestion often = new OftenUseQuestion();
			// *�]�w���D���D
			often.setTitle(title);
			// *�]�w���D�^��
			often.setChoices(choices);
			// *�]�w���D����
			often.setType(type);
			// *�]�w�O�_�������^��
			often.setMustKeyin(mustKeyin);
			oftenUseQuestionDao.save(often);
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "�|����J���D���D�H�ΰ��D�^��"); // �ק�
			return "redirect:/backAddOftenUse";
		}

		redirectAttrs.addFlashAttribute("oftenUseMsg", "�`�ΰ��D�إߧ���");
		return "redirect:/backOftenUse";
	}
	
	// �s��`�ΰ��D
	@PostMapping(value = { "/backAddOftenUse" }, params = "edit")
	public String editQuestion(Model model, 
			                   RedirectAttributes redirectAttrs,
			                   HttpSession session,
			                   @RequestParam(name = "ID", required = false) String id,
			                   @RequestParam("title") String title,
			                   @RequestParam("choices") String choices, 
			                   @RequestParam("type") int type,
			                   @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin) throws ParseException {
        OftenUseQuestion changeoften = (OftenUseQuestion) session.getAttribute("changeoften");
        String oftenId = changeoften.getOftenUseId();

		// �`�ΰ��D���b
		String ErrorMsg = oftenUseQuestionService.ErrorMsg(title, choices, type);
		if(!ErrorMsg.isEmpty()) {
			model.addAttribute("ErrorMsg", ErrorMsg);
			return "rediredt:/backAddOftenUse?ID=" + oftenId;
		}		

		try {		
		    // *�]�w���D���D
			changeoften.setTitle(title);
		    // *�]�w���D�^��
			changeoften.setChoices(choices);
		    // *�]�w���D����
			changeoften.setType(type);
		    // *�]�w�O�_�������^��
			changeoften.setMustKeyin(mustKeyin);
		    oftenUseQuestionDao.save(changeoften);
		}
		catch (Exception e){
			redirectAttrs.addFlashAttribute("oftenUseMsg", "�|����J���D���D�H�ΰ��D�^��"); //�ק�
		    return "redirect:/backAddOftenUse";
		}

		redirectAttrs.addFlashAttribute("oftenUseMsg", "�`�ΰ��D�إߧ���");
		return "redirect:/backOftenUse";
	}
	
	// �R���`�ΰ��D
	@GetMapping(value = "/backOftenUse", params = "delete")
	public String deleteByOftenUseQues(Model model,
									   RedirectAttributes redirectAttrs,
									   @RequestParam(name = "ID", required = false) String[] id) {
		if (id == null) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "�|�����o�ӱ`�ΰ��D");
			return "redirect:/backOftenUse";
		}

		oftenUseQuestionService.deleteOftenUseQuestion(id);

		redirectAttrs.addFlashAttribute("oftenUseMsg", "�`�ΰ��D�R�����\");
		return "redirect:/backOftenUse";
	}

	// �j�M�`�ΰ��D
	@GetMapping(value = { "/backOftenUse" }, params = "search")
	public String backOftenUseListSearchKey(Model model,
											RedirectAttributes redirectAttrs,
											@RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
											@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
											@RequestParam(name = "title", required = false, defaultValue = "") String title) {

		if (title.isEmpty()) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "�|����J�������");
			return "redirect:/backOftenUse";
		} else if (title.length() < 2) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "����r�ܤֻݭn��2�Ӧr");
			return "redirect:/backOftenUse";
		}

		Page<OftenUseQuestion> oftenuses = null;

		if (!title.isEmpty()) {
			oftenuses = oftenUseQuestionService.searchOftenUseByoftenuseTitle(pageNum, pageSize, title);
		} else {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "�|����J�������");
			return "redirect:/backOftenUse";
		}

		model.addAttribute("oftenuses", oftenuses);

		return UrlPath.Path.URL_BACK_OFTENUSE;
	}
}
