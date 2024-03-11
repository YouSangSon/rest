package yousang.backend.rest.response;

public class ApiResponse {

    private final int statusCode;
    private final String message;
    private final Object data;

    public ApiResponse(int statusCode, String message) {
        this(statusCode, message, null);
    }

    public ApiResponse(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}