package com.extendedhitsplats;

import net.runelite.client.config.*;

@ConfigGroup("extendedhitsplats")
public interface ExtendedHitsplatsConfig extends Config
{
	@ConfigItem(
			position = 1,
			keyName = "maxHitsplats",
			name = "Max Hitsplats",
			description = "Select the maximum number of hitsplats to display per actor."
	)
	@Range(min = 4, max = 255)
	default int maxHitsplats()
	{
		return 255;
	}

	@ConfigItem(
			position = 2,
			keyName = "singleHitsplat",
			name = "Hitsplat Type",
			description = "The type of hitsplat you would like to see in-game."
	)
	default HitsplatCategoryEnum hitsplatCategoryEnum(){return HitsplatCategoryEnum.None;}

	@ConfigItem(
			position = 3,
			keyName = "removeZeros",
			name = "Remove Zeros",
			description = "Hide hitsplats with zeros or misses."
	)
	default boolean removeZeros()
	{
		return false;
	}

	@ConfigItem(
			position = 4,
			keyName = "hitSplat2010",
			name = "2010 Hitsplat",
			description = "Multiply your hitsplat by 10, just like in 2010!"
	)
	default boolean hitsplat2010(){return false;}

}
