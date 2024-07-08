package com.extendedhitsplats.utils;

import net.runelite.api.Actor;
import net.runelite.api.events.HitsplatApplied;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class HitsplatManager {
    public static ConcurrentHashMap<Actor, CopyOnWriteArrayList<ManagedHitsplat>> hitsplatList;

    public HitsplatManager() {
        hitsplatList = new ConcurrentHashMap<Actor, CopyOnWriteArrayList<ManagedHitsplat>>();
    }
    public void add(HitsplatApplied hitsplatApplied)
    {
        int position = -1;
        Actor actor = hitsplatApplied.getActor();
        if (!hitsplatList.containsKey(actor)) {
            position = 0;
            hitsplatList.put(actor, new CopyOnWriteArrayList<ManagedHitsplat>());
        } else {
            CopyOnWriteArrayList<ManagedHitsplat> hitsplats = hitsplatList.get(actor);
            position = hitsplats.get(hitsplats.size() - 1).position + 1;
        }
        ManagedHitsplat managedHitsplat = new ManagedHitsplat(hitsplatApplied.getHitsplat(), position);
        hitsplatList.get(actor).add(managedHitsplat);
    }
}
