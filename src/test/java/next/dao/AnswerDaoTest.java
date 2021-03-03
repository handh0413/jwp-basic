package next.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Answer;

public class AnswerDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() throws Exception {
    	Answer answer = new Answer("한동희", "내용", 8);
    	AnswerDao answerDao = new AnswerDao();
    	answerDao.insert(answer);
    	
    	Answer expected = answerDao.findByAnswerId(6);
    	assertEquals(expected.getWriter(), "한동희");
    	assertEquals(expected.getContents(), "내용");
    	assertEquals(expected.getQuestionId(), 8);
    	
    	answer = answerDao.findByAnswerId(1);
    	answer.setWriter("변경한동희");
    	answer.setContents("변경내용");
    	answerDao.update(answer);
    	
    	expected = answerDao.findByAnswerId(1);
    	assertEquals(expected.getWriter(), "변경한동희");
    	assertEquals(expected.getContents(), "변경내용");
    	
    	answerDao.delete(6);
    	expected = answerDao.findByAnswerId(6);
    	assertNull(expected);
    }

    @Test
    public void findAll() throws Exception {
    	AnswerDao answerDao = new AnswerDao();
    	List<Answer> answers = answerDao.findAll(8);
    	assertEquals(3, answers.size());
    }
}