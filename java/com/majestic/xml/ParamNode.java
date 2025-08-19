package com.majestic.xml;

import java.util.ArrayList;
import java.util.List;

public class ParamNode
{
	private final ParamNodeType _entityType;
	private final ParamType _type;
	private String _name;
	private int _size;
	private boolean _hidden;
	private String _cycleName;
	private ArrayList<ParamNode> _sub;
	private boolean _isIterator;
	private boolean _skipWriteSize;
	private String _enumName;
	private String _paramIf;
	private String _valIf;
	private String _paramMask;
	private int _valMask;
	
	ParamNode(String name, ParamNodeType entityType, ParamType type)
	{
		_size = -1;
		_hidden = false;
		_name = name;
		_entityType = entityType;
		_type = type;
	}
	
	public int getSize()
	{
		return _size;
	}
	
	public void setSize(int size)
	{
		_size = size;
	}
	
	void setIterator()
	{
		_isIterator = true;
	}
	
	void setHidden()
	{
		_hidden = true;
	}
	
	boolean isIterator()
	{
		return _isIterator && (_size < 0);
	}
	
	boolean isNameHidden()
	{
		return _hidden;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	ParamNodeType getEntityType()
	{
		return _entityType;
	}
	
	public ParamType getType()
	{
		return _type;
	}
	
	ParamNode copy()
	{
		final ParamNode node = new ParamNode(getName(), getEntityType(), getType());
		if (isNameHidden())
		{
			node.setHidden();
		}
		
		if (isIterator())
		{
			node.setIterator();
		}
		
		node.setSkipWriteSize(isSkipWriteSize());
		node.setCycleName(getCycleName());
		if (getSubNodes() != null)
		{
			final List<ParamNode> list = new ArrayList<>();
			for (ParamNode n : getSubNodes())
			{
				final ParamNode copyN = n.copy();
				list.add(copyN);
			}
			node.addSubNodes(list);
		}
		
		return node;
	}
	
	void addSubNodes(List<ParamNode> n)
	{
		if (_sub == null)
		{
			_sub = new ArrayList<>();
		}
		_sub.addAll(n);
	}
	
	List<ParamNode> getSubNodes()
	{
		return _sub;
	}
	
	@Override
	public String toString()
	{
		return _name + "[" + _entityType + "][" + _cycleName + "][" + _type + "]";
	}
	
	boolean isSkipWriteSize()
	{
		return _skipWriteSize;
	}
	
	void setSkipWriteSize(boolean skipWrite)
	{
		_skipWriteSize = skipWrite;
	}
	
	String getParamIf()
	{
		return _paramIf;
	}
	
	void setParamIf(String paramIf)
	{
		_paramIf = paramIf;
	}
	
	String getValIf()
	{
		return _valIf;
	}
	
	void setValIf(String valIf)
	{
		_valIf = valIf;
	}
	
	String getCycleName()
	{
		return _cycleName;
	}
	
	void setCycleName(String cycleName)
	{
		_cycleName = cycleName;
	}
	
	public boolean isEnum()
	{
		return _enumName != null;
	}
	
	public void setEnumName(String enumName)
	{
		_enumName = enumName;
	}
	
	public String getEnumName()
	{
		return _enumName;
	}
	
	public String getParamMask()
	{
		return _paramMask;
	}
	
	public void setParamMask(String paramMask)
	{
		_paramMask = paramMask;
	}
	
	public int getValMask()
	{
		return _valMask;
	}
	
	public void setValMask(int valMask)
	{
		_valMask = valMask;
	}
}