package com.github.fsousa1987.attornatus.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResponse {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
