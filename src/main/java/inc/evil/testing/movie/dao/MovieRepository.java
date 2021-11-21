package inc.evil.testing.movie.dao;

import inc.evil.testing.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
	List<Movie> findByName(String name);

	@Query("select m from Movie m order by m.name")
	List<Movie> findAllOrderedByName();
}
