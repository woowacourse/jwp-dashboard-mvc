package webmvc.org.springframework.web.servlet.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    boolean supports(Object handler);
    Object handle(HttpServletRequest request, HttpServletResponse response, final Object handler) throws Exception;

}
