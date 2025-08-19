package com.majestic.xml;

public class DescriptorLink
{
	private final String _dir;
	private final String _namePattern;
	private final String _linkFile;
	private final String _linkVersion;
	
	public DescriptorLink(String dir, String namePattern, String linkFile, String linkVersion)
	{
		_dir = dir;
		_namePattern = namePattern;
		_linkFile = linkFile;
		_linkVersion = linkVersion;
	}
	
	public String getFilePattern()
	{
		return _dir;
	}
	
	public String getNamePattern()
	{
		return _namePattern;
	}
	
	public String getLinkFile()
	{
		return _linkFile;
	}
	
	public String getLinkVersion()
	{
		return _linkVersion;
	}
}