package cn.bromine0x23.demo.mappers.secondary;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * A mapper using secondary DataSource.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Repository
public interface SecondaryMapper {

	@Select("select sqlite_version()")
	String version();
}
