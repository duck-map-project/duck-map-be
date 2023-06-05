package com.teamddd.duckmap.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamddd.duckmap.dto.ImageRes;
import com.teamddd.duckmap.dto.user.CreateMemberReq;
import com.teamddd.duckmap.dto.user.CreateMemberRes;
import com.teamddd.duckmap.dto.user.MemberRes;
import com.teamddd.duckmap.dto.user.UpdateMemberReq;
import com.teamddd.duckmap.dto.user.UpdatePasswordReq;
import com.teamddd.duckmap.entity.Role;
import com.teamddd.duckmap.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberService;

	@Operation(summary = "회원 가입")
	@PostMapping("/join")
	public CreateMemberRes createUser(@Validated @RequestBody CreateMemberReq createMemberReq) {
		Long id = memberService.join(createMemberReq);
		return CreateMemberRes.builder()
			.id(id)
			.build();
	}

	@Operation(summary = "회원 정보 조회", description = "로그인한 회원 정보 조회")
	@GetMapping("/me")
	public MemberRes getUserInfo() {
		return MemberRes.builder()
			.id(1L)
			.username("user1")
			.email("sample@naver.com")
			.userProfile(
				ImageRes.builder()
					.filename("user1.jpg")
					.build()
			)
			.role(Role.USER)
			.loginAt(LocalDateTime.now())
			.build();
	}

	@Operation(summary = "회원정보 수정", description = "로그인한 회원의 닉네임, 프로필 사진 변경 요청")
	@PutMapping("/me")
	public void updateUser(@Validated @RequestBody UpdateMemberReq updateMemberReq) {
		memberService.updateUsername(updateMemberReq.getUsername());
	}

	@Operation(summary = "비밀번호 변경", description = "로그인한 회원 비밀번호 변경")
	@PatchMapping("/me/password")
	public void updatePassword(@Validated @RequestBody UpdatePasswordReq updatePasswordReq) {
		memberService.updatePassword(updatePasswordReq.getCurrentPassword(), updatePasswordReq.getNewPassword());
	}

	@Operation(summary = "회원 탈퇴")
	@DeleteMapping("/me")
	public void deleteUser(HttpSession session) {
	}

}
