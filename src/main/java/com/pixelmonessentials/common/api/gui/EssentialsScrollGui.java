package com.pixelmonessentials.common.api.gui;

import net.minecraft.entity.player.EntityPlayerMP;

public interface EssentialsScrollGui extends EssentialsGuis {
    void updateScroll(int id, EntityPlayerMP player);
    void setIndex(int id, String selection);
}
