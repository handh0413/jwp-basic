package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class ShowQuestionController implements Controller  {
	private static final Logger log = LoggerFactory.getLogger(ShowQuestionController.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int questionId = Integer.parseInt(req.getParameter("questionId"));
		QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(questionId);
        log.debug("question : {}", question);
        if (question == null) {
            throw new NullPointerException("질문을 찾을 수 없습니다.");
        }
        AnswerDao answerDao = new AnswerDao();
        List<Answer> answerList = answerDao.findAll(questionId); 
        req.setAttribute("question", question);
        req.setAttribute("answers", answerList);
        return "/qna/show.jsp";
	}
}
