package core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        
        for (Method method : clazz.getDeclaredMethods()) {
        	String methodName = method.getName();
        	if (methodName.startsWith("test")) {
        		method.invoke(clazz.newInstance());
        	}
        }
    }
}
