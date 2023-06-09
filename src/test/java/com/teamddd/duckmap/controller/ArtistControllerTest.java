package com.teamddd.duckmap.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamddd.duckmap.dto.artist.ArtistRes;
import com.teamddd.duckmap.dto.artist.CreateArtistReq;
import com.teamddd.duckmap.dto.artist.UpdateArtistReq;
import com.teamddd.duckmap.service.ArtistService;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private ArtistService artistService;

	@DisplayName("아티스트 구분과 이름으로 아티스트 목록을 조회한다")
	@Test
	void getArtists() throws Exception {
		//given
		PageRequest pageRequest = PageRequest.of(0, 5);
		PageImpl<ArtistRes> result = new PageImpl<>(List.of(), pageRequest, 0);

		when(artistService.getArtistResPageByTypeAndName(any(), any()))
			.thenReturn(result);

		//when //then
		mockMvc.perform(
				get("/artists")
					.param("artistTypeId", "1")
					.param("artistName", "name")
					.param("pageNumber", "0")
					.param("pageSize", "5")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.number").value(0))
			.andExpect(jsonPath("$.size").value(5))
			.andExpect(jsonPath("$.content").isArray());
	}

	@DisplayName("소속 아티스트의 목록을 조회한다")
	@Test
	void getArtistsByGroup() throws Exception {
		//given
		List<ArtistRes> result = List.of();
		when(artistService.getArtistsByGroup(any())).thenReturn(result);

		//when //then
		mockMvc.perform(
				get("/artists/1/artists"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray());
	}

	@DisplayName("아티스트를 등록한다")
	@Nested
	class CreateArtist {

		@DisplayName("관리자 계정은 아티스트를 등록할 수 있다")
		@Test
		@WithMockUser(roles = "ADMIN")
		void createArtist1() throws Exception {
			//given
			CreateArtistReq request = new CreateArtistReq();
			ReflectionTestUtils.setField(request, "name", "artist1");
			ReflectionTestUtils.setField(request, "image", "image");
			ReflectionTestUtils.setField(request, "artistTypeId", 1L);

			when(artistService.createArtist(any())).thenReturn(1L);

			//when //then
			mockMvc.perform(
					post("/artists")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1"));
		}

		@DisplayName("사용자 계정은 아티스트를 등록할 수 없다")
		@Test
		@WithMockUser(roles = "USER")
		void createArtist2() throws Exception {
			//given
			CreateArtistReq request = new CreateArtistReq();
			ReflectionTestUtils.setField(request, "name", "artist1");
			ReflectionTestUtils.setField(request, "image", "image");
			ReflectionTestUtils.setField(request, "artistTypeId", 1L);

			//when //then
			mockMvc.perform(
					post("/artists")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.code").value("A004"))
				.andExpect(jsonPath("$.message").value("권한이 없는 사용자입니다"));
		}
	}

	@DisplayName("아티스트정보를 변경한다")
	@Nested
	class UpdateArtist {

		@DisplayName("관리자 계정은 아티스트 정보를 변경할 수 있다")
		@Test
		@WithMockUser(roles = "ADMIN")
		void updateArtist1() throws Exception {
			//given
			UpdateArtistReq request = new UpdateArtistReq();
			ReflectionTestUtils.setField(request, "groupId", 1L);
			ReflectionTestUtils.setField(request, "name", "new_name");
			ReflectionTestUtils.setField(request, "image", "new_image");
			ReflectionTestUtils.setField(request, "artistTypeId", 1L);

			doNothing().when(artistService).updateArtist(any());

			//when //then
			mockMvc.perform(
					put("/artists/1")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isOk());
		}

		@DisplayName("사용자 계정은 아티스트 정보를 변경할 수 없다")
		@Test
		@WithMockUser(roles = "USER")
		void updateArtist2() throws Exception {
			//given
			UpdateArtistReq request = new UpdateArtistReq();
			ReflectionTestUtils.setField(request, "groupId", 1L);
			ReflectionTestUtils.setField(request, "name", "new_name");
			ReflectionTestUtils.setField(request, "image", "new_image");
			ReflectionTestUtils.setField(request, "artistTypeId", 1L);

			//when //then
			mockMvc.perform(
					put("/artists/1")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.code").value("A004"))
				.andExpect(jsonPath("$.message").value("권한이 없는 사용자입니다"));
		}
	}

	@DisplayName("아티스트를 삭제한다")
	@Nested
	class DeleteArtist {

		@DisplayName("관리자 계정은 아티스트를 삭제할 수 있다")
		@Test
		@WithMockUser(roles = "ADMIN")
		void deleteArtist1() throws Exception {
			//given
			doNothing().when(artistService).deleteArtist(any());

			//when //then
			mockMvc.perform(
					delete("/artists/1")
				)
				.andDo(print())
				.andExpect(status().isOk());
		}

		@DisplayName("사용자 계정은 아티스트를 삭제할 수 없다")
		@Test
		@WithMockUser(roles = "USER")
		void deleteArtist2() throws Exception {
			//given
			//when //then
			mockMvc.perform(
					delete("/artists/1")
				)
				.andDo(print())
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.code").value("A004"))
				.andExpect(jsonPath("$.message").value("권한이 없는 사용자입니다"));
		}
	}
}
