package com.majestic.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.majestic.L2ClientDat;
import com.majestic.clientcryptor.DatFile;
import com.majestic.clientcryptor.crypt.DatCrypter;
import com.majestic.config.ConfigDebug;
import com.majestic.data.GameDataName;
import com.majestic.xml.Descriptor;
import com.majestic.xml.DescriptorParser;
import com.majestic.xml.DescriptorWriter;

public class SaveDat extends ActionTask
{
	private static final Logger LOGGER = Logger.getLogger(SaveDat.class.getName());
	
	private final File _file;
	private final String _chronicle;
	
	public SaveDat(L2ClientDat l2ClientDat, File file, String chronicle)
	{
		super(l2ClientDat);
		_file = file;
		_chronicle = chronicle;
	}
	
	@Override
	protected void action()
	{
		final long startTime = System.currentTimeMillis();
		byte[] buff = null;
		DatCrypter crypter = null;
		double progress = getCurrentProgress();
		boolean shouldContinue = true;
		
		if (_file.getName().endsWith(".dat") || _file.getName().endsWith(".txt"))
		{
			try
			{
				final Descriptor desc = DescriptorParser.getInstance().findDescriptorForFile(_chronicle, _file.getName().replace(".txt", ".dat"));
				if (desc != null)
				{
					crypter = _l2clientdat.getEncryptor(_file);
					if (crypter == null)
					{
						shouldContinue = false;
					}
					else
					{
						buff = DescriptorWriter.parseData(this, 90.0, _file, crypter, desc, _l2clientdat.getTextPaneMain().getText().replace("\n", "\r\n"), false);
						if (isCancelled())
						{
							shouldContinue = false;
						}
						progress = addProgress(progress, 90.0, 100.0);
						GameDataName.getInstance().checkAndUpdate(_file.getParent(), crypter);
						if (isCancelled())
						{
							shouldContinue = false;
						}
						progress = addProgress(progress, 5.0, 100.0);
					}
				}
				else
				{
					L2ClientDat.addLogConsole("Not found the structure of the file: " + _file.getName(), true);
				}
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
				shouldContinue = false;
			}
		}
		else if (_file.getName().endsWith(".ini"))
		{
			crypter = _l2clientdat.getEncryptor(_file);
			if ((crypter == null) || isCancelled())
			{
				shouldContinue = false;
			}
			else
			{
				buff = _l2clientdat.getTextPaneMain().getText().replace("\n", "\r\n").getBytes();
			}
		}
		else
		{
			L2ClientDat.addLogConsole("Unknown file " + _file.getName() + " type!", true);
			shouldContinue = false;
		}
		
		if (shouldContinue && (buff != null))
		{
			if (isCancelled())
			{
				return;
			}
			
			try
			{
				if (ConfigDebug.ENCRYPT)
				{
					DatFile.encrypt(buff, _file.getPath(), crypter);
				}
				else
				{
					final FileOutputStream os = new FileOutputStream(_file.getPath(), false);
					os.write(buff);
					os.close();
				}
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
				return;
			}
			
			if (isCancelled())
			{
				return;
			}
			
			addProgress(progress, 5.0, 100.0);
			final long diffTime = (System.currentTimeMillis() - startTime) / 1000L;
			L2ClientDat.addLogConsole("Packed successfully by " + (crypter == null ? "crypter" : crypter.getName()) + " encrypter. Elapsed ".concat(String.valueOf(diffTime)).concat(" sec"), true);
			return;
		}
		
		L2ClientDat.addLogConsole("buff == null.", true);
	}
}
