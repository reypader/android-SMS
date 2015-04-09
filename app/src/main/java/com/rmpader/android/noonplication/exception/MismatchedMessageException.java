package com.rmpader.android.noonplication.exception;

/**
 * Created by Reynald on 4/4/2015.
 */
public class MismatchedMessageException extends Exception {

    public MismatchedMessageException() {
        super();
    }

    public MismatchedMessageException(String detailMessage) {
        super(detailMessage);
    }

    public MismatchedMessageException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public MismatchedMessageException(Throwable throwable) {
        super(throwable);
    }
}
