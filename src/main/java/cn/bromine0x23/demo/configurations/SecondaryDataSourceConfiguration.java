package cn.bromine0x23.demo.configurations;

import cn.bromine0x23.demo.annotations.Secondary;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * Configuration for secondary DataSource and related beans.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Configuration
@MapperScan(basePackages = "cn.bromine0x23.demo.mappers.secondary", sqlSessionTemplateRef = "secondarySqlSessionTemplate")
public class SecondaryDataSourceConfiguration extends AbstractDataSourceConfiguration {

	private static final String PREFIX = "demo.datasource.secondary";

	@Bean
	@Secondary
	@ConfigurationProperties(PREFIX)
	public DataSourceProperties secondaryDataSourceProperties(
	) {
		return new DataSourceProperties();
	}

	@Bean
	@Secondary
	@ConfigurationProperties(PREFIX + ".hikari")
	public HikariDataSource secondaryDataSource(
		@Secondary DataSourceProperties properties
	) {
		HikariDataSource dataSource = createDataSource(properties, HikariDataSource.class);
		if (StringUtils.hasText(properties.getName())) {
			dataSource.setPoolName(properties.getName());
		}
		return dataSource;
	}

	@Bean
	@Secondary
	@ConfigurationProperties(PREFIX + ".jdbc")
	public JdbcProperties secondaryJdbcProperties(
	) {
		return new JdbcProperties();
	}

	@Bean
	@Secondary
	public JdbcTemplate secondaryJdbcTemplate(
		@Secondary DataSource dataSource,
		@Secondary JdbcProperties jdbcProperties
	) {
		return createJdbcTemplate(dataSource, jdbcProperties);
	}

	@Bean
	@Secondary
	public NamedParameterJdbcTemplate secondaryNamedParameterJdbcTemplate(
		@Secondary JdbcTemplate jdbcTemplate
	) {
		return createNamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Bean
	@Secondary
	@ConfigurationProperties(PREFIX + ".mybatis")
	public org.apache.ibatis.session.Configuration secondaryMybatisConfiguration(
	) {
		return new org.apache.ibatis.session.Configuration();
	}

	@Bean
	@Secondary
	public SqlSessionFactory secondarySqlSessionFactory(
		@Secondary DataSource dataSource,
		@Secondary org.apache.ibatis.session.Configuration configuration
	) throws Exception {
		return createSqlSessionFactory(dataSource, configuration);
	}

	@Bean
	@Secondary
	public SqlSessionTemplate secondarySqlSessionTemplate(
		@Secondary SqlSessionFactory sqlSessionFactory
	) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
