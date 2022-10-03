package com.extendedhitsplats;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("extendedhitsplats")
public interface ExtendedHitsplatsConfig extends Config
{
	@ConfigSection(
			position = 1,
			name = "Interactive Settings",
			description = "Select the interactive settings for the plugin."
	)
	String interactiveSection = "interactiveSettings";

	@ConfigItem(
			position = 1,
			keyName = "remove this",
			name = "Auth Token",
			description = "Your authentication token for the plugin. Length 32 characters - automatically generated if cleared and the plugin is restarted.",
			warning = "There are rare circumstances where you will need to change this field. If you are unsure about what you are doing, please click 'No'.",
			secret = true,
			hidden = true
	)
	default String authToken() {
		return "";
	}

}
