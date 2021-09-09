package com.techcourse.controller;

import com.techcourse.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginViewControllerTest {

    private final LoginController controller = new LoginController();

    private HttpSession httpSession;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        httpSession = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    @DisplayName("세션 존재 시 메인 화면 응답 테스트")
    void indexTest() {

        // given
        when(httpSession.getAttribute("user")).thenReturn(new User(1L, "test", "test", "test"));
        when(request.getSession()).thenReturn(httpSession);

        // when
        final ModelAndView modelAndView = controller.execute(request, response);

        // then
        assertThat(modelAndView).usingRecursiveComparison().isEqualTo(new ModelAndView(new JspView("redirect:/index.jsp")));
    }

    @Test
    @DisplayName("로그인 화면 응답 테스트")
    void loginViewTest() {

        // given
        when(request.getSession()).thenReturn(httpSession);

        // when
        final ModelAndView modelAndView = controller.execute(request, response);

        // then
        assertThat(modelAndView).usingRecursiveComparison().isEqualTo(new ModelAndView(new JspView("/login.jsp")));
    }
}
