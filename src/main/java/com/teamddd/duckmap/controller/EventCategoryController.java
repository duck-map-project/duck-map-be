package com.teamddd.duckmap.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamddd.duckmap.dto.event.category.CreateEventCategoryReq;
import com.teamddd.duckmap.dto.event.category.CreateEventCategoryRes;
import com.teamddd.duckmap.dto.event.category.EventCategoryRes;
import com.teamddd.duckmap.dto.event.category.UpdateEventCategoryReq;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events/categories")
public class EventCategoryController {

	@Operation(summary = "이벤트 카테고리 생성")
	@PostMapping
	public CreateEventCategoryRes createEventCategory(
		@Validated @RequestBody CreateEventCategoryReq createEventCategoryReq) {
		return CreateEventCategoryRes.builder()
			.id(1L)
			.build();
	}

	@Operation(summary = "이벤트 카테고리 목록 조회")
	@GetMapping
	public Page<EventCategoryRes> getEventCategories(Pageable pageable) {
		return new PageImpl<>(List.of(
			EventCategoryRes.builder()
				.id(1L)
				.category("생일카페")
				.build(),
			EventCategoryRes.builder()
				.id(2L)
				.category("전시회")
				.build(),
			EventCategoryRes.builder()
				.id(3L)
				.category("럭키드로우")
				.build()
		));
	}

	@Operation(summary = "이벤트 카테고리 수정")
	@PutMapping("/{id}")
	public void updateEventCategory(@PathVariable Long id,
		@Validated @RequestBody UpdateEventCategoryReq updateEventCategoryReq) {
	}

	@Operation(summary = "이벤트 카테고리 삭제")
	@DeleteMapping("/{id}")
	public void deleteEventCategory(@PathVariable Long id) {
	}
}
