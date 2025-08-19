package com.majestic.xml;

public class Variant
{
	private final Object _value;
	private final Class<?> _type;
	
	Variant(Object value, Class<?> type)
	{
		_value = type.cast(value);
		_type = type;
	}
	
	public final boolean isInt()
	{
		return _type == Integer.class;
	}
	
	public final boolean isShort()
	{
		return _type == Short.class;
	}
	
	public final boolean isFloat()
	{
		return _type == Float.class;
	}
	
	public final boolean isDouble()
	{
		return _type == Double.class;
	}
	
	public final int getInt()
	{
		return (int) _value;
	}
	
	public final short getShort()
	{
		return (short) _value;
	}
	
	public final float getFloat()
	{
		return (float) _value;
	}
	
	public double getDouble()
	{
		return (double) _value;
	}
	
	public long getLong()
	{
		return (long) _value;
	}
	
	@Override
	public final String toString()
	{
		return String.valueOf(_value);
	}
}