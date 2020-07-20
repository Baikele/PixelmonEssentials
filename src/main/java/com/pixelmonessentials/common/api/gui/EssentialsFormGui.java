package com.pixelmonessentials.common.api.gui;

import net.minecraft.entity.player.EntityPlayerMP;

public interface EssentialsFormGui extends EssentialsGuis {
    void sendForm(EntityPlayerMP playerMP);
    int getSubmitButton();
}
