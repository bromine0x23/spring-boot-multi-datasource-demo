package cn.bromine0x23.demo.mappers.primary;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * A mapper using primary DataSource.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Repository
public interface PrimaryMapper {

	@Select("SELECT H2VERSION() FROM DUAL")
	String version();
}
