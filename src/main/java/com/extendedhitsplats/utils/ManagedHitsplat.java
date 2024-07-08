package com.extendedhitsplats.utils;

import net.runelite.api.Hitsplat;

public class ManagedHitsplat {
    public Hitsplat hitsplat;
    public int position;

    public ManagedHitsplat(Hitsplat hitsplatApplied, int position)
    {
        this.hitsplat = hitsplatApplied;
        this.position = position;
    }
}
