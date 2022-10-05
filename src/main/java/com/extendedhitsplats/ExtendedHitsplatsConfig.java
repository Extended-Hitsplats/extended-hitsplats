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
	@Range(min = 4, max = 543)
	default int maxHitsplats()
	{
		return 543;
	}

	@ConfigItem(
			position = 2,
			keyName = "showZero",
			name = "Show Miss/Zero",
			description = "Show misses or zero hitsplats on a character."
	)
	default boolean showZero(){return true;}


	@ConfigItem(
			position = 3,
			keyName = "bigHitsplat",
			name = "Single Big Hitsplat",
			description = "Show one big hitsplat with all of the damage values for a target."
	)
	default boolean bigHitsplat(){return false;}

}
