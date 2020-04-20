package hu.vattila.insight.authentication;

public enum AuthConstants {
    AUTHORIZATION_HEADER_NAME("Authorization"),
    TOKEN_PREFIX("Bearer "),
    LOGIN_ENDPOINT("/api/user/login"),
    REFRESH_ENDPOINT("/api/user/refresh_token"),
    WS_ENDPOINT("/api/ws/**"),
    STATIC_IMG("/img/**"),

    TOKEN_SERVER_URL("https://www.googleapis.com/oauth2/v4/token"),
    CLIENT_ID("***REMOVED***"),
    CLIENT_SECRET("***REMOVED***");

    private final String value;

    AuthConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
