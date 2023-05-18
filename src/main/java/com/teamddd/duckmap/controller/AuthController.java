package com.teamddd.duckmap.controller;

import javax.servlet.http.HttpSession;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamddd.duckmap.dto.LoginUserReq;
import com.teamddd.duckmap.dto.LoginUserRes;
import com.teamddd.duckmap.dto.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	@Operation(summary = "로그인")
	@PostMapping("/login")
	public Result<LoginUserRes> login(@Validated @RequestBody LoginUserReq loginUserRQ) {
		return Result.<LoginUserRes>builder()
			.data(
				LoginUserRes.builder()
					.id(1L)
					.username("사용자1")
					.image("img.png")
					.build()
			)
			.build();
	}

	@Operation(summary = "로그아웃")
	@GetMapping("/logout")
	public Result<Void> logout(HttpSession session) {
		return Result.<Void>builder()
			.build();
	}
}