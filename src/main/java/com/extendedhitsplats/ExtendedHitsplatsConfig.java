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

	@ConfigItem(
			position = 2,
			keyName = "removeZeros",
			name = "Remove Zeros",
			description = "Hide hitsplats with zeros or misses."
	)
	default boolean removeZeros()
	{
		return false;
	}

	@ConfigItem(
			position = 3,
			keyName = "bigHitsplat",
			name = "Single Big Hitsplat",
			description = "Show one big hitsplat with all of the damage values for a target."
	)
	default boolean bigHitsplat(){return false;}

	@ConfigItem(
			position = 4,
			keyName = "hitSplat2010",
			name = "2010 Hitsplat",
			description = "Multiply your hitsplat by 10, just like in 2010!"
	)
	default boolean hitsplat2010(){return false;}


	@ConfigItem(
			position = 4,
			keyName = "oneShotBonanza",
			name = "Oops all Ones",
			description = "Turns every hit into 1, but it sums to the correct amount."
	)
	default boolean oneShotBonanza(){return false;}

}
