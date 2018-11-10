package cn.bromine0x23.demo.configurations;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * Configuration for primary DataSource and related beans.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Configuration
@MapperScan(basePackages = "cn.bromine0x23.demo.mappers.primary", sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDataSourceConfiguration extends AbstractDataSourceConfiguration {

	private static final String PREFIX = "demo.datasource.primary";

	@Bean
	@Primary
	@ConfigurationProperties(PREFIX)
	public DataSourceProperties primaryDataSourceProperties(
	) {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties(PREFIX + ".hikari")
	public HikariDataSource primaryDataSource(
		DataSourceProperties properties
	) {
		HikariDataSource dataSource = createDataSource(properties, HikariDataSource.class);
		if (StringUtils.hasText(properties.getName())) {
			dataSource.setPoolName(properties.getName());
		}
		return dataSource;
	}

	@Bean
	@Primary
	@ConfigurationProperties(PREFIX + ".jdbc")
	public JdbcProperties primaryJdbcProperties(
	) {
		return new JdbcProperties();
	}

	@Bean
	@Primary
	public JdbcTemplate primaryJdbcTemplate(
		DataSource dataSource,
		JdbcProperties jdbcProperties
	) {
		return createJdbcTemplate(dataSource, jdbcProperties);
	}

	@Bean
	@Primary
	public NamedParameterJdbcTemplate primaryNamedParameterJdbcTemplate(
		JdbcTemplate jdbcTemplate
	) {
		return createNamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Bean
	@Primary
	@ConfigurationProperties(PREFIX + ".mybatis")
	public org.apache.ibatis.session.Configuration primaryMybatisConfiguration(
	) {
		return new org.apache.ibatis.session.Configuration();
	}

	@Bean
	@Primary
	public SqlSessionFactory primarySqlSessionFactory(
		DataSource dataSource,
		org.apache.ibatis.session.Configuration configuration
	) throws Exception {
		return createSqlSessionFactory(dataSource, configuration);
	}

	@Bean
	@Primary
	public SqlSessionTemplate primarySqlSessionTemplate(
		SqlSessionFactory sqlSessionFactory
	) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
