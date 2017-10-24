package com.spring.multitenancy.Multitenancy.exception;

import org.springframework.http.HttpStatus;

/*
 *
 * This is an user define exception class which handles Application exceptions
 * */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 4265758284484258031L;
    /*
     * String constant representing Unhandled Exception message string
     */
    public static final String UNHANDLED_EXCEPTION_TXT = "Unhandled Exception !!! ";
    /**
     * This field represents message .
     *
     */
    private String message;
    /**
     * This field represents is user logged in or not .
     *
     */
    private boolean logged;
    /**
     * This field represents ErrorCode.
     *
     */
    private IErrorCode errorCode;

    private HttpStatus httpStatusCode;

    public ApplicationException(final IErrorCode code) {
        this.errorCode = code;
    }

    public ApplicationException(final String message, final HttpStatus httpStatusCode) {
        this.setMessage(message);
        this.httpStatusCode = httpStatusCode;
    }

    public ApplicationException(final String message, final IErrorCode code) {
        this.setMessage(message);
        this.errorCode = code;
    }

    public ApplicationException(final String message, final IErrorCode errorCode, final Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.setMessage(message);
    }

    public ApplicationException(final String message, final Throwable throwable) {
        super(throwable);
        this.setMessage(message);
    }

    public ApplicationException(final String message, final HttpStatus httpStatusCode, IErrorCode errorCode) {
        this.setMessage(message);
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
    }

    /**
     * @return the ErrorCode object
     */
    public IErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the logged
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * @param logged
     *            the logged to set
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }



}

