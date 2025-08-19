package com.majestic.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigWindow
{
	private static final Logger LOGGER = Logger.getLogger(ConfigWindow.class.getName());
	
	public static String FILE_OPEN_CURRENT_DIRECTORY_UNPACK;
	public static String FILE_OPEN_CURRENT_DIRECTORY_PACK;
	public static String FILE_OPEN_CURRENT_DIRECTORY;
	public static String FILE_SAVE_CURRENT_DIRECTORY;
	public static String CURRENT_CHRONICLE;
	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
	public static String CURRENT_ENCRYPT;
	public static String LAST_FILE_SELECTED;
	public static String CURRENT_FORMATTER;
	
	public static void load()
	{
		try
		{
			final PropertiesParser parser = new PropertiesParser("./config/config_window.ini");
			FILE_OPEN_CURRENT_DIRECTORY_UNPACK = parser.getString("FILE_OPEN_CURRENT_DIRECTORY_UNPACK", ".");
			FILE_OPEN_CURRENT_DIRECTORY_PACK = parser.getString("FILE_OPEN_CURRENT_DIRECTORY_PACK", ".");
			FILE_OPEN_CURRENT_DIRECTORY = parser.getString("FILE_OPEN_CURRENT_DIRECTORY", ".");
			FILE_SAVE_CURRENT_DIRECTORY = parser.getString("FILE_SAVE_CURRENT_DIRECTORY", ".");
			CURRENT_CHRONICLE = parser.getString("CURRENT_CHRONICLE", "");
			WINDOW_HEIGHT = parser.getInt("WINDOW_HEIGHT", 600);
			WINDOW_WIDTH = parser.getInt("WINDOW_WIDTH", 800);
			CURRENT_ENCRYPT = parser.getString("CURRENT_ENCRYPT", ".");
			LAST_FILE_SELECTED = parser.getString("LAST_FILE_SELECTED", ".");
			CURRENT_FORMATTER = parser.getString("CURRENT_FORMATTER", ".");
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, "Failed to load configuration file.", e);
		}
	}
	
	public static void save(String key, String variable)
	{
		try
		{
			final Properties props = new Properties();
			props.load(new FileInputStream("./config/config_window.ini"));
			props.setProperty(key, variable);
			final FileOutputStream output = new FileOutputStream("./config/config_window.ini");
			props.store(output, "Saved settings");
			output.close();
			load();
		}
		catch (Exception ex)
		{
		}
	}
}
