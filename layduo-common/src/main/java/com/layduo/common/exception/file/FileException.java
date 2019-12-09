package com.layduo.common.exception.file;

import com.layduo.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author layduo
 */
public class FileException extends BaseException {
	private static final long serialVersionUID = 1L;

	public FileException(String code, Object[] args) {
		super("file", code, args, null);
	}

}
