package webmvc.org.springframework.web.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webmvc.org.springframework.web.servlet.ModelAndView;
import webmvc.org.springframework.web.servlet.mvc.asis.HandlerAdapter;
import webmvc.org.springframework.web.servlet.mvc.asis.HandlerMapping;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(final Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(final HandlerMapping handlerMapping, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HandlerExecution handler = (HandlerExecution) handlerMapping.getHandler(request);
        return handler.handle(request, response);
    }
}