package com.jx.example.log;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年5月31日 上午9:38:11
 *
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemLogAnnotation {
	String module() default "";

	String remark() default "";
}