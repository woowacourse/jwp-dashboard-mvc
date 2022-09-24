package nextstep.mvc.controller.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import nextstep.mvc.view.ModelAndView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestController;

class HandlerExecutionHandlerAdapterTest {

    @DisplayName("HandlerExecution 을 지원해줄 수 있다. (support() 결과가 true 이다.)")
    @Test
    void supportHandlerExecution() throws NoSuchMethodException {
        // given
        final HandlerExecutionHandlerAdapter handlerExecutionHandlerAdapter = new HandlerExecutionHandlerAdapter();

        final Class<TestController> testControllerClass = TestController.class;
        final Method method = testControllerClass.getDeclaredMethod(
                "findUserId", HttpServletRequest.class, HttpServletResponse.class);
        final HandlerExecution handlerExecution = new HandlerExecution("/get-test", method);

        // when
        final boolean result = handlerExecutionHandlerAdapter.supports(handlerExecution);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("HandlerExecution 을 통해서 요청을 처리할 수 있다.")
    @Test
    void handleHandlerExecution() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        final HandlerExecutionHandlerAdapter handlerExecutionHandlerAdapter = new HandlerExecutionHandlerAdapter();
        final AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping("samples");
        handlerMapping.initialize();
        final Object handler = handlerMapping.getHandler(request);

        final ModelAndView modelAndView = handlerExecutionHandlerAdapter.handle(request, response, handler);
        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }
}
