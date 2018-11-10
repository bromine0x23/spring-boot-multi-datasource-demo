package cn.bromine0x23.demo.services;

import cn.bromine0x23.demo.mappers.primary.PrimaryMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Demo service using primary DataSource.
 * <p>
 * For using primary DataSource or related beans, no additional changes need to be done.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Service
public class PrimaryService {

	private final JdbcTemplate jdbcTemplate;

	private final PrimaryMapper primaryMapper;

	public PrimaryService(
		JdbcTemplate jdbcTemplate,
		PrimaryMapper primaryMapper
	) {
		this.jdbcTemplate = jdbcTemplate;
		this.primaryMapper = primaryMapper;
	}

	public String version() {
		StringBuilder builder = new StringBuilder();
		builder.append(jdbcTemplate.queryForObject("SELECT H2VERSION() FROM DUAL", String.class));
		builder.append('\n');
		builder.append(primaryMapper.version());
		return builder.toString();
	}
}
