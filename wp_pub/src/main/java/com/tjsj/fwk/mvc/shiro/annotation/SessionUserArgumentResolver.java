package com.tjsj.fwk.mvc.shiro.annotation;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.tjsj.base.constant.Const;
import com.tjsj.wp.orm.entity.SmUserTbl;

/**
 * {@link SessionUser}注解的解析
 */
public class SessionUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if(supportsParameter(parameter) && subject.isAuthenticated()){
        	SmUserTbl user = (SmUserTbl) subject.getSession().getAttribute(Const.SESSION_USER);
            return user;
        }
        return null;
    }
    
}
