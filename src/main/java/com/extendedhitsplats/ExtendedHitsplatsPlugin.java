/*
 * Copyright (c) 2022, Ferrariic <ferrariictweet@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.extendedhitsplats;

import com.extendedhitsplats.overlays.ExtendedHitsplatsOverlay;
import com.extendedhitsplats.utils.HitsplatManager;
import com.extendedhitsplats.utils.ManagedHitsplat;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.Hitsplat;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.game.SpriteOverride;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@PluginDescriptor(
	name = "Extended Hitsplats",description = "This plugin will allow you to see more than four hitsplats on a character",enabledByDefault = true, tags = {"extended", "hitsplat"}
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
	@Inject
	private SpriteManager spriteManager;
	private final SpriteOverride[] overrides = new SpriteOverride[Sprites.ALL_SPRITES.length];
	private int debug = 0;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
		if (overrides[0] == null) {
			for (int i = 0; i < Sprites.ALL_SPRITES.length; i++) {
				int id = Sprites.ALL_SPRITES[i];
				overrides[i] = new SpriteOverride() {
					@Override
					public int getSpriteId() {
						return id;
					}

					@Override
					public String getFileName() {
						return "/com/extendedhitsplats/hitsplats/osrs/blank.png";
					}
				};
			}
		}
		spriteManager.addSpriteOverrides(overrides);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		spriteManager.removeSpriteOverrides(overrides);
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied hitsplatApplied){
		HitsplatManager.add(hitsplatApplied);
	}

	@Subscribe
	public void onGameTick(GameTick gameTick){
		int clientGameCycle = client.getGameCycle();
		if (HitsplatManager.hitsplatList.isEmpty()){
			return;
		}

		List<Actor> toRemoveActors = new ArrayList<>();
		List<ManagedHitsplat> toRemoveSplats = new ArrayList<>();
		for (Map.Entry<Actor, CopyOnWriteArrayList<ManagedHitsplat>> entry: HitsplatManager.hitsplatList.entrySet()) {
			Actor actor = entry.getKey();
			for (ManagedHitsplat managedHitsplat : entry.getValue()) {
				int disappear = managedHitsplat.hitsplat.getDisappearsOnGameCycle();
				if (clientGameCycle > disappear) {
					toRemoveSplats.add(managedHitsplat);
					HitsplatManager.releasePosition(actor, managedHitsplat.position);
				}
			}
			if (HitsplatManager.hitsplatList.get(actor).isEmpty()) {
				toRemoveActors.add(actor);
			}
			HitsplatManager.hitsplatList.get(actor).removeAll(toRemoveSplats);
		}

		for (Actor actor : toRemoveActors) {
			HitsplatManager.hitsplatList.remove(actor);
			HitsplatManager.takenPositions.remove(actor);
		}
	}

	@Provides
	ExtendedHitsplatsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExtendedHitsplatsConfig.class);
	}
}
