package com.majestic.listeners;

import com.majestic.actions.ActionTask;

public interface FormatListener
{
	String decode(ActionTask p0, double p1, String p2);
	
	String encode(ActionTask p0, double p1, String p2);
}