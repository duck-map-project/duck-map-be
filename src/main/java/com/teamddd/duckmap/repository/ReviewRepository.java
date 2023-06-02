package com.teamddd.duckmap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.teamddd.duckmap.entity.Event;
import com.teamddd.duckmap.entity.Member;
import com.teamddd.duckmap.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
	Page<Review> findByMember(Member member, Pageable pageable);

	Page<Review> findByEvent(Event event, Pageable pageable);
}
