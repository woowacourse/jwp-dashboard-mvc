package com.techcourse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webmvc.org.springframework.web.servlet.ControllerHandlerAdapter;
import webmvc.org.springframework.web.servlet.HandlerAdapter;
import webmvc.org.springframework.web.servlet.HandlerAdapterRegistry;
import webmvc.org.springframework.web.servlet.HandlerExecutionHandlerAdapter;
import webmvc.org.springframework.web.servlet.HandlerMappingRegistry;
import webmvc.org.springframework.web.servlet.ModelAndView;
import webmvc.org.springframework.web.servlet.mvc.tobe.AnnotationHandlerMapping;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final transient HandlerMappingRegistry handlerMappingRegistry;
    private final transient HandlerAdapterRegistry handlerAdapterRegistry;

    public DispatcherServlet() {
        this.handlerMappingRegistry = new HandlerMappingRegistry(
                new ManualHandlerMapping(),
                new AnnotationHandlerMapping("com.techcourse.controller")
        );

        this.handlerAdapterRegistry = new HandlerAdapterRegistry(new ControllerHandlerAdapter(),
                new HandlerExecutionHandlerAdapter()
        );
    }

    @Override
    public void init() {
        handlerMappingRegistry.initialize();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            Object handler = handlerMappingRegistry.getHandler(request)
                    .orElseThrow(IllegalArgumentException::new);
            HandlerAdapter handlerAdapter = handlerAdapterRegistry.getHandlerAdapter(handler);
            ModelAndView modelAndView = handlerAdapter.handle(handler, request, response);
            modelAndView.render(request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }


}
