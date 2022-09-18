package com.techcourse.controllerV2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.support.RequestMethod;

@Controller
public class ErrorController {

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView getNotFoundErrorPage(final HttpServletRequest req, final HttpServletResponse res) {
        return new ModelAndView(new JspView("redirect:/404.jsp"));
    }
}
