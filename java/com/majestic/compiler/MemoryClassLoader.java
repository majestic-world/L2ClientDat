package com.majestic.compiler;

import java.util.HashMap;
import java.util.Map;

public class MemoryClassLoader extends ClassLoader
{
	private final Map<String, MemoryByteCode> _classes = new HashMap<>();
	private final Map<String, MemoryByteCode> _loaded = new HashMap<>();
	
	public MemoryClassLoader()
	{
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		MemoryByteCode mbc = _classes.get(name);
		if (mbc == null)
		{
			mbc = _classes.get(name);
			if (mbc == null)
			{
				return super.findClass(name);
			}
		}
		return defineClass(name, mbc.getBytes(), 0, mbc.getBytes().length);
	}
	
	public void addClass(MemoryByteCode mbc)
	{
		_classes.put(mbc.getName(), mbc);
		_loaded.put(mbc.getName(), mbc);
	}
	
	public MemoryByteCode getClass(String name)
	{
		return _classes.get(name);
	}
	
	public String[] getLoadedClasses()
	{
		return _loaded.keySet().toArray(new String[_loaded.size()]);
	}
	
	public void clear()
	{
		_loaded.clear();
	}
}
