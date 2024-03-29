package com.teamddd.duckmap.dto.artist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class UpdateArtistReq {
	private Long groupId;
	@NotBlank
	private String name;
	@NotBlank
	private String image;
	@NotNull
	private Long artistTypeId;
}
