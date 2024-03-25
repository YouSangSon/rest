package yousang.backend.rest.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ConfigProperties {
    private Redis redis;
    private Database database;

    @Data
    @NoArgsConstructor
    public static class Redis {
        private String host;
        private int port;
    }

    @Data
    @NoArgsConstructor
    public static class Database {
        private String url;
        private String username;
        private String password;
    }
}