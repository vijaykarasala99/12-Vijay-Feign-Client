package com.vijaystack.feign.exception;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DownstreamResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(DownstreamResourceNotFoundException exception) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of("timestamp", Instant.now(), "status", 404, "message", exception.getMessage()));
	}

	@ExceptionHandler(DownstreamServiceException.class)
	public ResponseEntity<Map<String, Object>> handleDownstreamException(DownstreamServiceException exception) {

		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(Map.of("timestamp", Instant.now(), "status", 502, "message", exception.getMessage()));
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<Map<String, Object>> handleFeignException(FeignException exception) {

		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
				Map.of("timestamp", Instant.now(), "status", 502, "message", "External service returned an error"));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException exception) {

		String message = exception.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).findFirst()
				.orElse("Invalid request");

		return ResponseEntity.badRequest().body(Map.of("timestamp", Instant.now(), "status", 400, "message", message));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception exception) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("timestamp", Instant.now(), "status", 500, "message", "Internal server error"));
	}
}