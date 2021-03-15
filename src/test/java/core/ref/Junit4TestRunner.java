package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.User;

public class Junit4TestRunner {
	private Logger logger = LoggerFactory.getLogger(Junit4TestRunner.class);
	
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        for (Method method : clazz.getDeclaredMethods()) {
        	if(method.isAnnotationPresent(MyTest.class)) {
        		method.invoke(clazz.newInstance());
        	}
        }
    }
    
    @Test
    public void userTest() throws Exception {
    	Class<User> clazz = User.class;
    	
    	for(Constructor constructor : clazz.getDeclaredConstructors()) {
    		logger.debug(constructor.toString());
    		
    		String userId = "donghee.han";
    		String password = "1234";
    		String name = "동희";
    		String email = "dongee.han@test.com";
    		
    		User user = (User) constructor.newInstance(userId, password, name, email);
    		logger.debug(user.toString());
    	}
    }
}
