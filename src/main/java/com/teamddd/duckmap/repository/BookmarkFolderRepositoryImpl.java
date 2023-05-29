package com.teamddd.duckmap.repository;

import static com.teamddd.duckmap.entity.QEvent.*;
import static com.teamddd.duckmap.entity.QEventBookmark.*;
import static com.teamddd.duckmap.entity.QEventBookmarkFolder.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamddd.duckmap.dto.event.bookmark.BookmarkFolderEventDto;
import com.teamddd.duckmap.dto.event.bookmark.QBookmarkFolderEventDto;
import com.teamddd.duckmap.entity.EventBookmarkFolder;

public class BookmarkFolderRepositoryImpl implements BookmarkFolderRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public BookmarkFolderRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<BookmarkFolderEventDto> findBookmarkedEvents(Long bookmarkFolderId, Pageable pageable) {
		List<BookmarkFolderEventDto> events = queryFactory.select(
				new QBookmarkFolderEventDto(
					event,
					eventBookmark
				))
			.from(event)
			.join(eventBookmark).on(event.eq(eventBookmark.event))
			.where(eventBookmark.eventBookmarkFolder.id.eq(bookmarkFolderId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory.select(event.count())
			.from(event)
			.join(eventBookmark).on(event.eq(eventBookmark.event))
			.where(eventBookmark.eventBookmarkFolder.id.eq(bookmarkFolderId));

		return PageableExecutionUtils.getPage(events, pageable, countQuery::fetchOne);
	}

	@Override
	public Page<EventBookmarkFolder> findBookmarkFoldersByUserId(Long userId, Pageable pageable) {
		List<EventBookmarkFolder> bookmarkFolders = queryFactory
			.select(eventBookmarkFolder)
			.from(eventBookmarkFolder)
			.where(eventBookmarkFolder.user.id.eq(userId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory.select(eventBookmarkFolder.count())
			.from(eventBookmarkFolder)
			.where(eventBookmarkFolder.user.id.eq(userId));

		return PageableExecutionUtils.getPage(bookmarkFolders, pageable, countQuery::fetchOne);

	}

}