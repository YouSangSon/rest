package yousang.backend.rest.response;

import lombok.Getter;

@Getter
public class ApiResponse {
    private final int statusCode;
    private final String message;
    private final Object data;

    public ApiResponse(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int statusCode, String message) {
        this(statusCode, message, null);
    }
}