package com.majestic.xml;

public enum ParamNodeType
{
	FOR,
	WRAPPER,
	CONSTANT,
	VARIABLE,
	IF,
	ELSE,
	MASK;
	
	boolean isCycle()
	{
		return this == ParamNodeType.FOR;
	}
	
	public boolean isWrapper()
	{
		return this == ParamNodeType.WRAPPER;
	}
	
	boolean isConstant()
	{
		return this == ParamNodeType.CONSTANT;
	}
	
	boolean isVariable()
	{
		return this == ParamNodeType.VARIABLE;
	}
	
	boolean isIf()
	{
		return this == ParamNodeType.IF;
	}
	
	boolean isElse()
	{
		return this == ParamNodeType.ELSE;
	}
	
	boolean isMask()
	{
		return this == ParamNodeType.MASK;
	}
}