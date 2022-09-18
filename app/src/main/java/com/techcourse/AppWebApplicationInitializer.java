package com.techcourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContext;
import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.controller.asis.ManualHandlerAdaptor;
import nextstep.mvc.controller.tobe.AnnotationHandlerAdaptor;
import nextstep.mvc.controller.tobe.AnnotationHandlerMapping;
import nextstep.web.WebApplicationInitializer;

public class AppWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger log = LoggerFactory.getLogger(AppWebApplicationInitializer.class);
    private static final String ROOT_PATH = "/";

    @Override
    public void onStartup(final ServletContext servletContext) {
        final var dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.addHandlerMapping(new ManualHandlerMapping());
        dispatcherServlet.addHandlerMapping(new AnnotationHandlerMapping("src"));

        dispatcherServlet.addHandlerAdapter(new ManualHandlerAdaptor());
        dispatcherServlet.addHandlerAdapter(new AnnotationHandlerAdaptor());

        final var dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(ROOT_PATH);

        log.info("Start AppWebApplication Initializer");
    }
}
