package com.extendedhitsplats.utils;

import net.runelite.api.Actor;
import net.runelite.api.Hitsplat;

public class ManagedHitsplat {
    public Hitsplat hitsplat;
    public int position;

    public ManagedHitsplat(Hitsplat hitsplat, int position)
    {
        this.hitsplat = hitsplat;
        this.position = position;
    }
}
