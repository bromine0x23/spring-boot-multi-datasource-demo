package cn.bromine0x23.demo.configurations;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * Define common methods for DataSource configuration.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 * @see org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration
 * @see org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
 */
abstract class AbstractDataSourceConfiguration {

	@Autowired
	private MybatisProperties mybatisProperties;

	@Autowired(required = false)
	private List<Interceptor> interceptors;

	@Autowired(required = false)
	private DatabaseIdProvider databaseIdProvider;

	@Autowired(required = false)
	private List<ConfigurationCustomizer> configurationCustomizers;

	@SuppressWarnings("unchecked")
	protected <T> T createDataSource(
		DataSourceProperties properties,
		Class<? extends DataSource> type
	) {
		return (T) properties.initializeDataSourceBuilder().type(type).build();
	}

	protected JdbcTemplate createJdbcTemplate(
		DataSource dataSource,
		JdbcProperties jdbcProperties
	) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		JdbcProperties.Template template = jdbcProperties.getTemplate();
		jdbcTemplate.setFetchSize(template.getFetchSize());
		jdbcTemplate.setMaxRows(template.getMaxRows());
		if (template.getQueryTimeout() != null) {
			jdbcTemplate.setQueryTimeout((int) template.getQueryTimeout().getSeconds());
		}
		return jdbcTemplate;
	}

	protected NamedParameterJdbcTemplate createNamedParameterJdbcTemplate(
		JdbcTemplate jdbcTemplate
	) {
		return new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	protected SqlSessionFactory createSqlSessionFactory(DataSource dataSource, Configuration configuration) throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setVfs(SpringBootVFS.class);

		if (configuration == null) {
			configuration = new Configuration();
		}
		if (!CollectionUtils.isEmpty(configurationCustomizers)) {
			for (ConfigurationCustomizer customizer : configurationCustomizers) {
				customizer.customize(configuration);
			}
		}
		factory.setConfiguration(configuration);

		if (!ObjectUtils.isEmpty(interceptors)) {
			factory.setPlugins(interceptors.toArray(new Interceptor[0]));
		}
		if (databaseIdProvider != null) {
			factory.setDatabaseIdProvider(databaseIdProvider);
		}
		if (StringUtils.hasLength(mybatisProperties.getTypeAliasesPackage())) {
			factory.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
		}
		if (StringUtils.hasLength(mybatisProperties.getTypeHandlersPackage())) {
			factory.setTypeHandlersPackage(mybatisProperties.getTypeHandlersPackage());
		}
		return factory.getObject();
	}
}
