package com.teamddd.duckmap.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamddd.duckmap.dto.event.category.CreateEventCategoryReq;
import com.teamddd.duckmap.entity.EventCategory;
import com.teamddd.duckmap.repository.EventCategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventCategoryService {
	private final EventCategoryRepository eventCategoryRepository;

	@Transactional
	public Long createEventCategory(CreateEventCategoryReq createEventCategoryReq) {
		EventCategory eventCategory = EventCategory.builder()
			.category(createEventCategoryReq.getCategory())
			.build();

		eventCategoryRepository.save(eventCategory);

		return eventCategory.getId();
	}
}
