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
import com.extendedhitsplats.HitsplatCategoryEnum;
import com.extendedhitsplats.points.SplatPoints;
import com.extendedhitsplats.utils.HitsplatManager;
import com.extendedhitsplats.utils.Icons;
import com.extendedhitsplats.utils.ManagedHitsplat;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ExtendedHitsplatsOverlay extends Overlay
{
    private final ExtendedHitsplatsPlugin plugin;
    private final ExtendedHitsplatsConfig config;
    private final Client client;

    @Inject
    private ExtendedHitsplatsOverlay(ExtendedHitsplatsPlugin plugin, ExtendedHitsplatsConfig config, Client client)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.plugin = plugin;
        this.config = config;
        this.client = client;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (HitsplatManager.hitsplatList.isEmpty()){
            return null;
        }

        for (Map.Entry<Actor, CopyOnWriteArrayList<ManagedHitsplat>> entry : HitsplatManager.hitsplatList.entrySet()){
            for (ManagedHitsplat managedHitsplat : entry.getValue()) {
                HitsplatCategoryEnum hitsplatCategoryEnumConfig = config.hitsplatCategoryEnum();
                switch (hitsplatCategoryEnumConfig){
                    case Every_Hitsplat:
                        drawExtendedHitsplats(graphics, entry.getKey(), entry.getValue());
                        break;
                    case Single_Normal:
                    case Single_BIG:
                        drawSingleHitsplat(graphics, entry.getKey(), entry.getValue(), hitsplatCategoryEnumConfig);
                        break;
                }
            }
        }
        return null;
    }

    private void drawExtendedHitsplats(Graphics2D graphics, Actor actor, List<ManagedHitsplat> hitsplats){
        // normal hitsplat construction
        int missOffset = 0;
        for (ManagedHitsplat managedHitsplat : hitsplats) {
            Hitsplat hitsplat = managedHitsplat.hitsplat;
            int position = managedHitsplat.position;
            int damage = hitsplat.getAmount();
            int hitsplatType = hitsplat.getHitsplatType();

            if (position >= config.maxHitsplats()) {
                continue;
            }

            if ((damage == 0) & (config.removeZeros())) {
                missOffset += 1;
                continue;
            }

            if (config.hitsplat2010()){
                damage = damage * 10;
            }

            BufferedImage hitsplatImage = drawHitsplat(hitsplatType, damage, FontManager.getRunescapeSmallFont());
            Point cPoint = actor.getCanvasImageLocation(hitsplatImage, actor.getLogicalHeight()/2);

            if (cPoint == null){
                continue;
            }

            Point p = new Point(cPoint.getX()+1, cPoint.getY()-1);
            Point k = SplatPoints.splatPoints.get(position);
            OverlayUtil.renderImageLocation(graphics, new Point(p.getX()+k.getX(), p.getY()+k.getY()), hitsplatImage);
        }
    }

    private void drawSingleHitsplat(Graphics2D graphics, Actor actor, List<ManagedHitsplat> hitsplats, HitsplatCategoryEnum hitsplatCategoryEnum){
        // makes one big hitsplat with the cumulative amount of tick damage per actor
        int damage = 0;
        for (ManagedHitsplat managedHitsplat : hitsplats) {
            if (managedHitsplat.hitsplat.getHitsplatType() != HitsplatID.HEAL){
                damage += managedHitsplat.hitsplat.getAmount();
            }
        }

        if ((damage == 0) & (config.removeZeros())){
            return;
        }

        if (config.hitsplat2010()){
            damage = damage * 10;
        }

        int hitsplatType = HitsplatID.DAMAGE_MAX_ME;;
        Font font = FontManager.getRunescapeSmallFont();
        switch (hitsplatCategoryEnum){
            case Single_Normal:
                hitsplatType = HitsplatID.DAMAGE_MAX_ME;
                font = FontManager.getRunescapeSmallFont();
                break;
            case Single_BIG:
                hitsplatType = -1;
                font = FontManager.getRunescapeBoldFont();
                break;
        }

        BufferedImage hitsplatImage = drawHitsplat(hitsplatType, damage, font);
        Point cPoint = actor.getCanvasImageLocation(hitsplatImage, actor.getLogicalHeight()/2);

        if (cPoint == null){
            return;
        }

        Point p = new Point(cPoint.getX()+1, cPoint.getY()-1);
        OverlayUtil.renderImageLocation(graphics, new Point(p.getX(), p.getY()), hitsplatImage);
    }

    private BufferedImage drawHitsplat(int hitsplat_type, int damage, Font font){
        ImageIcon hitIcon;
        switch (hitsplat_type){
            case HitsplatID.BLEED:
                hitIcon = Icons.OSRS_BLEED_HITSPLAT;
                break;
            case HitsplatID.BURN:
                hitIcon = Icons.OSRS_BURN_HITSPLAT;
                break;
            case HitsplatID.BLOCK_ME:
                hitIcon = Icons.OSRS_SELF_MISS_HITSPLAT;
                break;
            case HitsplatID.BLOCK_OTHER:
                hitIcon = Icons.OSRS_OTHER_MISS_HITSPLAT;
                break;
            case HitsplatID.CORRUPTION:
                hitIcon = Icons.OSRS_CORRUPTION_HITSPLAT;
                break;
            case HitsplatID.CYAN_DOWN:
                hitIcon = Icons.OSRS_ALT_UNCHARGE_HITSPLAT;
                break;
            case HitsplatID.CYAN_UP:
                hitIcon = Icons.OSRS_ALT_CHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME:
                hitIcon = Icons.OSRS_MAX_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME_CYAN:
                hitIcon = Icons.OSRS_MAX_SHIELD_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME_ORANGE:
                hitIcon = Icons.OSRS_MAX_ARMOUR_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME_POISE:
                hitIcon = Icons.OSRS_MAX_POISE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME_WHITE:
                hitIcon = Icons.OSRS_MAX_UNCHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_MAX_ME_YELLOW:
                hitIcon = Icons.OSRS_MAX_CHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME:
                hitIcon = Icons.OSRS_SELF_DAMAGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME_CYAN:
                hitIcon = Icons.OSRS_SELF_SHIELD_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME_ORANGE:
                hitIcon = Icons.OSRS_SELF_ARMOUR_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME_POISE:
                hitIcon = Icons.OSRS_SELF_POISE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME_WHITE:
                hitIcon = Icons.OSRS_SELF_UNCHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_ME_YELLOW:
                hitIcon = Icons.OSRS_SELF_CHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_OTHER:
                hitIcon = Icons.OSRS_OTHER_DAMAGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_OTHER_CYAN:
                hitIcon = Icons.OSRS_OTHER_SHIELD_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_OTHER_ORANGE:
                hitIcon = Icons.OSRS_OTHER_ARMOUR_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_OTHER_POISE:
                hitIcon = Icons.OSRS_OTHER_POISE_HITSPLAT;
                break;
            // Does not exist, defaulting to self for future support
            case HitsplatID.DAMAGE_OTHER_WHITE:
                hitIcon = Icons.OSRS_SELF_UNCHARGE_HITSPLAT;
                break;
            case HitsplatID.DAMAGE_OTHER_YELLOW:
                hitIcon = Icons.OSRS_OTHER_CHARGE_HITSPLAT;
                break;
            case HitsplatID.DISEASE:
                hitIcon = Icons.OSRS_DISEASE_HITSPLAT;
                break;
            case HitsplatID.DOOM:
                hitIcon = Icons.OSRS_DOOM_HITSPLAT;
                break;
            case HitsplatID.HEAL:
                hitIcon = Icons.OSRS_HEAL_HITSPLAT;
                break;
            case HitsplatID.POISON:
                hitIcon = Icons.OSRS_POISON_HITSPLAT;
                break;
            case HitsplatID.PRAYER_DRAIN:
                hitIcon = Icons.OSRS_PRAYER_DRAIN_HITSPLAT;
                break;
            case HitsplatID.SANITY_DRAIN:
                hitIcon = Icons.OSRS_SANITY_DRAIN_HITSPLAT;
                break;
            case HitsplatID.SANITY_RESTORE:
                hitIcon = Icons.OSRS_SANITY_RESTORE_HITSPLAT;
                break;
            case HitsplatID.VENOM:
                hitIcon = Icons.OSRS_VENOM_HITSPLAT;
                break;
            case -1:
                hitIcon = Icons.OSRS_BIG_HITSPLAT;
                break;
            default:
                return new BufferedImage(0,0,0);
        }
        BufferedImage bi = iconToBuffered(hitIcon);
        Graphics g = bi.getGraphics();
        bi = drawCenteredDamageNumbers(g, String.valueOf(damage), bi, font);
        g.dispose();
        return bi;
    }

    public BufferedImage drawCenteredDamageNumbers(Graphics g, String text, BufferedImage bi, Font font) {
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
        Image image = icon.getImage();
        int height = icon.getIconHeight();
        int width = icon.getIconWidth();
        Image tempImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon sizedImageIcon = new ImageIcon(tempImage);

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