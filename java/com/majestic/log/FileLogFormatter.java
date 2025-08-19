package com.majestic.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import com.majestic.util.StringUtil;

public class FileLogFormatter extends Formatter
{
	private static final String TAB = "\t";
	
	private final SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss,SSS");
	
	@Override
	public String format(LogRecord record)
	{
		// Java 1.8
		// return StringUtil.concat(_dateFormat.format(new Date(record.getMillis())), TAB, record.getLevel().getName(), TAB, String.valueOf(record.getThreadID()), TAB, record.getLoggerName(), TAB, record.getMessage(), System.lineSeparator());
		// Java 16
		return StringUtil.concat(_dateFormat.format(new Date(record.getMillis())), TAB, record.getLevel().getName(), TAB, String.valueOf(record.getLongThreadID()), TAB, record.getLoggerName(), TAB, record.getMessage(), System.lineSeparator());
	}
}
