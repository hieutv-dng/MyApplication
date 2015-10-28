package com.hieugmail.hieu.service.core;

import android.content.Context;

import lombok.Value;
import lombok.experimental.Builder;

/**
 * A configure is used to create
 */
@Value
@Builder
public class ApiConfig {
    private Context context;
    private String baseUrl;
}
