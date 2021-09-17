package com.techcourse.controller;

import com.techcourse.domain.User;
import com.techcourse.exception.DuplicatedUserException;
import com.techcourse.repository.InMemoryUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.support.RequestMethod;

@Controller
public class RegisterController {

    @RequestMapping(value = "/register/view", method = RequestMethod.GET)
    public ModelAndView view(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView(new JspView("/register.jsp"));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(HttpServletRequest req, HttpServletResponse res) {
        final User user = new User(2,
                req.getParameter("account"),
                req.getParameter("password"),
                req.getParameter("email"));
        try {
            InMemoryUserRepository.save(user);
        } catch (DuplicatedUserException e) {
            res.setStatus(409);
            return new ModelAndView(new JspView("409.jsp"));
        }
        return new ModelAndView(new JspView("redirect:/index.jsp"));
    }
}
