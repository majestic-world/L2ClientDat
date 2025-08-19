package com.majestic.compiler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

public class MemoryByteCode extends SimpleJavaFileObject
{
	private ByteArrayOutputStream _oStream;
	private final String _className;
	
	public MemoryByteCode(String className, URI uri)
	{
		super(uri, JavaFileObject.Kind.CLASS);
		_className = className;
	}
	
	@Override
	public OutputStream openOutputStream()
	{
		return _oStream = new ByteArrayOutputStream();
	}
	
	public byte[] getBytes()
	{
		return _oStream.toByteArray();
	}
	
	@Override
	public String getName()
	{
		return _className;
	}
}
