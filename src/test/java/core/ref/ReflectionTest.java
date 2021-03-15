package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        
        for (Field field : clazz.getDeclaredFields()) {
        	logger.debug("field : {}", field.toString());
        }
        
        for (Constructor constructor : clazz.getDeclaredConstructors()) {
        	logger.debug("constructor : {}", constructor.toString());
        }
        
        for (Method method : clazz.getDeclaredMethods()) {
        	logger.debug("method : {}", method.toString());
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
    }
    
    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
    }
    
}
