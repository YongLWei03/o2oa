package com.x.okr.assemble.control.jaxrs.okrtask.exception;

import com.x.base.core.exception.PromptException;

public class PersonQueryByFlagException extends PromptException {

	private static final long serialVersionUID = 1859164370743532895L;

	public PersonQueryByFlagException( Throwable e, String flag ) {
		super("根据用户唯一标识查询用户信息时发生异常!Flag:" + flag );
	}
}
