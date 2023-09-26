package webmvc.org.springframework.web.servlet.mvc.dispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webmvc.org.springframework.web.servlet.ModelAndView;
import webmvc.org.springframework.web.servlet.mvc.handleradapter.DefaultHandlerAdapter;
import webmvc.org.springframework.web.servlet.mvc.handleradapter.HandlerAdapter;
import webmvc.org.springframework.web.servlet.mvc.handleradapter.HandlerAdapterRegistry;
import webmvc.org.springframework.web.servlet.mvc.handleradapter.HandlerExecutionHandlerAdapter;
import webmvc.org.springframework.web.servlet.mvc.handlermapping.AnnotationHandlerMapping;
import webmvc.org.springframework.web.servlet.mvc.handlermapping.DefaultHandlerMapping;
import webmvc.org.springframework.web.servlet.mvc.handlermapping.HandlerMappingRegistry;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingRegistry handlerMapping;
    private final HandlerAdapterRegistry handlerAdapter;

    public DispatcherServlet() {
        this(new HandlerMappingRegistry(Set.of(
                        new AnnotationHandlerMapping("com.techcourse"),
                        new DefaultHandlerMapping())
                ),
                new HandlerAdapterRegistry(Set.of(
                        new HandlerExecutionHandlerAdapter(),
                        new DefaultHandlerAdapter())
                )
        );
    }

    public DispatcherServlet(HandlerMappingRegistry handlerMapping, HandlerAdapterRegistry handlerAdapter) {
        this.handlerMapping = handlerMapping;
        this.handlerAdapter = handlerAdapter;
    }

    @Override
    public void init() {
        handlerMapping.initialize();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        log.debug("Method : {}, Request URI : {}", requestMethod, requestURI);

        try {
            Optional<Object> optionalHandler = handlerMapping.getHandler(request);
            if (optionalHandler.isEmpty()) {
                response.sendRedirect("404.jsp");
                return;
            }
            Object handler = optionalHandler.get();
            HandlerAdapter handlerAdapter = this.handlerAdapter.getHandlerAdapter(handler);
            ModelAndView modelAndView = handlerAdapter.handle(handler, request, response);

            modelAndView.getView().render(modelAndView.getModel(), request, response);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }
}
