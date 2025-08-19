package com.majestic.xml.exceptions;

public class CycleArgumentException extends Exception
{
	public CycleArgumentException(String message)
	{
		super(message);
	}
}