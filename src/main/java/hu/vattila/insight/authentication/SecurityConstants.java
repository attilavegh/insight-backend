package hu.vattila.insight.authentication;

public enum SecurityConstants {
    AUTHORIZATION_HEADER  ("Authorization"),
    TOKEN_PREFIX("Bearer "),
    LOGIN_ENDPOINT("/api/user/login");

    private final String value;

    SecurityConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
