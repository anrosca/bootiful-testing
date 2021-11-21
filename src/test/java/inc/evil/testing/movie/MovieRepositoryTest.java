package inc.evil.testing.movie;

import inc.evil.testing.common.AbstractIntegrationTest;
import inc.evil.testing.movie.dao.MovieRepository;
import inc.evil.testing.movie.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private MovieRepository movieRepository;

	@Test
	public void shouldBeAbleToFindMoviesByName() {
		List<Movie> actualMovies = movieRepository.findByName("Pulp Fiction");

		List<Movie> expectedMovies = List.of(
				new Movie("74dcbcaf-a5e5-4183-b3c9-cf6b2061298f", "Pulp Fiction")
		);
		assertThat(actualMovies).isEqualTo(expectedMovies);
	}

	@Test
	@Sql("/data-jpa-test-data/movie-repository/movies.sql")
	public void shouldBeAbleToFindAllMovies() {
		List<Movie> actualMovies = movieRepository.findAllOrderedByName();

		List<Movie> expectedMovies = List.of(
				new Movie("84dcbcaf-a5e5-8183-b3c9-cf6b20612988", "Kill Bill Vol.1"),
				new Movie("74dcbcaf-a5e5-4183-b3c9-cf6b2061298f", "Pulp Fiction")
		);
		assertThat(actualMovies).isEqualTo(expectedMovies);
	}
}
