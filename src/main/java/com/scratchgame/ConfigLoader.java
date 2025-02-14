package com.scratchgame;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    private JsonNode config;

    public ConfigLoader(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        config = objectMapper.readTree(new File(filePath));
    }

    public JsonNode getConfig() {
        return config;
    }
}
