package inc.evil.testing.common;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import javax.sql.DataSource;

@Slf4j
public class FlywayTestExecutionListener extends AbstractTestExecutionListener {

	@Override
	public void afterTestMethod(TestContext testContext) {
		Environment environment = testContext.getApplicationContext().getEnvironment();
		DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
		Flyway flyway = Flyway.configure()
				.locations(parseListProperty(environment, "spring.flyway.locations"))
				.schemas(parseListProperty(environment, "spring.flyway.schemas"))
				.dataSource(dataSource)
				.sqlMigrationPrefix(environment.getProperty("spring.flyway.sql-migration-prefix", "V"))
				.sqlMigrationSeparator(environment.getProperty("spring.flyway.sql-migration-separator", "__"))
				.sqlMigrationSuffixes(environment.getProperty("spring.flyway.sql-migration-suffixes", ".sql"))
				.load();
		flyway.clean();
		flyway.migrate();
		log.info("Successfully cleaned up the database");
	}

	private String[] parseListProperty(Environment environment, String propertyName) {
		String propertyValue = environment.getProperty(propertyName);
		if (propertyValue == null) {
			return new String[0];
		}
		return propertyValue.contains(",") ? propertyValue.split(",") : new String[] { propertyValue };
	}
}
