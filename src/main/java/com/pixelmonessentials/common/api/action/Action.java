package com.pixelmonessentials.common.api.action;

import net.minecraft.entity.player.EntityPlayerMP;

public interface Action {
    public String getName();
    public void doAction(EntityPlayerMP player, ActionData data);
}
