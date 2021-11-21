package inc.evil.testing.movie;

import inc.evil.testing.common.AbstractRestTest;
import inc.evil.testing.movie.controller.MovieController;
import inc.evil.testing.movie.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;

@WebMvcTest(controllers = MovieController.class)
public class MovieControllerTest extends AbstractRestTest {
	@MockBean
	private MovieService movieService;

	@Test
	public void whenNameFieldIsNull_shouldReturnEmptyResponse() throws Exception {
		String payload = """
				{
				  "foo": "bar"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movies").content(payload)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/movies")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]",
						equalTo("Field 'name' must not be null but value was 'null'")));
	}
}
