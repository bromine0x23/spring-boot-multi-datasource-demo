package cn.bromine0x23.demo.annotations;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * Qualifier for secondary DataSource.
 * <p>
 * Be used in defining secondary DataSource and related beans, and constraining injected bean.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface Secondary {
}
