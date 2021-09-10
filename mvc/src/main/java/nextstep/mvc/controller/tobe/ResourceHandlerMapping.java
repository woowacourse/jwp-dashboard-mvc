package nextstep.mvc.controller.tobe;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nextstep.mvc.HandlerMapping;

public class ResourceHandlerMapping implements HandlerMapping {

    private static final char DIRECTORY_CHARACTER = '/';
    private final String defaultDirectory;
    private final Map<String, String> resources = new HashMap<>();

    public ResourceHandlerMapping(final String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }

    @Override
    public void initialize() {
        for (final Path path : scanAllFiles()) {
            final String requestPath = DIRECTORY_CHARACTER + path.getFileName().toString();
            final String actualPath = path.toString();

            resources.put(requestPath, actualPath);
        }
    }

    private List<Path> scanAllFiles() {
        try {
            return Files.walk(Paths.get(defaultDirectory))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        } catch (IOException ioException) {
            throw new IllegalArgumentException(String.format("존재하지 않는 디렉토리입니다.(%s)", defaultDirectory));
        }
    }

    @Override
    public Object getHandler(final HttpServletRequest request) {
        final String resource = request.getRequestURI();
        if (resources.containsKey(resource)) {
            return new ResourceHandler(resources.get(resource));
        }
        return null;
    }
}
