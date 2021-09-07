package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class Junit3TestRunner {

    @Test
    void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        final Junit3Test junit3Test = clazz.getConstructor().newInstance();

        final Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String targetName = "test";
            if (method.getName().contains(targetName)) {
                method.invoke(junit3Test);
            }
        }
    }
}
