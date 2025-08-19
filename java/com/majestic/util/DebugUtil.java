package com.majestic.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.majestic.config.ConfigDebug;
import com.majestic.xml.Variant;

public class DebugUtil
{
	private static final Logger LOGGER = Logger.getLogger(DebugUtil.class.getName());
	
	public static void debug(String message)
	{
		if (ConfigDebug.DAT_DEBUG_MSG)
		{
			DebugUtil.LOGGER.info(message);
		}
	}
	
	public static void debugPos(int pos, String name, Variant val)
	{
		if (ConfigDebug.DAT_DEBUG_POS)
		{
			DebugUtil.LOGGER.info("pos: " + pos + " " + name + ": " + val);
			if ((ConfigDebug.DAT_DEBUG_POS_LIMIT != 0) && (pos > ConfigDebug.DAT_DEBUG_POS_LIMIT))
			{
				System.exit(0);
			}
		}
	}
	
	public static void save(ByteBuffer buffer, File path)
	{
		if (ConfigDebug.SAVE_DECODE)
		{
			try
			{
				final String unpackDirPath = path.getParent() + "/!decrypted";
				final File decryptedDir = new File(unpackDirPath);
				decryptedDir.mkdir();
				final File file = new File(decryptedDir + "/" + path.getName());
				final FileOutputStream fos = new FileOutputStream(file);
				fos.write(buffer.array());
				fos.close();
			}
			catch (IOException e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}
}
