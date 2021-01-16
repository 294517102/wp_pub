/**
 * 
 */
package com.tjsj.fwk.mvc.interceptors;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.wp.mvc.controller.system.MyErrorController;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;


/**
 * @author andrew-silence
 *
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {
	 protected Logger logger =  LoggerFactory.getLogger(FreemarkerExceptionHandler.class);
    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
    	logger.warn("[Freemarker Error: " + te.getMessage() + "]");
	        String missingVariable = "undefined";
	        try {
	            String[] tmp = te.getMessageWithoutStackTop().split("\n");
	            if (tmp.length > 1)
	                tmp = tmp[1].split(" ");
	            if (tmp.length > 1)
	                missingVariable = tmp[1];
	            out.write("");
	            logger.error("[出错了，请联系网站管理员]", te);

	        } catch (IOException e) {
	            throw new TemplateException(
	                    "Failed to print error message. Cause: " + e, env);
	        }
 
    }

}
