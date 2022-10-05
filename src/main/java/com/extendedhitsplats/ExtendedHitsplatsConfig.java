package com.extendedhitsplats;

import net.runelite.client.config.*;

@ConfigGroup("extendedhitsplats")
public interface ExtendedHitsplatsConfig extends Config
{
	@ConfigItem(
			position = 1,
			keyName = "maxHitsplats",
			name = "Max Hitsplats",
			description = "Select the maximum number of hitsplats to display on screen."
	)

	@Range(min = 4, max = 255)
	default int maxHitsplats()
	{
		return 255;
	}

}
