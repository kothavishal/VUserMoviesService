package com.vishal.error;

public enum Error {

    UNREACHABLE_SERVICE(0, ErrorCategory.EXTERNAL_SERVICES),
    BAD_INPUT(1, ErrorCategory.INVALID_INPUT),
    BAD_USER(2, ErrorCategory.AUTHENTICATION),
    BAD_MOVIE(3, ErrorCategory.INVALID_ID),
    UNKNOWN_USER(4, ErrorCategory.INVALID_ID),    
    EXTERNAL_SERVICE(5, ErrorCategory.EXTERNAL_SERVICES),
	INTERNAL_ERROR(6, ErrorCategory.INTERNAL_ERRORS);

    public final int code;

    public final ErrorCategory category;

    private Error(int code, ErrorCategory category) {
        this.code = code;
        this.category = category;
    }

}

