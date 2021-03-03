package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;

public class CreateAnswerController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
        User user = (User)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
        if (user == null) {
        	req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
        
        String writer = user.getName();
        String contents = req.getParameter("answerContents");
        String questionId = req.getParameter("answerQuestionId");
        
        Answer answer = new Answer(writer, contents, Integer.parseInt(questionId));
        AnswerDao answerDao = new AnswerDao();
        answerDao.insert(answer);
        
        req.setAttribute("questionId", req.getParameter("answerQuestionId"));
        return "/qna/show";
	}

}
