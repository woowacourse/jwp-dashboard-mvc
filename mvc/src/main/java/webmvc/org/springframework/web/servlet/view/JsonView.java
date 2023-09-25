package webmvc.org.springframework.web.servlet.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.org.springframework.http.MediaType;
import webmvc.org.springframework.web.servlet.View;

import java.io.PrintWriter;
import java.util.Map;
import java.util.NoSuchElementException;

public class JsonView implements View {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String json = getJsonString(model);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        final PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }

    private String getJsonString(Map<String, ?> model) {
        if (model.size() == 1) {
            return model.values().stream()
                    .map(this::writeValueAsString)
                    .findAny()
                    .orElseThrow(NoSuchElementException::new);
        }
        return writeValueAsString(model);
    }

    private String writeValueAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JsonProcessingException: ", e);
        }
    }

}
