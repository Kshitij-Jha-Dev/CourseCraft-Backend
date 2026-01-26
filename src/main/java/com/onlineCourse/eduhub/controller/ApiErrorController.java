package com.onlineCourse.eduhub.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@Slf4j
public class ApiErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {

        Object statusObj = request.getAttribute("jakarta.servlet.error.status_code");
        int status = (statusObj instanceof Integer) ? (Integer) statusObj : 500;

        Object messageObj = request.getAttribute("jakarta.servlet.error.message");
        String message = (messageObj != null && !messageObj.toString().isBlank())
                ? messageObj.toString()
                : "Something went wrong";

        Object pathObj = request.getAttribute("jakarta.servlet.error.request_uri");
        String path = pathObj != null ? pathObj.toString() : "N/A";

        log.error("Error occurred: status={}, path={}, message={}", status, path, message);

        return ResponseEntity.status(status).body(Map.of(
                "timestamp", Instant.now().toString(),
                "success", false,
                "status", status,
                "error", getErrorName(status),
                "message", message,
                "path", path
        ));
    }

    private String getErrorName(int status) {
        return switch (status) {
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 405 -> "Method Not Allowed";
            case 500 -> "Internal Server Error";
            default -> "Error";
        };
    }
}