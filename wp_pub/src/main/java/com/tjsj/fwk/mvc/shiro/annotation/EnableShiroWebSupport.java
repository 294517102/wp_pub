package com.tjsj.fwk.mvc.shiro.annotation;


import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.tjsj.wp.mvc.config.WebMvcConfigurer;

import java.lang.annotation.*;

/**
 * Annotation to automatically register the following beans for usage with Spring MVC.
 * <ul>
 * <li>
 * {@link com.millinch.spring.boot.autoconfigure.shiro.annotation.SessionUser}.
 * </li>
 * </ul>
 * @author John Zhang  
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
@Import({ EnableShiroWebSupport.ShiroWebMvcConfigurerAdapterImportSelector.class })
public @interface EnableShiroWebSupport {

    static class ShiroWebMvcConfigurerAdapterImportSelector implements ImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[] { WebMvcConfigurer.class.getName() };
        }

    }
}
