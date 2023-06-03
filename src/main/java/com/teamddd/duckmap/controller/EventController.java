package com.teamddd.duckmap.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamddd.duckmap.dto.ImageRes;
import com.teamddd.duckmap.dto.artist.ArtistRes;
import com.teamddd.duckmap.dto.artist.ArtistTypeRes;
import com.teamddd.duckmap.dto.event.category.EventCategoryRes;
import com.teamddd.duckmap.dto.event.event.CreateEventReq;
import com.teamddd.duckmap.dto.event.event.CreateEventRes;
import com.teamddd.duckmap.dto.event.event.EventRes;
import com.teamddd.duckmap.dto.event.event.EventSearchParam;
import com.teamddd.duckmap.dto.event.event.EventsRes;
import com.teamddd.duckmap.dto.event.event.HashtagRes;
import com.teamddd.duckmap.dto.event.event.UpdateEventReq;
import com.teamddd.duckmap.dto.review.ReviewRes;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

	@Operation(summary = "이벤트 등록", description = "address 형식 변경 가능성 있음")
	@PostMapping
	public CreateEventRes createEvent(@Validated @RequestBody CreateEventReq createEventReq) {
		return CreateEventRes.builder()
			.id(1L)
			.build();
	}

	@Operation(summary = "이벤트 pk로 조회")
	@GetMapping("/{id}")
	public EventRes getEvent(@PathVariable Long id) {
		ImageRes imageRes = ImageRes.builder()
			.apiUrl("/images/")
			.filename("filename.png")
			.build();

		return EventRes.builder()
			.id(id)
			.storeName("상호명")
			.inProgress(true)
			.fromDate(LocalDateTime.now().minusDays(2L))
			.toDate(LocalDateTime.now().plusDays(1L))
			.address("주소")
			.businessHour("10:00 - 18:00")
			.hashtag("#뫄뫄 #생일축하해")
			.twitterUrl("https://twitter.com/home?lang=ko")
			.artists(List.of(
				ArtistRes.builder()
					.id(2L)
					.groupId(1L)
					.name("태연")
					.image(imageRes)
					.artistType(
						ArtistTypeRes.builder()
							.id(1L)
							.type("아이돌")
							.build()
					)
					.build()
			))
			.categories(List.of(
				EventCategoryRes.builder()
					.id(1L)
					.category("생일카페")
					.build()
			))
			.images(List.of(imageRes))
			.score(4.5)
			.like(true)
			.likeCount(23)
			.bookmark(false)
			.reviews(List.of(
				ReviewRes.builder()
					.id(1L)
					.userProfile(imageRes)
					.username("user_nickname")
					.createdAt(LocalDateTime.now().minusDays(2))
					.score(5)
					.content("review content")
					.photos(List.of(
						imageRes,
						imageRes
					))
					.build(),
				ReviewRes.builder()
					.id(2L)
					.userProfile(imageRes)
					.username("user2_nickname")
					.createdAt(LocalDateTime.now().minusDays(3))
					.score(4)
					.content("review content")
					.photos(List.of(imageRes))
					.build()
			))
			.build();
	}

	@Operation(summary = "이벤트 수정")
	@PutMapping("/{id}")
	public void updateEvent(@PathVariable Long id, @Validated @RequestBody UpdateEventReq updateEventReq) {
	}

	@Operation(summary = "이벤트 삭제")
	@DeleteMapping("/{id}")
	public void deleteEvent(@PathVariable Long id) {
	}

	@Operation(summary = "이벤트 목록 조회")
	@GetMapping
	public Page<EventsRes> getEvents(Pageable pageable, @ModelAttribute EventSearchParam eventSearchParam) {
		ImageRes imageRes = ImageRes.builder()
			.apiUrl("/images/")
			.filename("filename.png")
			.build();

		return new PageImpl<>(List.of(
			EventsRes.builder()
				.id(1L)
				.storeName("이벤트1")
				.inProgress(false)
				.address("서울 서초동")
				.artists(List.of(
					ArtistRes.builder()
						.id(2L)
						.groupId(1L)
						.name("태연")
						.image(imageRes)
						.artistType(
							ArtistTypeRes.builder()
								.id(1L)
								.type("아이돌")
								.build()
						)
						.build()
				))
				.categories(List.of(
					EventCategoryRes.builder()
						.id(1L)
						.category("생일카페")
						.build()
				))
				.image(
					imageRes
				)
				.like(true)
				.bookmark(true)
				.build(),
			EventsRes.builder()
				.id(2L)
				.storeName("이벤트2")
				.inProgress(true)
				.address("서울 한남동")
				.artists(List.of(
					ArtistRes.builder()
						.id(1L)
						.groupId(null)
						.name("소녀시대")
						.image(imageRes)
						.artistType(
							ArtistTypeRes.builder()
								.id(2L)
								.type("그룹")
								.build()
						)
						.build()
				))
				.categories(List.of(
					EventCategoryRes.builder()
						.id(2L)
						.category("전시회")
						.build()
				))
				.image(
					ImageRes.builder()
						.apiUrl("/images/")
						.filename("event_image2.jpg")
						.build()
				)
				.like(true)
				.bookmark(false)
				.build()
		));
	}

	@Operation(summary = "나의 이벤트 목록 조회")
	@GetMapping("/myevent")
	public Page<EventsRes> getMyEvents(Pageable pageable) {
		ImageRes imageRes = ImageRes.builder()
			.apiUrl("/images/")
			.filename("event_image.jpg")
			.build();

		return new PageImpl<>(List.of(
			EventsRes.builder()
				.id(1L)
				.storeName("이벤트1")
				.inProgress(false)
				.address("서울 서초동")
				.artists(List.of(
					ArtistRes.builder()
						.id(2L)
						.groupId(1L)
						.name("태연")
						.image(imageRes)
						.artistType(
							ArtistTypeRes.builder()
								.id(1L)
								.type("아이돌")
								.build()
						)
						.build()
				))
				.categories(List.of(
					EventCategoryRes.builder()
						.id(1L)
						.category("생일카페")
						.build()
				))
				.image(
					imageRes
				)
				.like(false)
				.bookmark(true)
				.build(),
			EventsRes.builder()
				.id(2L)
				.storeName("이벤트2")
				.inProgress(true)
				.address("서울 한남동")
				.artists(List.of(
					ArtistRes.builder()
						.id(1L)
						.groupId(null)
						.name("소녀시대")
						.image(imageRes)
						.artistType(
							ArtistTypeRes.builder()
								.id(2L)
								.type("그룹")
								.build()
						)
						.build()
				))
				.categories(List.of(
					EventCategoryRes.builder()
						.id(2L)
						.category("전시회")
						.build()
				))
				.image(
					ImageRes.builder()
						.apiUrl("/images/")
						.filename("event_image2.jpg")
						.build()
				)
				.like(true)
				.bookmark(true)
				.build()
		));
	}

	@Operation(summary = "나의 좋아요 이벤트 목록 조회")
	@GetMapping("/mylike")
	public Page<EventsRes> getMyLikeEvents(Pageable pageable) {
		ImageRes imageRes = ImageRes.builder()
			.apiUrl("/images/")
			.filename("event_image.jpg")
			.build();

		return new PageImpl<>(List.of(
			EventsRes.builder()
				.id(1L)
				.storeName("이벤트1")
				.inProgress(false)
				.address("서울 서초동")
				.artists(List.of(
					ArtistRes.builder()
						.id(2L)
						.groupId(1L)
						.name("태연")
						.image(imageRes)
						.artistType(
							ArtistTypeRes.builder()
								.id(1L)
								.type("아이돌")
								.build()
						)
						.build()
				))
				.categories(List.of(
					EventCategoryRes.builder()
						.id(1L)
						.category("생일카페")
						.build()
				))
				.image(
					imageRes
				)
				.like(true)
				.bookmark(false)
				.build(),
			EventsRes.builder()
				.id(2L)
				.storeName("이벤트2")
				.inProgress(true)
				.address("서울 한남동")
				.artists(List.of(
					ArtistRes.builder()
						.id(1L)
						.groupId(null)
						.name("소녀시대")
						.image(imageRes)
						.artistType(
							ArtistTypeRes.builder()
								.id(2L)
								.type("그룹")
								.build()
						)
						.build()
				))
				.categories(List.of(
					EventCategoryRes.builder()
						.id(2L)
						.category("전시회")
						.build()
				))
				.image(
					ImageRes.builder()
						.apiUrl("/images/")
						.filename("event_image2.jpg")
						.build()
				)
				.like(true)
				.bookmark(true)
				.build()
		));
	}

	@Operation(summary = "오늘 진행중인 이벤트 해시태그 목록 조회")
	@GetMapping("/hashtags/today")
	public List<HashtagRes> getTodayHashtags() {
		return List.of(
			HashtagRes.builder()
				.eventId(1L)
				.hashtag("#뫄뫄 #생일_축하해")
				.build(),
			HashtagRes.builder()
				.eventId(2L)
				.hashtag("#소녀시대 #10주년")
				.build(),
			HashtagRes.builder()
				.eventId(3L)
				.hashtag("#뫄뫄_탄신 #벌써10000일")
				.build(),
			HashtagRes.builder()
				.eventId(4L)
				.hashtag("#드라마 #대박나자")
				.build()
		);
	}
}
