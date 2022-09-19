package nextstep.mvc.controller.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;
import org.junit.jupiter.api.Test;
import samples.TestController;

class ManualHandlerAdapterTest {

    @Test
    void supports() {
        ManualHandlerAdapter manualHandlerAdapter = new ManualHandlerAdapter();

        boolean supports = manualHandlerAdapter.supports(new TestController());

        assertThat(supports).isTrue();
    }

    @Test
    void notSupports() throws Exception {
        Class<?> clazz = Class.forName("samples.TestController");
        Method method = clazz.getMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        ManualHandlerAdapter manualHandlerAdapter = new ManualHandlerAdapter();

        boolean supports = manualHandlerAdapter.supports(new HandlerExecution(method));

        assertThat(supports).isFalse();
    }

    @Test
    void handle() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        ManualHandlerAdapter manualHandlerAdapter = new ManualHandlerAdapter();

        ModelAndView modelAndView = manualHandlerAdapter.handle(request, response, new TestController());

        JspView view = (JspView) modelAndView.getView();
        Field field = view.getClass().getDeclaredField("viewName");
        field.setAccessible(true);

        assertThat(field.get(view)).isEqualTo("redirect:/index.jsp");
    }
}
