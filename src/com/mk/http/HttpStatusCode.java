package com.mk.http;

public abstract class HttpStatusCode {
    static int OK = 200;
    static int CREATED = 201;
    static int NO_CONTENT = 204;
    static int BAD_REQUEST = 400;
    static int UNAUTHORIZED = 401;
    static int FORBIDDEN = 403;
    static int NOT_FOUND = 404;
    static int METHOD_NOT_ALLOWED = 405;
    static int NOT_ACCEPTABLE = 406;
    static int PROXY_AUTHENTICATION_REQUIRED = 407;
    static int REQUEST_TIMEOUT = 408;
    static int CONFLICT = 409;
    static int GONE = 410;
    static int LENGTH_REQUIRED = 411;
    static int PRECONDITION_FAILED = 412;
    static int REQUEST_ENTITY_TOO_LARGE = 413;
    static int REQUEST_URI_TOO_LONG = 414;
    static int UNSUPPORTED_MEDIA_TYPE = 415;
    static int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    static int EXPECTATION_FAILED = 417;
    static int INTERNAL_SERVER_ERROR = 500;
    static int NOT_IMPLEMENTED = 501;
    static int BAD_GATEWAY = 502;
    static int SERVICE_UNAVAILABLE = 503;
    static int GATEWAY_TIMEOUT = 504;
    static int HTTP_VERSION_NOT_SUPPORTED = 505;
    static int INSUFFICIENT_STORAGE = 507;
    static int PRECONDITION_REQUIRED = 508;
    static int REQUEST_TOO_MANY_REQUESTS = 510;

}
