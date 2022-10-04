package com.extendedhitsplats.overlays;
/*
 * Copyright (c) 2018, Morgan Lewis <https://github.com/MESLewis>
 * Copyright (c) 2022, Ferrariic <ferrariictweet@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.extendedhitsplats.ExtendedHitsplatsConfig;
import com.extendedhitsplats.ExtendedHitsplatsPlugin;
import com.extendedhitsplats.utils.Icons;
import net.runelite.api.Client;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.HitsplatID;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ExtendedHitsplatsOverlay extends Overlay
{
    private final Client client;
    private final ExtendedHitsplatsPlugin plugin;
    private final ExtendedHitsplatsConfig config;

    @Inject
    private ExtendedHitsplatsOverlay(Client client, ExtendedHitsplatsPlugin plugin, ExtendedHitsplatsConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.NONE);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        List<HitsplatApplied> hitsplatAppliedList = ExtendedHitsplatsPlugin.appliedHitsplatList;
        if (hitsplatAppliedList.size() == 0){
            return null;
        }
        for (HitsplatApplied hitsplatApplied : hitsplatAppliedList){
            BufferedImage hitsplatImage = drawHitsplat(hitsplatApplied.getHitsplat().getHitsplatType(), hitsplatApplied.getHitsplat().getHitsplatType());
            OverlayUtil.renderActorOverlayImage(graphics, hitsplatApplied.getActor(), hitsplatImage, null, hitsplatApplied.getActor().getLogicalHeight()*2);
        }

        return null;
    }

    private BufferedImage drawHitsplat(int hitsplat_type, int damage){
        ImageIcon hitIcon = null;
        switch (hitsplat_type){
            case HitsplatID.POISON:
                hitIcon = Icons.POISON_HITSPLAT;
                break;
            case HitsplatID.BLOCK_ME:
                hitIcon = Icons.SELF_MISS_HITSPLAT;
                break;
            case HitsplatID.BLOCK_OTHER:
                hitIcon = Icons.OTHER_MISS_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME:
                hitIcon = Icons.MAX_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME:
                hitIcon = Icons.SELF_DAMAGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME_CYAN:
                hitIcon = Icons.MAX_SHIELD_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME_ORANGE:
                hitIcon = Icons.MAX_ARMOUR_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME_WHITE:
                hitIcon = Icons.MAX_UNCHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME_YELLOW:
                hitIcon = Icons.MAX_CHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME_CYAN:
                hitIcon = Icons.SELF_SHIELD_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME_ORANGE:
                hitIcon = Icons.SELF_ARMOUR_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME_WHITE:
            case HitsplatID.DAMAGE_OTHER_WHITE:
                hitIcon = Icons.SELF_UNCHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME_YELLOW:
                hitIcon = Icons.SELF_CHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_OTHER:
                hitIcon = Icons.OTHER_DAMAGE_HITSPLAT;
                break;
            case HitsplatID.DISEASE:
                hitIcon = Icons.DISEASE_HITSPLAT;
                break;
            case HitsplatID.HEAL:
                hitIcon = Icons.HEAL_HITSPLAT;
                break;
            case HitsplatID.VENOM:
                hitIcon = Icons.VENOM_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_OTHER_CYAN:
                hitIcon = Icons.OTHER_SHIELD_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_OTHER_ORANGE:
                hitIcon = Icons.OTHER_ARMOUR_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_OTHER_YELLOW:
                hitIcon = Icons.OTHER_CHARGE_HITSPLAT;
                break;
            case 0:
                hitIcon = Icons.CORRUPTION_HITSPLAT;
                break;
            default:
                hitIcon = Icons.OTHER_POISE_HITSPLAT;
        }
        BufferedImage bi = iconToBuffered(hitIcon);
        Graphics g = bi.getGraphics();
        bi = drawCenteredString(g, String.valueOf(damage), bi, FontManager.getRunescapeSmallFont());
        g.dispose();
        return bi;
    }

    public BufferedImage drawCenteredString(Graphics g, String text, BufferedImage bi, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (bi.getWidth() - metrics.stringWidth(text)) / 2;
        int y = ((bi.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        // draw shadow
        g.setColor(Color.black);
        g.drawString(text, x+1, y+1);
        // draw normal text
        g.setColor(Color.white);
        g.drawString(text, x, y);
        return bi;
    }

    private BufferedImage iconToBuffered(ImageIcon icon){
        // resize
        Image image = icon.getImage();
        int height = icon.getIconHeight();
        int width = icon.getIconWidth();
        Image tempImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        ImageIcon sizedImageIcon = new ImageIcon(tempImage);

        // write to buffered
        BufferedImage bi = new BufferedImage(
                sizedImageIcon.getIconWidth(),
                sizedImageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        sizedImageIcon.paintIcon(null, g, 0,0);
        g.dispose();
        return bi;
    }
}