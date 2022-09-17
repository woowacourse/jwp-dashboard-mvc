package nextstep.mvc.controller.tobe;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.support.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackages;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackages) {
        this.basePackages = basePackages;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        for (Object basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<?>> controllerTypes = reflections.getTypesAnnotatedWith(Controller.class);

            addHandlerExecutions(controllerTypes);
        }
        log.info("Initialized AnnotationHandlerMapping!");
    }

    private void addHandlerExecutions(Set<Class<?>> controllerTypes) {
        for (Class<?> controllerType : controllerTypes) {
            addHandlerExecution(controllerType);
        }
    }

    private void addHandlerExecution(Class<?> controllerType) {
        List<Method> requestMappingMethods = findRequestMappingMethods(controllerType);

        for (Method method : requestMappingMethods) {
            List<HandlerKey> handlerKeys = createHandlerKeys(method);
            handlerKeys.forEach(key -> handlerExecutions.put(key, new HandlerExecution(controllerType, method)));
        }
    }

    private List<Method> findRequestMappingMethods(Class<?> controllerType) {
        Method[] methods = controllerType.getDeclaredMethods();

        return findRequestMappingMethods(methods);
    }

    private List<Method> findRequestMappingMethods(Method[] methods) {
        List<Method> requestMappingMethods = new ArrayList<>();
        for (Method method : methods) {
            addIfRequestMappingMethod(requestMappingMethods, method);
        }
        return requestMappingMethods;
    }

    private void addIfRequestMappingMethod(List<Method> requestMappingMethods, Method method) {
        if (hasRequestMappingAnnotation(method)) {
            requestMappingMethods.add(method);
        }
    }

    private boolean hasRequestMappingAnnotation(Method method) {
        return method.isAnnotationPresent(RequestMapping.class);
    }

    private List<HandlerKey> createHandlerKeys(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String url = requestMapping.value();
        RequestMethod[] requestMethods = requestMapping.method();

        return createHandlerKeys(url, requestMethods);
    }

    private List<HandlerKey> createHandlerKeys(String url, RequestMethod[] requestMethods) {
        return Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(url, requestMethod))
                .collect(Collectors.toList());
    }

    public Object getHandler(final HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        RequestMethod requestMethod = RequestMethod.of(method);
        HandlerKey handlerKey = new HandlerKey(requestURI, requestMethod);

        return handlerExecutions.get(handlerKey);
    }
}
