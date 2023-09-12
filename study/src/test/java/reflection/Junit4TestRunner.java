package reflection;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

class Junit4TestRunner {

    @Test
    void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        // TODO Junit4Test에서 @MyTest 애노테이션이 있는 메소드 실행
        final Junit4Test junit4Test = clazz.getDeclaredConstructor().newInstance();

        final Method[] declaredMethods = clazz.getDeclaredMethods();

        for (final Method method : declaredMethods) {
            final MyTest methodAnnotation = method.getAnnotation(MyTest.class);
            if (methodAnnotation != null) {
                assertThat(methodAnnotation).isNotNull();
                method.invoke(junit4Test);
            }
        }
    }
}
