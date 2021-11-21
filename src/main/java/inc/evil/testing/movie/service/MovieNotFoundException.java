package inc.evil.testing.movie.service;

public class MovieNotFoundException extends RuntimeException {
	public MovieNotFoundException(String message) {
		super(message);
	}
}
