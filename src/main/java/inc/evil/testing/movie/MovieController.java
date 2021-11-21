package inc.evil.testing.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@Validated
public class MovieController {
	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping
	public List<MovieDto> findAll() {
		return movieService.findAll();
	}

	@GetMapping("{id}")
	public MovieDto findById(@PathVariable String id) {
		return movieService.findById(id);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid CreateMovieRequest request) {
		MovieDto createdMovie = movieService.create(request);
		URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
						.findById(createdMovie.getId()))
				.build()
				.toUri();
		return ResponseEntity.created(location)
				.build();
	}
}
