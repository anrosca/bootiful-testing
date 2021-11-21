package inc.evil.testing.movie;

import inc.evil.testing.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "movies")
public class Movie extends AbstractEntity {
	private String name;

	protected Movie() {
	}

	public Movie(String name) {
		this.name = name;
	}

	public Movie(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		Movie movie = (Movie) o;
		return Objects.equals(getName(), movie.getName()) && Objects.equals(getId(), movie.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), name);
	}

	@Override
	public String toString() {
		return "Movie{" +
				"name='" + name + '\'' +
				'}';
	}
}
