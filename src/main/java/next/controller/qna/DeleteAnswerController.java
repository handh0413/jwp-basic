package next.controller.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController implements Controller {
	private static final Logger log = 
			LoggerFactory.getLogger(DeleteAnswerController.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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
		
		ObjectMapper mapper = new ObjectMapper();
		resp.setContentType("application/json;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.print(mapper.writeValueAsString(result));
		return null;
	}

}
