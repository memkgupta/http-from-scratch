package com.mk.http;

public class RequestLine {
    private String method;
    private String uri;
    private String version;
    public RequestLine(String line)
    {
        String[] fields = line.split(" ");
        this.method = fields[0];
        this.uri = fields[1].substring(1);
        this.version = fields[2];

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
