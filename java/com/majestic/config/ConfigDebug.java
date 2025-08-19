package com.majestic.config;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigDebug
{
	private static final Logger LOGGER = Logger.getLogger(ConfigDebug.class.getName());
	
	public static boolean DAT_ADD_END_BYTES;
	public static boolean DAT_DEBUG_MSG;
	public static boolean DAT_DEBUG_POS;
	public static boolean DAT_DEBUG_POS_BUFFER;
	public static int DAT_DEBUG_POS_LIMIT;
	public static boolean DAT_REPLACEMENT_NAMES;
	public static boolean DAT_REPLACEMENT_ENUMS;
	public static boolean ENCRYPT;
	public static boolean SAVE_DECODE;
	
	public static void load()
	{
		try
		{
			final PropertiesParser parser = new PropertiesParser("./config/config_debug.ini");
			DAT_ADD_END_BYTES = parser.getBoolean("DAT_ADD_END_BYTES", false);
			DAT_DEBUG_MSG = parser.getBoolean("DAT_DEBUG_MSG", false);
			DAT_DEBUG_POS = parser.getBoolean("DAT_DEBUG_POS", false);
			DAT_DEBUG_POS_LIMIT = parser.getInt("DAT_DEBUG_POS_LIMIT", 100000);
			DAT_REPLACEMENT_NAMES = parser.getBoolean("DAT_REPLACEMENT_NAMES", true);
			DAT_REPLACEMENT_ENUMS = parser.getBoolean("DAT_REPLACEMENT_ENUMS", true);
			ENCRYPT = parser.getBoolean("ENCRYPT", true);
			SAVE_DECODE = parser.getBoolean("SAVE_DECODE", false);
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, "Failed to load configuration file.", e);
		}
	}
}
