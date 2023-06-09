package com.teamddd.duckmap.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.teamddd.duckmap.dto.user.CreateMemberReq;
import com.teamddd.duckmap.entity.Member;
import com.teamddd.duckmap.repository.MemberRepository;

@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;

	@DisplayName("생성된 회원 확인")
	@Test
	void createMember() throws Exception {
		//given
		CreateMemberReq request = new CreateMemberReq();
		ReflectionTestUtils.setField(request, "username", "user1");
		ReflectionTestUtils.setField(request, "email", "string@string.com");
		ReflectionTestUtils.setField(request, "password", "@Aaaa1234523");
		//when
		Long memberId = memberService.join(request);
		//then
		assertThat(memberId).isNotNull();
		Optional<Member> findMember = memberRepository.findById(memberId);
		assertThat(findMember.get())
			.extracting("username")
			.isEqualTo("user1");
	}
}
