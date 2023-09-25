package webmvc.org.springframework.web.servlet.mvc.tobe.handler_mapping;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import webmvc.org.springframework.web.servlet.mvc.tobe.handler.Handler;
import webmvc.org.springframework.web.servlet.mvc.tobe.handler.NotFoundHandler;

public class HandlerMappings {

    private final List<HandlerMapping> handlerMappings;

    public HandlerMappings(final HandlerMapping... handlerMappings) {
        this.handlerMappings = Arrays.stream(handlerMappings)
            .collect(Collectors.toUnmodifiableList());
    }

    public Handler getHandler(final HttpServletRequest request) {
        return handlerMappings.stream()
            .map(handlerMapping -> handlerMapping.getHandler(request))
            .filter(Objects::nonNull)
            .findAny()
            .orElseGet(NotFoundHandler::new);
    }

    public void initialize() {
        for (final HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }
}