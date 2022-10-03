package com.extendedhitsplats;

import com.extendedhitsplats.overlays.ExtendedHitsplatsOverlay;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Extended Hitsplats"
)
public class ExtendedHitsplatsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ExtendedHitsplatsConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ExtendedHitsplatsOverlay overlay;


	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
		log.info("Extended Hitsplats started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		log.info("Extended Hitsplats stopped!");
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied hitsplatApplied){
		hitsplatApplied.getActor();
	}

	@Provides
	ExtendedHitsplatsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExtendedHitsplatsConfig.class);
	}
}
