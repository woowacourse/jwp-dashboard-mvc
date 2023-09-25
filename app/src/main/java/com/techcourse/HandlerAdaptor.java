package com.techcourse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webmvc.org.springframework.web.servlet.ModelAndView;

public interface HandlerAdaptor {

    boolean isHandle(Object handler);

    ModelAndView execute(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
