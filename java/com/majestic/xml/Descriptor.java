package com.majestic.xml;

import java.util.List;

import com.majestic.listeners.FormatListener;

public class Descriptor
{
	private final String _alias;
	private final String _filePattern;
	private final List<ParamNode> _nodes;
	private boolean _isRawData;
	private boolean _isSafePackage;
	private FormatListener _format;
	
	Descriptor(String alias, String filePattern, List<ParamNode> nodes)
	{
		_alias = alias;
		_filePattern = filePattern;
		_nodes = nodes;
		_isRawData = false;
	}
	
	void setIsRawData(boolean value)
	{
		_isRawData = value;
	}
	
	public void setIsSafePackage(boolean value)
	{
		_isSafePackage = value;
	}
	
	boolean isRawData()
	{
		return _isRawData;
	}
	
	public boolean isSafePackage()
	{
		return _isSafePackage;
	}
	
	public String getAlias()
	{
		return _alias;
	}
	
	public String getFilePattern()
	{
		return _filePattern;
	}
	
	List<ParamNode> getNodes()
	{
		return _nodes;
	}
	
	public FormatListener getFormat()
	{
		return _format;
	}
	
	public void setFormat(FormatListener format)
	{
		_format = format;
	}
}
