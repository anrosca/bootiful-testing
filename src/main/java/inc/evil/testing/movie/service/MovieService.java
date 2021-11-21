package inc.evil.testing.movie.service;

import inc.evil.testing.movie.dao.MovieRepository;
import inc.evil.testing.movie.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
	private final MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Transactional(readOnly = true)
	public List<MovieDto> findAll() {
		return movieRepository.findAllOrderedByName()
				.stream()
				.map(MovieDto::from)
				.toList();
	}

	@Transactional
	public MovieDto create(CreateMovieRequest request) {
		Movie createdMovie = movieRepository.save(request.toEntity());
		return MovieDto.from(createdMovie);
	}

	public MovieDto findById(String id) {
		return movieRepository.findById(id)
				.map(MovieDto::from)
				.orElseThrow(() -> new MovieNotFoundException("No movie with id = '" + id + "' was found"));
	}
}
