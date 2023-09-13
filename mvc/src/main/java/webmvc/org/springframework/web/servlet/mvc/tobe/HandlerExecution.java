package webmvc.org.springframework.web.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import webmvc.org.springframework.web.servlet.ModelAndView;

public class HandlerExecution {

    private final Object executor;
    private final Method handlerMethod;

    private HandlerExecution(final Object executor, final Method handlerMethod) {
        this.executor = executor;
        this.handlerMethod = handlerMethod;
    }

    public static HandlerExecution of(final Class<?> executor, final Method handlerMethod) throws Exception {
        return new HandlerExecution(executor.getConstructor().newInstance(), handlerMethod);
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return (ModelAndView) handlerMethod.invoke(executor, request, response);
    }
}
