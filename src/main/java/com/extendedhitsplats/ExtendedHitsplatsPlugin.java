/*
 * Copyright (c) 2018-2022, Lotto, Adam, alexanderhenne, Nightfirecat <https://github.com/devLotto> [shouldDraw & EntityHider]
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
import com.extendedhitsplats.overlays.ExtendedHitsplatsRedrawUIOverlay;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.callback.Hooks;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.NpcUtil;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.List;
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
	private ExtendedHitsplatsRedrawUIOverlay redrawUIOverlay;
	@Inject
	private Hooks hooks;
	@Inject
	private NpcUtil npcUtil;
	public static List<HitsplatApplied> appliedHitsplatList = new CopyOnWriteArrayList<>();
	private final Hooks.RenderableDrawListener drawListener = this::shouldDraw;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(redrawUIOverlay);
		overlayManager.add(overlay);
		hooks.registerRenderableDrawListener(drawListener);
		log.info("Extended Hitsplats started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(redrawUIOverlay);
		overlayManager.remove(overlay);
		hooks.unregisterRenderableDrawListener(drawListener);
		log.info("Extended Hitsplats stopped!");
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied hitsplatApplied){
		appliedHitsplatList.add(hitsplatApplied);
	}


	@Subscribe
	public void onGameTick(GameTick gameTick){
		int clientGameCycle = client.getGameCycle();
		if (appliedHitsplatList == null){
			return;
		}
		if (appliedHitsplatList.size() == 0){
			return;
		}
		for (HitsplatApplied hitsplatApplied : appliedHitsplatList){
			int disappear = hitsplatApplied.getHitsplat().getDisappearsOnGameCycle();
			if (clientGameCycle > disappear + 50){ // delays by 2 ticks to prevent hitsplats from being cleared, atm getDisappearsOnGameCycle is the creation time of the Hitsplat
				appliedHitsplatList.remove(hitsplatApplied);
			}
		}
	}

	//  * Copyright (c) 2018-2022, Adam, alexanderhenne, Nightfirecat for shouldDraw
	boolean shouldDraw(Renderable renderable, boolean drawingUI)
	{
		// hardcoded values from 2022, Ferrariic <ferrariictweet@gmail.com>
		boolean hideOthers = false;
		boolean hideOthers2D = true;

		boolean hideFriends = false;
		boolean hideFriendsChatMembers = false;
		boolean hideClanMembers  = false;
		boolean hideIgnoredPlayers = false;

		boolean hideLocalPlayer = false;
		boolean hideLocalPlayer2D = true;

		boolean hideNPCs = false;
		boolean hideNPCs2D = true;
		boolean hideDeadNpcs = false;

		boolean hidePets = false;
		boolean hideAttackers = false;
		boolean hideProjectiles = false;

		if (renderable instanceof Player)
		{
			Player player = (Player) renderable;
			Player local = client.getLocalPlayer();

			if (player.getName() == null)
			{
				// player.isFriend() and player.isFriendsChatMember() npe when the player has a null name
				return true;
			}

			// Allow hiding local self in pvp, which is an established meta.
			// It is more advantageous than renderself due to being able to still render local player 2d
			if (player == local)
			{
				return !(drawingUI ? hideLocalPlayer2D : hideLocalPlayer);
			}

			if (hideAttackers && player.getInteracting() == local)
			{
				return false; // hide
			}

			if (player.isFriend())
			{
				return !hideFriends;
			}
			if (player.isFriendsChatMember())
			{
				return !hideFriendsChatMembers;
			}
			if (player.isClanMember())
			{
				return !hideClanMembers;
			}
			if (client.getIgnoreContainer().findByName(player.getName()) != null)
			{
				return !hideIgnoredPlayers;
			}

			return !(drawingUI ? hideOthers2D : hideOthers);
		}
		else if (renderable instanceof NPC)
		{
			NPC npc = (NPC) renderable;

			if (npc.getComposition().isFollower() && npc != client.getFollower())
			{
				return !hidePets;
			}

			// dead npcs can also be interacting so prioritize it over the interacting check
			if (npcUtil.isDying(npc) && hideDeadNpcs)
			{
				return false;
			}

			if (npc.getInteracting() == client.getLocalPlayer())
			{
				boolean b = hideAttackers;
				// Kludge to make hide attackers only affect 2d or 3d if the 2d or 3d hide is on
				// This allows hiding 2d for all npcs, including attackers.
				if (hideNPCs2D || hideNPCs)
				{
					b &= drawingUI ? hideNPCs2D : hideNPCs;
				}
				return !b;
			}

			return !(drawingUI ? hideNPCs2D : hideNPCs);
		}
		else if (renderable instanceof Projectile)
		{
			return !hideProjectiles;
		}
		else if (renderable instanceof GraphicsObject)
		{
			if (!hideDeadNpcs)
			{
				return true;
			}

			switch (((GraphicsObject) renderable).getId())
			{
				case GraphicID.MELEE_NYLO_DEATH:
				case GraphicID.RANGE_NYLO_DEATH:
				case GraphicID.MAGE_NYLO_DEATH:
				case GraphicID.MELEE_NYLO_EXPLOSION:
				case GraphicID.RANGE_NYLO_EXPLOSION:
				case GraphicID.MAGE_NYLO_EXPLOSION:
					return false;
				default:
					return true;
			}
		}
		return true;
	}

	@Provides
	ExtendedHitsplatsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExtendedHitsplatsConfig.class);
	}
}
