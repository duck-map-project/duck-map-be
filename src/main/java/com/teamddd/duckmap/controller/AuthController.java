package com.teamddd.duckmap.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamddd.duckmap.config.security.TokenDto;
import com.teamddd.duckmap.dto.user.auth.LoginReq;
import com.teamddd.duckmap.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	private static final long COOKIE_EXPIRATION = 7776000;

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<?> login(@Validated @RequestBody LoginReq loginUserRQ) {

		// User 등록 및 Refresh Token 저장
		TokenDto tokenDto = authService.login(loginUserRQ);

		return ResponseEntity.ok()
			// AT 저장
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
			.build();
	}

	@PostMapping("/validate")
	public ResponseEntity<?> validate(@RequestHeader("Authorization") String requestAccessToken) {
		if (!authService.validate(requestAccessToken)) {
			return ResponseEntity.status(HttpStatus.OK).build(); // 재발급 필요X
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 재발급 필요
		}
	}

	// 토큰 재발급
	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@RequestHeader("Authorization") String requestAccessToken) {
		TokenDto reissuedTokenDto = authService.reissue(requestAccessToken);

		if (reissuedTokenDto != null) { // 토큰 재발급 성공
			return ResponseEntity
				.status(HttpStatus.OK)
				// AT 저장
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
				.build();

		} else { // Refresh Token 탈취 가능성
			// 재로그인 유도
			return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.build();
		}
	}

	// 로그아웃
	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
		authService.logout(requestAccessToken);
		ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
			.maxAge(0)
			.path("/")
			.build();

		return ResponseEntity
			.status(HttpStatus.OK)
			.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
			.build();
	}

}

