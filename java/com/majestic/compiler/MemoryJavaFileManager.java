package com.majestic.compiler;

import java.net.URI;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

public class MemoryJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager>
{
	private final MemoryClassLoader _classLoader;
	
	public MemoryJavaFileManager(StandardJavaFileManager sjfm, MemoryClassLoader xcl)
	{
		super(sjfm);
		_classLoader = xcl;
	}
	
	@Override
	public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling)
	{
		final MemoryByteCode mbc = new MemoryByteCode(className.replace('/', '.').replace('\\', '.'), URI.create("file:///" + className.replace('.', '/').replace('\\', '/') + kind.extension));
		_classLoader.addClass(mbc);
		return mbc;
	}
	
	@Override
	public ClassLoader getClassLoader(JavaFileManager.Location location)
	{
		return _classLoader;
	}
}
