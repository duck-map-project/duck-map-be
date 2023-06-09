package com.teamddd.duckmap.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int score;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private Event event;

	@Builder.Default
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewImage> reviewImages = new ArrayList<>();

	public void updateReview(int score, String content, List<String> reviewImages) {
		this.score = score;
		this.content = content;

		this.reviewImages.clear();

		for (String imageFilename : reviewImages) {
			this.reviewImages.add(
				ReviewImage.builder()
					.review(this)
					.image(imageFilename)
					.build()
			);
		}
	}
}
