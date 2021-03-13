package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController extends AbstractController {
	private static final Logger log = 
			LoggerFactory.getLogger(DeleteAnswerController.class);
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		long answerId = Long.parseLong(req.getParameter("answerId"));
		
		AnswerDao answerDao = new AnswerDao();
		int affectedRows = answerDao.delete(answerId);
		
		Result result = null;
		if (affectedRows > 0) {
			result = Result.ok();
		} else {
			result = Result.fail("delete operation failed");
		}
		
		log.debug("result : {}", result);
		return jsonView().addObject("result", result);
	}

}
