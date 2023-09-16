package com.techcourse.support.web.adapter;

import com.techcourse.support.web.resolver.ViewResolvers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import webmvc.org.springframework.web.servlet.ModelAndView;
import webmvc.org.springframework.web.servlet.View;
import webmvc.org.springframework.web.servlet.mvc.HandlerAdapter;
import webmvc.org.springframework.web.servlet.mvc.asis.Controller;

public class ManualHandlerMappingAdapter implements HandlerAdapter {

    private final ViewResolvers resolvers;

    public ManualHandlerMappingAdapter(final ViewResolvers resolvers) {
        validateViewResolvers(resolvers);

        this.resolvers = resolvers;
    }

    private void validateViewResolvers(final ViewResolvers resolvers) {
        if (resolvers.isEmpty()) {
            throw new IllegalArgumentException("viewResolver를 지정해주세요.");
        }
    }

    @Override
    public boolean supports(final Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView execute(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler
    ) throws Exception {
        final String viewName = ((Controller) handler).execute(request, response);
        final View view = resolvers.findView(request, viewName);
        final ModelAndView modelAndView = new ModelAndView(view);

        addModelData(request, modelAndView);

        return modelAndView;
    }

    private void addModelData(final HttpServletRequest request, final ModelAndView modelAndView) {
        final HttpSession session = request.getSession();
        final Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            final String attributeName = attributeNames.nextElement();
            final Object attributeValue = session.getAttribute(attributeName);

            modelAndView.addObject(attributeName, attributeValue);
        }
    }
}