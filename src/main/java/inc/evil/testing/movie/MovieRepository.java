package inc.evil.testing.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {
	List<Movie> findByName(String name);

	@Query("select m from Movie m order by m.name")
	List<Movie> findAllOrderedByName();
}
