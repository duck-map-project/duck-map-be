package com.teamddd.duckmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teamddd.duckmap.entity.EventLike;

public interface EventLikeRepository extends JpaRepository<EventLike, Long> {
	Long countByEventId(Long eventId);

	@Modifying
	@Query("delete from EventLike el where el.event.id = :eventId")
	int deleteByEventId(@Param("eventId") Long eventId);
}
