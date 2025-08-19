package com.majestic.clientcryptor.crypt;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class DatCrypter
{
	private final String _name;
	private final int _code;
	private boolean _useStructure;
	private final List<String> _fileEndNames = new ArrayList<>();
	private final ReentrantLock _lock = new ReentrantLock(true);
	
	public DatCrypter(String name, int code)
	{
		_name = name;
		_code = code;
	}
	
	public abstract boolean update(byte[] p0) throws Exception;
	
	public abstract ByteBuffer decryptResult();
	
	public abstract ByteBuffer encryptResult();
	
	public abstract int getChunkSize(int p0);
	
	public abstract int getSkipSize();
	
	public boolean checkAquired()
	{
		return _lock.isHeldByCurrentThread();
	}
	
	public void aquire()
	{
		_lock.lock();
	}
	
	public void release()
	{
		_lock.unlock();
	}
	
	public abstract boolean isEncrypt();
	
	public String getName()
	{
		return _name;
	}
	
	public int getCode()
	{
		return _code;
	}
	
	public void addFileExtension(String n)
	{
		_fileEndNames.addAll(Arrays.asList(n.split(";")));
	}
	
	public boolean checkFileExtension(String n)
	{
		return n.contains(".") && _fileEndNames.contains(n.split("\\.")[1]);
	}
	
	public boolean isUseStructure()
	{
		return _useStructure;
	}
	
	public void setUseStructure(boolean useStructure)
	{
		_useStructure = useStructure;
	}
}
