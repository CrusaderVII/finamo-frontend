package com.egor.finamo.frontend.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class GraphqlQueryLoader {

    public String getQuery(String filename) {
        File file = FileUtils.getFile(getClass().getClassLoader()
                .getResource(filename)
                .getPath());
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static String getCollectionVariable(Collection<String> values) {
        StringBuilder builder = new StringBuilder("[");

        values.forEach(value -> builder.append("\"")
                .append(value)
                .append("\","));

        builder.delete(builder.length()-1, builder.length());
        builder.append("]");
        return builder.toString();
    }
}
