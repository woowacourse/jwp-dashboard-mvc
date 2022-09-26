package com.techcourse.controller.v1;

import com.techcourse.repository.InMemoryUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nextstep.mvc.handler.asis.Controller;

public class RegisterViewV1Controller implements Controller {

    private final InMemoryUserRepository userRepository;

    public RegisterViewV1Controller(final InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        return "/register.jsp";
    }
}
