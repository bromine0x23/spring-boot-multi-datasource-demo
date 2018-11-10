package cn.bromine0x23.demo.services;

import cn.bromine0x23.demo.annotations.Secondary;
import cn.bromine0x23.demo.mappers.secondary.SecondaryMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Demo service using primary DataSource.
 * <p>
 * For using MyBatis mappers, no additional changes need to be done.
 * <p>
 * For others, a @{@link Secondary} annotation need to used in injecting.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Service
public class SecondaryService {

	private final JdbcTemplate jdbcTemplate;

	private final SecondaryMapper secondaryMapper;

	public SecondaryService(
		@Secondary JdbcTemplate jdbcTemplate,
		SecondaryMapper secondaryMapper
	) {
		this.jdbcTemplate = jdbcTemplate;
		this.secondaryMapper = secondaryMapper;
	}

	public String version() {
		StringBuilder builder = new StringBuilder();
		builder.append(jdbcTemplate.queryForObject("select sqlite_version()", String.class));
		builder.append('\n');
		builder.append(secondaryMapper.version());
		return builder.toString();
	}
}
