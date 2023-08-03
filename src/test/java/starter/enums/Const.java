package starter.enums;

public enum Const {

    ACCEPT_HEADER("Accept", "application/vnd.heroku+json; version=3"),
    AUTH_TOKEN("Authorization", "Bearer 31295c43-30a7-4600-93b7-f8ebe92071e8"),
    API_PATH("api","v1/search/demo"),
    DETAIL_ERROR("detail.error","true"),

    SCHEMA_PATH("json schema path","schema/"),
    UNAUTHORIZED("message","There were no credentials in your `Authorization` header. Try `Authorization: Bearer <OAuth access token>` or `Authorization: Basic <base64-encoded email + \":\" + password>`."),

    USER_NAME("name", "Yuriy");

    private String field;
    private String value;

    Const(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getField() {
        return field;
    }
}
