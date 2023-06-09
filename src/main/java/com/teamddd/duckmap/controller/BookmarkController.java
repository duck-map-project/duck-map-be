package com.teamddd.duckmap.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamddd.duckmap.dto.event.bookmark.CreateBookmarkReq;
import com.teamddd.duckmap.dto.event.bookmark.CreateBookmarkRes;
import com.teamddd.duckmap.dto.event.bookmark.UpdateBookmarkReq;
import com.teamddd.duckmap.entity.Member;
import com.teamddd.duckmap.service.BookmarkService;
import com.teamddd.duckmap.util.MemberUtils;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class BookmarkController {
	private final BookmarkService bookmarkService;

	@Operation(summary = "북마크 생성")
	@PostMapping("/{eventId}/bookmarks")
	public CreateBookmarkRes createBookmark(@PathVariable Long eventId,
		@Validated @RequestBody CreateBookmarkReq createBookmarkReq) {
		Member member = MemberUtils.getAuthMember().getUser();
		Long bookmarkId = bookmarkService.createBookmark(eventId, createBookmarkReq.getBookmarkFolderId(), member);
		return CreateBookmarkRes.builder()
			.id(bookmarkId)
			.build();
	}

	@Operation(summary = "북마크 폴더 변경")
	@PutMapping("/{eventId}/bookmarks")
	public void updateBookmark(@PathVariable Long eventId,
		@Validated @RequestBody UpdateBookmarkReq updateBookmarkReq) {
		Member member = MemberUtils.getAuthMember().getUser();
		bookmarkService.updateBookmark(eventId, updateBookmarkReq, member);
	}

	@Operation(summary = "북마크 취소")
	@DeleteMapping("/{eventId}/bookmarks")
	public void deleteBookmark(@PathVariable Long eventId) {
		Member member = MemberUtils.getAuthMember().getUser();
		bookmarkService.deleteBookmark(eventId, member.getId());
	}

}
