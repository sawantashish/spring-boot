package com.spring.multitenancy.Multitenancy.exception;


/**
 * Set of error Codes that are used for indicating specific error scenarios
 *
 * @author Devang.Ghugharawala
 *
 */
public enum ErrorCode implements IErrorCode {

    INTERNAL_SERVER_ERROR("internal.server.error"),
    INSUFFICIENT_PARAMETERS("1001"),
    INVALID_INPUT("invalid.input"),
    ACCESS_DENIED("user.unauthorized"),
    GENERIC_ERROR("server.error"),
    FILE_OPERATION_ERROR("1005"),
    PARSE_EXCEPTION("1006"),
    ACCOUNT_LOCKED("account.locked"),
    FILE_EMPTY("2000"),
    BASE_DB_ERROR("server.error"),
    ROLES_NOT_FOUND("roles.not.found");

    private final String code;

    private ErrorCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

