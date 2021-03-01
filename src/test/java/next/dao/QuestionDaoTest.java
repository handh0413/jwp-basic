package next.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Question;
import next.model.User;

public class QuestionDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() throws Exception {
    	Question question = new Question("한동희", "제목", "내용");
    	QuestionDao questionDao = new QuestionDao();
    	questionDao.insert(question);
    	
    	Question expected = questionDao.findByQuestionId(9);
    	assertEquals(expected.getTitle(), "제목");
    	assertEquals(expected.getWriter(), "한동희");
    	assertEquals(expected.getContents(), "내용");
    	
    	question.setQuestionId(expected.getQuestionId());
    	assertEquals(expected, question);
    	
    	expected = questionDao.findByQuestionId(1);
    	expected.setCountOfAnswer(expected.getCountOfAnswer() + 1);
    	expected.setTitle("변경한 제목");
    	questionDao.update(question);
    	
    	assertEquals(expected.getCountOfAnswer(), 1);
    	assertEquals(expected.getTitle(), "변경한 제목");
    }

    @Test
    public void findAll() throws Exception {
        QuestionDao questionDao = new QuestionDao();
        List<Question> users = questionDao.findAll();
        assertEquals(8, users.size());
    }
}