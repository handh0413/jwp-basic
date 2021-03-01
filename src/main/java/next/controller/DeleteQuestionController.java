package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.User;

public class DeleteQuestionController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
        User user = (User)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
        if (user == null) {
        	req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
        
        QuestionDao questionDao = new QuestionDao();
        int questionId = Integer.parseInt(req.getParameter("questionId"));
        questionDao.delete(questionId);
        
        return "redirect:/";
	}

}
