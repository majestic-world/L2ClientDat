package com.majestic.clientcryptor.crypt;

import java.nio.ByteBuffer;

public class BlowFishDatCrypter extends DatCrypter
{
	private boolean _encrypt;
	private final BlowfishEngine _blowfish;
	
	public BlowFishDatCrypter(String name, int code, String key, boolean deCrypt)
	{
		super(name, code);
		_encrypt = false;
		_blowfish = new BlowfishEngine();
		_encrypt = !deCrypt;
		_blowfish.init(_encrypt, key.getBytes());
	}
	
	@Override
	public ByteBuffer decryptResult()
	{
		return null;
	}
	
	@Override
	public ByteBuffer encryptResult()
	{
		return null;
	}
	
	@Override
	public boolean update(byte[] b)
	{
		return true;
	}
	
	@Override
	public int getChunkSize(int available)
	{
		return available;
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
}
