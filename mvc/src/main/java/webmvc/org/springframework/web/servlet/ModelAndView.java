package webmvc.org.springframework.web.servlet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private final Object view;
    private final Map<String, Object> model;

    public ModelAndView(final Object view) {
        this.view = view;
        this.model = new HashMap<>();
    }

    public ModelAndView addObject(final String attributeName, final Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Object getObject(final String attributeName) {
        return model.get(attributeName);
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public String getViewName() {
        return this.view instanceof String ? (String) view : null;
    }
}
