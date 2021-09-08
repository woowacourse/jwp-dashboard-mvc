package com.techcourse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nextstep.mvc.controller.asis.Controller;
import nextstep.mvc.controller.tobe.HandlerExecution;
import nextstep.mvc.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ManualHandlerAdapterTest {

    private ManualHandlerAdapter manualHandlerAdapter;

    @BeforeEach
    void setUp() {
        manualHandlerAdapter = new ManualHandlerAdapter();
    }

    @DisplayName("handler 지원 여부를 물었을 때")
    @Nested
    class Supports {

        @DisplayName("handler가 Controller 구현체인 경우 true를 반환한다.")
        @Test
        void supportsTrue() {
            // given
            Object handler = mock(Controller.class);

            // when, then
            assertThat(manualHandlerAdapter.supports(handler)).isTrue();
        }

        @DisplayName("handler가 Controller 구현체가 아닌 경우 false를 반환한다.")
        @Test
        void supportsFalse() {
            // given
            Object handler = mock(HandlerExecution.class);

            // when, then
            assertThat(manualHandlerAdapter.supports(handler)).isFalse();
        }
    }

    @DisplayName("handle 요청을 수행했을 때")
    @Nested
    class Handle {

        private HttpServletRequest request;
        private HttpServletResponse response;

        @BeforeEach
        void setUp() {
            request = mock(HttpServletRequest.class);
            response = mock(HttpServletResponse.class);
        }

        @DisplayName("Controller 구현체를 handler로 전달할 경우 ModelAndView를 반환한다.")
        @Test
        void handleReturnModelAndView() throws Exception {
            // given
            Controller handler = mock(Controller.class);
            ModelAndView modelAndView = mock(ModelAndView.class);

            when(handler.execute(request, response)).thenReturn("wow");

            // when, then
            assertThat(manualHandlerAdapter.handle(request, response, handler))
                .isInstanceOf(ModelAndView.class);
        }

        @DisplayName("Controller 구현체가 아닌 handler를 전달할 경우 예외가 발생한다.")
        @Test
        void handleException() {
            // given
            HandlerExecution handler = mock(HandlerExecution.class);

            // when, then
            assertThatThrownBy(() -> manualHandlerAdapter.handle(request, response, handler))
                .isExactlyInstanceOf(ClassCastException.class);
        }
    }
}