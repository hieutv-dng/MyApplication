package com.hieugmail.hieu.service.core;

import lombok.Value;
import lombok.experimental.Builder;

/**
 * Class custom error api.
 */
@Value
@Builder
public class ApiError {
    public static final ApiError ERROR_NETWORK = ApiError.builder()
            .error(ApiError.Error.ERROR_NETWORK)
            .build();

    public static final ApiError ERROR_PLEASE_TRY_LATER = ApiError.builder()
            .error(ApiError.Error.ERROR_PLEASE_TRY_LATER)
            .build();

    public static final ApiError ERROR_CONVERSION = ApiError.builder()
            .error(ApiError.Error.ERROR_CONVERSION)
            .build();

    private Error error;

    /**
     * Define Error
     */
    @Value
    @Builder
    public static class Error {
        public static final Error ERROR_NETWORK = ApiError.Error.builder()
                .message("Khong co ket noi internet")
                .build();

        public static final Error ERROR_PLEASE_TRY_LATER = ApiError.Error.builder()
                .message("Vui long thu lai sau")
                .build();
        public static final Error ERROR_CONVERSION = ApiError.Error.builder()
                .message("Loi boc du lieu")
                .build();

        private String code;
        private String message;
    }
}
