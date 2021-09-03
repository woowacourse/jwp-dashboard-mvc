package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Junit4TestRunner {

    @Test
    void run() throws Exception {
        final Class<Junit4Test> clazz = Junit4Test.class;
        final Junit4Test junit4Test = clazz.getConstructor().newInstance();
        final Method[] methods = clazz.getDeclaredMethods();

        Arrays.stream(methods)
              .filter(method -> method.isAnnotationPresent(MyTest.class))
              .forEach(method -> {
                  try {
                      method.invoke(junit4Test);
                  } catch (IllegalAccessException | InvocationTargetException e) {
                      e.printStackTrace();
                  }
              });
    }
}
