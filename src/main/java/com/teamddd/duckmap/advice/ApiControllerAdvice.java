package com.teamddd.duckmap.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.teamddd.duckmap.common.ExceptionCodeMessage;
import com.teamddd.duckmap.dto.ErrorResult;
import com.teamddd.duckmap.exception.NotContentTypeImageException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ErrorResult bindException(BindException ex) {
		return ErrorResult.builder()
			.code(String.valueOf(HttpStatus.BAD_REQUEST))
			.message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
			.build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ErrorResult notContentTypeImageException(NotContentTypeImageException ex) {
		return ErrorResult.builder()
			.code(ExceptionCodeMessage.NOT_CONTENT_TYPE_IMAGE_EXCEPTION.code())
			.message(ex.getMessage())
			.build();
	}
}
