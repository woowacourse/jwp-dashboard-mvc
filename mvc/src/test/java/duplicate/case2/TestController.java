package duplicate.case2;

import context.org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.org.springframework.web.bind.annotation.RequestMapping;
import web.org.springframework.web.bind.annotation.RequestMethod;
import webmvc.org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(samples.TestController.class);

    @RequestMapping(value = "/get-test", method = RequestMethod.GET)
    public ModelAndView duplicatedMethod1(final HttpServletRequest request,
                                         final HttpServletResponse response) {
        return null;
    }

    @RequestMapping(value = "/get-test", method = RequestMethod.GET)
    public ModelAndView duplicatedMethod2(final HttpServletRequest request,
                                         final HttpServletResponse response) {
        return null;
    }
}