package com.majestic.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import com.majestic.util.StringUtil;
import com.majestic.util.Util;

public class ConsoleLogFormatter extends Formatter
{
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM HH:mm:ss");
	
	@Override
	public String format(LogRecord record)
	{
		final StringBuilder output = new StringBuilder(128);
		StringUtil.append(output, "[", dateFmt.format(new Date(record.getMillis())), "] " + record.getMessage(), System.lineSeparator());
		
		if (record.getThrown() != null)
		{
			try
			{
				StringUtil.append(output, Util.getStackTrace(record.getThrown()), System.lineSeparator());
			}
			catch (Exception ex)
			{
				// Ignore.
			}
		}
		return output.toString();
	}
}