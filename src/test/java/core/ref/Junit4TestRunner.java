package core.ref;

import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Junit4TestRunner {
	private Logger logger = LoggerFactory.getLogger(Junit4TestRunner.class);

	@Test
	public void run() throws Exception {
		Class<Junit4Test> clazz = Junit4Test.class;

		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(MyTest.class)) {
				method.invoke(clazz.newInstance());
			}
		}
	}

}
