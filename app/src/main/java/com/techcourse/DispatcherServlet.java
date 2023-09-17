package com.techcourse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webmvc.org.springframework.web.servlet.ModelAndView;
import webmvc.org.springframework.web.servlet.View;
import webmvc.org.springframework.web.servlet.mvc.tobe.AnnotationHandlerAdapter;
import webmvc.org.springframework.web.servlet.mvc.tobe.AnnotationHandlerMapping;
import webmvc.org.springframework.web.servlet.mvc.tobe.ExceptionResolver;
import webmvc.org.springframework.web.servlet.mvc.tobe.HandlerAdapter;
import webmvc.org.springframework.web.servlet.mvc.tobe.HandlerAdapterFinder;
import webmvc.org.springframework.web.servlet.mvc.tobe.HandlerMappings;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMappings handlerMappings;
    private HandlerAdapterFinder handlerAdapterFinder;
    private ExceptionResolver exceptionResolver;

    public DispatcherServlet() {
    }

    @Override
    public void init() {
        handlerMappings = initHandlerMappings();
        handlerAdapterFinder = initHandlerAdapterFinder();
        exceptionResolver = new ExceptionResolver();
    }

    private HandlerMappings initHandlerMappings() {
        HandlerMappings handlerMappings = new HandlerMappings(
            List.of(new ManualHandlerMapping(), new AnnotationHandlerMapping("com")));
        handlerMappings.initialize();
        return handlerMappings;
    }

    private HandlerAdapterFinder initHandlerAdapterFinder() {
        return new HandlerAdapterFinder(
            List.of(new ManualHandlerAdapter(), new AnnotationHandlerAdapter()));
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
        throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            Object handler = handlerMappings.getHandler(request);
            HandlerAdapter handlerAdapter = handlerAdapterFinder.find(handler);
            ModelAndView mv = handlerAdapter.handle(request, response, handler);
            render(request, response, mv);
        } catch (Throwable e) {
            ModelAndView mv = exceptionResolver.handle(e);
            try {
                render(request, response, mv);
            } catch (Exception ex) {
                log.error("Exception : {}", e.getMessage(), e);
                throw new ServletException(e.getMessage());
            }
        }
    }

    private void render(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView)
        throws Exception {
        View view = modelAndView.getView();
        view.render(modelAndView.getModel(), request, response);
    }
}
