package com.pixelmonessentials.common.api.requirement;

import net.minecraft.entity.player.EntityPlayerMP;

public interface Requirement {
    boolean hasRequirement(String data, EntityPlayerMP player);
    String getName();
}
