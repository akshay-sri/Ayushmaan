package com.ayushmaan.APIGatewayService;

import java.util.List;

public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/ayushmaan/auth/login",
            "/ayushmaan/auth/register",
            "/ayushmaan/auth/refresh"
    );

    public static boolean isSecured(String path) {

        return openApiEndpoints
                .stream()
                .noneMatch(path::startsWith);
    }
}
