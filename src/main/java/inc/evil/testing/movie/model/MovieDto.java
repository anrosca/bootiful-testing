package inc.evil.testing.movie.model;

import java.util.Objects;

public class MovieDto {
	private String id;
	private String name;

	public MovieDto() {
	}

	public MovieDto(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static MovieDto from(Movie movie) {
		return new MovieDto(movie.getId(), movie.getName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MovieDto movieDto = (MovieDto) o;
		return id.equals(movieDto.id) && name.equals(movieDto.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "MovieDto{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
