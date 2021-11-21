package inc.evil.testing.movie;

import inc.evil.testing.common.AbstractIntegrationTest;
import inc.evil.testing.common.ComponentTest;
import inc.evil.testing.movie.model.MovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
public class MovieControllerComponentTest extends AbstractIntegrationTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldBeAbleToFindMovieById() {
		MovieDto expectedMovie = new MovieDto("84dcbcaf-a5e5-8183-b3c9-cf6b20612988", "Joker");
		String movieId = "84dcbcaf-a5e5-8183-b3c9-cf6b20612988";

		ResponseEntity<MovieDto> response =
				restTemplate.getForEntity("http://localhost:{port}/api/v1/movies/{movieId}",
						MovieDto.class, port, movieId);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(expectedMovie);
	}

	@Test
	@Sql("/data-jpa-test-data/movie-repository/movies.sql")
	public void shouldBeAbleToFindAllMovies() {
		MovieDto[] expectedMovies = {
				new MovieDto("84dcbcaf-a5e5-8183-b3c9-cf6b20612988", "Kill Bill Vol.1"),
				new MovieDto("74dcbcaf-a5e5-4183-b3c9-cf6b2061298f", "Pulp Fiction")
		};

		ResponseEntity<MovieDto[]> response =
				restTemplate.getForEntity("http://localhost:{port}/api/v1/movies",
						MovieDto[].class, port);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(expectedMovies);
	}
}
