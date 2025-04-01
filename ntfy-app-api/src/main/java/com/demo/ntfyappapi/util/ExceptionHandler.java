package com.demo.ntfyappapi.util;

import com.demo.ntfyappapi.exception.GeneralException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

public final class ExceptionHandler {
    /**
     * Map different types of exceptions to more specific service exceptions
     *
     * @param originalException The original exception thrown
     * @return A mapped exception for better error handling
     */
    private Throwable mapToException(Throwable originalException){
        // Map database-related exceptions
        if (originalException instanceof DataIntegrityViolationException) {
            return new GeneralException("Unable to reject book due to data integrity issues", originalException);
        }

        // Map repository-related exceptions
        if (originalException instanceof DataAccessException) {
            return new GeneralException("Database error occurred during book rejection", originalException);
        }

        return new GeneralException("Unexpected error during book rejection", originalException);
    }

}
