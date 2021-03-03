package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
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
        
        QuestionDao questionDao = new QuestionDao();
        int questionId = Integer.parseInt(req.getParameter("answerQuestionId"));
        
        Question question = questionDao.findByQuestionId(questionId);
        List<Answer> answers = answerDao.findAll(questionId);
        req.setAttribute("question", question);
        req.setAttribute("answers", answers);
        return "/qna/show.jsp";
	}

}
