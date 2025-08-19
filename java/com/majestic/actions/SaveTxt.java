package com.majestic.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.majestic.L2ClientDat;
import com.majestic.config.ConfigWindow;

public class SaveTxt extends ActionTask
{
	private static final Logger LOGGER = Logger.getLogger(SaveTxt.class.getName());
	
	private final File _file;
	
	public SaveTxt(L2ClientDat l2ClientDat, File file)
	{
		super(l2ClientDat);
		_file = file;
	}
	
	@Override
	protected void action()
	{
		try
		{
			if (isCancelled())
			{
				return;
			}
			
			changeProgress(15.0);
			final PrintWriter out = new PrintWriter(new FileOutputStream(_file.getPath()), true);
			changeProgress(30.0);
			ConfigWindow.save("FILE_SAVE_CURRENT_DIRECTORY", _file.getParentFile().toString());
			changeProgress(50.0);
			out.print(_l2clientdat.getTextPaneMain().getText());
			changeProgress(90.0);
			out.close();
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, null, e);
		}
		
		L2ClientDat.addLogConsole("---------------------------------------", true);
		L2ClientDat.addLogConsole("Saved: " + _file.getPath(), true);
	}
}
