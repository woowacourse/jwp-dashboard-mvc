package nextstep.mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nextstep.mvc.exception.HandlerNotFoundException;
import nextstep.mvc.support.HandlerAdapter;
import nextstep.mvc.support.HandlerAdapterRegistry;
import nextstep.mvc.support.HandlerMapping;
import nextstep.mvc.support.HandlerMappingRegistry;
import nextstep.mvc.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingRegistry handlerMappingRegistry;
    private final HandlerAdapterRegistry handlerAdapterRegistry;

    public DispatcherServlet() {
        handlerMappingRegistry = new HandlerMappingRegistry();
        handlerAdapterRegistry = new HandlerAdapterRegistry();
    }

    @Override
    public void init() {
        handlerMappingRegistry.init();
    }

    public void addHandlerMapping(final HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMapping(handlerMapping);
    }

    public void addHandlerAdapter(final HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        log.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());

        try {
            final Optional<Object> wrappedHandler = handlerMappingRegistry.getHandler(request);
            if (wrappedHandler.isEmpty()) {
                throw new HandlerNotFoundException();
            }
            final HandlerAdapter handlerAdapter = handlerAdapterRegistry.getHandlerAdapter(wrappedHandler.get());
            final ModelAndView modelAndView = handlerAdapter.handle(request, response, wrappedHandler.get());
            if (modelAndView != null) {
                modelAndView.render(request, response);
            }
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }
}
