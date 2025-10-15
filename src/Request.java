import java.util.ArrayList;
import java.util.HashMap;

public class Request {
    private String method;
    private String uri;
    private HashMap<String,String> headers;
    private String body;

    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
        this.headers = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
