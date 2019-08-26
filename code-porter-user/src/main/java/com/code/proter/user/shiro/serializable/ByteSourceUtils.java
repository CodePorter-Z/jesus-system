package com.code.proter.user.shiro.serializable;

import org.apache.shiro.util.ByteSource;

public class ByteSourceUtils{

	public static ByteSource bytes(byte[] bytes){
		return new SimpleByteSource(bytes);
	}

	public static ByteSource bytes(String arg){
		return new SimpleByteSource(arg);
	}
}
