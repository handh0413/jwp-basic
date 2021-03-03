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
        int questionId = Integer.parseInt(req.getParameter("answerQuestionId"));
        
        Answer answer = new Answer(writer, contents, questionId);
        AnswerDao answerDao = new AnswerDao();
        answerDao.insert(answer);

        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(questionId);
        List<Answer> answerList = answerDao.findAll(questionId); 
        req.setAttribute("question", question);
        req.setAttribute("answers", answerList);
        
        return "/qna/show.jsp";
	}

}
