package com.techcourse.controller.regacy;

import com.techcourse.domain.User;
import com.techcourse.repository.InMemoryUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webmvc.org.springframework.web.servlet.mvc.asis.Controller;

public class RegisterController implements Controller {

    @Override
    public String execute(final HttpServletRequest req, final HttpServletResponse res) {
        if (!"POST".equals(req.getMethod())) {
            return "/register.jsp";
        }
        final var user = new User(2,
            req.getParameter("account"),
            req.getParameter("password"),
            req.getParameter("email"));
        InMemoryUserRepository.save(user);

        return "redirect:/index.jsp";
    }
}
