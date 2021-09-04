package reflection;

import annotation.Controller;
import annotation.Repository;
import annotation.Service;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReflectionsTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    void showAnnotationClass() throws Exception {
        Reflections reflections = new Reflections("examples");

        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(Service.class);
        Set<Class<?>> repositories = reflections.getTypesAnnotatedWith(Repository.class);

        Set<Class<?>> classes = new LinkedHashSet<>();
        classes.addAll(controllers);
        classes.addAll(services);
        classes.addAll(repositories);

        for (Class<?> clazz : classes) {
            System.out.println(clazz.toString());
        }
    }
}
