package com.majestic.clientcryptor.crypt;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class XorDatCrypter extends DatCrypter
{
	private final boolean _encrypt;
	private final int _xorKey;
	private ByteArrayOutputStream _result;
	
	public XorDatCrypter(String name, int code, int key, boolean deCrypt)
	{
		super(name, code);
		_encrypt = !deCrypt;
		_xorKey = key;
	}
	
	@Override
	public ByteBuffer decryptResult()
	{
		return ByteBuffer.wrap(_result.toByteArray());
	}
	
	@Override
	public ByteBuffer encryptResult()
	{
		return ByteBuffer.wrap(_result.toByteArray());
	}
	
	@Override
	public boolean update(byte[] bArray)
	{
		for (byte b : bArray)
		{
			_result.write(b ^ _xorKey);
		}
		return true;
	}
	
	@Override
	public int getChunkSize(int available)
	{
		return 1;
	}
	
	@Override
	public int getSkipSize()
	{
		return 0;
	}
	
	@Override
	public boolean isEncrypt()
	{
		return _encrypt;
	}
	
	@Override
	public void aquire()
	{
		super.aquire();
		_result = new ByteArrayOutputStream(128);
	}
}
