package com.tjsj.fwk.mvc.shiro.annotation;



import java.lang.annotation.*;

/**
 * 获取Shiro当前用户  
 * @see SessionUserArgumentResolver
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionUser {
}
