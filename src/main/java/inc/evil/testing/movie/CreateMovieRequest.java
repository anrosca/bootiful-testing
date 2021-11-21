package inc.evil.testing.movie;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateMovieRequest {

	@NotNull
	@Size(min = 1)
	private String name;

	public CreateMovieRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Movie toEntity() {
		return new Movie(name);
	}

	@Override
	public String toString() {
		return "CreateMovieRequest{" +
				"name='" + name + '\'' +
				'}';
	}
}
