package com.teamddd.duckmap.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExceptionCodeMessage {
	/* Auth */
	INVALID_TOKEN_EXCEPTION("A001", "유효하지 않은 토큰입니다"),
	INVALID_MEMBER_EXCEPTION("A002", "유효하지 않은 이메일 혹은 비밀번호입니다"),
	AUTHENTICATION_EXCEPTION("A003", "잘못된 인증입니다"),
	ACCESS_DENIED_EXCEPTION("A004", "권한이 없는 사용자입니다"),
	INVALID_PASSWORD_EXCEPTION("A005", "비밀번호가 맞지 않습니다"),
	INVALID_UUID_EXCEPTION("A006", "유효하지 않은 UUID 입니다"),
	AUTHENTICATION_REQUIRED_EXCEPTION("A007", "인증이 필요합니다"),

	/* Member */
	DUPLICATE_EMAIL_EXCEPTION("M001", "이미 사용중인 이메일입니다"),
	DUPLICATE_USERNAME_EXCEPTION("M002", "이미 사용중인 닉네임입니다"),
	NON_EXISTENT_MEMBER_EXCEPTION("M003", "잘못된 사용자 정보입니다"),

	/* Event */
	NON_EXISTENT_EVENT_EXCEPTION("E001", "잘못된 이벤트 정보입니다"),

	/* Event Like */
	NON_EXISTENT_EVENT_LIKE_EXCEPTION("EL001", "잘못된 이벤트 좋아요 정보입니다"),

	/* Review */
	NON_EXISTENT_REVIEW_EXCEPTION("R001", "잘못된 리뷰 정보입니다"),

	/* BookmarkFolder */
	NON_EXISTENT_BOOKMARK_FOLDER_EXCEPTION("BF001", "잘못된 북마크 폴더 정보입니다"),

	/* Bookmark*/
	NON_EXISTENT_BOOKMARK_EXCEPTION("B001", "잘못된 북마크 정보입니다"),

	/* Image File */
	NON_EXISTENT_FILE_EXCEPTION("F001", "존재하지 않는 파일입니다"),
	NOT_CONTENT_TYPE_IMAGE_EXCEPTION("F002", "이미지 파일만 저장 가능합니다"),

	/* ArtistType */
	NON_EXISTENT_ARTIST_TYPE_EXCEPTION("AT001", "잘못된 아티스트 구분 정보입니다"),
	UNABLE_TO_DELETE_ARTIST_TYPE_IN_USE_EXCEPTION("AT002", "해당 아티스트 구분을 사용중인 아티스트가 존재하여 삭제할 수 없습니다"),

	/* Artist */
	NON_EXISTENT_ARTIST_EXCEPTION("A001", "잘못된 아티스트 정보입니다"),

	/* EventCategory */
	NON_EXISTENT_EVENT_CATEGORY_EXCEPTION("EC001", "잘못된 이벤트 카테고리 정보입니다"),
	UNABLE_TO_DELETE_EVENT_CATEGORY_IN_USE_EXCEPTION("EC002", "해당 카테고리를 사용중인 이벤트가 존재하여 삭제할 수 없습니다");

	private final String code;
	private final String message;

	public String code() {
		return code;
	}

	public String message() {
		return message;
	}
}
