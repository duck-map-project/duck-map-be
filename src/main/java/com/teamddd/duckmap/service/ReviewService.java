package com.teamddd.duckmap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamddd.duckmap.dto.review.CreateReviewReq;
import com.teamddd.duckmap.dto.review.ReviewRes;
import com.teamddd.duckmap.entity.Event;
import com.teamddd.duckmap.entity.Member;
import com.teamddd.duckmap.entity.Review;
import com.teamddd.duckmap.entity.ReviewImage;
import com.teamddd.duckmap.exception.NonExistentReviewException;
import com.teamddd.duckmap.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

	private final EventService eventService;
	private final ReviewRepository reviewRepository;

	@Transactional
	public Long createReview(CreateReviewReq createReviewReq, Member member) {
		Event event = eventService.getEvent(createReviewReq.getEventId());

		Review review = Review.builder()
			.member(member)
			.event(event)
			.content(createReviewReq.getContent())
			.score(createReviewReq.getScore())
			.build();

		List<String> imageFilenames = createReviewReq.getImageFilenames();
		for (String imageFilename : imageFilenames) {
			review.getReviewImages().add(
				ReviewImage.builder()
					.review(review)
					.image(imageFilename)
					.build()
			);
		}

		reviewRepository.save(review);

		return review.getId();
	}

	//Review 단건 조회
	public Review getReview(Long reviewId) throws NonExistentReviewException {
		return reviewRepository.findById(reviewId)
			.orElseThrow(NonExistentReviewException::new);
	}

	public ReviewRes getReviewRes(Long reviewId) throws NonExistentReviewException {
		return reviewRepository.findById(reviewId)
			.map(ReviewRes::of)
			.orElseThrow(NonExistentReviewException::new);
	}

}
