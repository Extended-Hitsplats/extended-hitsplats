package com.extendedhitsplats;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ExtendedHitsplatsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ExtendedHitsplatsPlugin.class);
		RuneLite.main(args);
	}
}