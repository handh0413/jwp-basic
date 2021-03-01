package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class UpdateQuestionController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
        User user = (User)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
        if (user == null) {
        	req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
        int questionId = Integer.parseInt(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(questionId);
        
        question.setTitle(req.getParameter("title"));
        question.setWriter(req.getParameter("writer"));
        question.setContents(req.getParameter("contents"));
        questionDao.update(question);
        
        return "redirect:/";
	}

}
