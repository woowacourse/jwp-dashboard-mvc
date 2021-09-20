package nextstep.mvc.controller.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;

public class HandlerExecution {

    private final Object object;
    private final Method method;

    public HandlerExecution(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object methodReturnType = method.invoke(object, request, response);
        if (methodReturnType instanceof String) {
            return new ModelAndView(new JspView((String) methodReturnType));
        }
        return (ModelAndView) methodReturnType;
    }
}
