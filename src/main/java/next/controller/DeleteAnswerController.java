package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.User;

public class DeleteAnswerController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
        User user = (User)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
        if (user == null) {
        	req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
        
        AnswerDao answerDao = new AnswerDao();
        int answerId = Integer.parseInt(req.getParameter("answerId"));
        answerDao.delete(answerId);
        
        String questionId = req.getParameter("answerQuestionId");
        req.setAttribute("questionId", questionId);
        return "/qna/show";
	}

}
