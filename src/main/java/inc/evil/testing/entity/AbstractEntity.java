package inc.evil.testing.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity {
	@Id
	@GenericGenerator(name = "uuid-generator", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid-generator")
	protected String id;

	public String getId() {
		return id;
	}

	public boolean equals(Object other) {
		if (!(other instanceof AbstractEntity otherEntity))
			return false;
		return Objects.equals(getId(), otherEntity.getId());
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return "AbstractEntity{" +
				"id='" + id + '\'' +
				'}';
	}
}
