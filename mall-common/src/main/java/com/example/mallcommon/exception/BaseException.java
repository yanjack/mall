package com.example.mallcommon.exception;

import java.util.Map;



/**
 * 检查型异常基类
 *
 * @author Bob
 *
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 异常配置
	 */
	private ExceptionConfig config;

	public BaseException(String code) {
		this(code, null, (Map<String, String>) null);
	}

//	public BaseException(Throwable throwable) {
//		this(ErrorCodeEnum.RET_SYSTEMEXCEPTION.getCode(), throwable, (Map<String, String>) null);
//	}

	public BaseException(String code, String desc) {
		config = new ExceptionConfig(code, desc);
	}

	public BaseException(String code, Throwable t) {
		this(code, t, (Map<String, String>) null);
	}

	public BaseException(String code, Map<String, String> args) {
		this(code, null, args);
	}

	public BaseException(String code, Throwable t, Map<String, String> args) {
		config = new ExceptionConfig(code, t, args);
	}

	@Override
	public String getMessage() {
		return this.getDesc();
	}

	@Override
	public Throwable getCause() {
		return config.getThrowable();
	}

	/**
	 * 获取编码
	 * 
	 * @return
	 */
	public String getCode() {
		return config.getCode();
	}

	/**
	 * 获取描述信息
	 * 
	 * @return
	 */
	public String getDesc() {
		return config.getDesc();
	}

	/**
	 * 获取堆栈信息
	 * 
	 * @return
	 */
	public String getStackMessage() {
		return config.getStackMessage();
	}

}
