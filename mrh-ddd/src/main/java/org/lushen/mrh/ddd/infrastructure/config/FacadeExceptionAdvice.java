package org.lushen.mrh.ddd.infrastructure.config;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lushen.mrh.ddd.infrastructure.basic.IBusinessException;
import org.lushen.mrh.ddd.infrastructure.basic.IErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 异常处理切面
 * 
 * @author hlm
 */
@ControllerAdvice
public class FacadeExceptionAdvice {

	@Autowired
	private ObjectMapper objectMapper;

	@ExceptionHandler(IBusinessException.class)
	public void handle(HttpServletRequest request, HttpServletResponse response, IBusinessException cause) throws Exception {

		IErrorMessage message = new IErrorMessage(500, cause.getMessage());
		String output = objectMapper.writeValueAsString(message);

		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(output);
		out.flush();
		out.close();

	}

	@ExceptionHandler(Throwable.class)
	public void handle(HttpServletRequest request, HttpServletResponse response, Throwable cause) throws Exception {

		IErrorMessage message = new IErrorMessage(500, "系统出错了，请稍后再试~");
		String output = objectMapper.writeValueAsString(message);

		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(output);
		out.flush();
		out.close();

	}

}
