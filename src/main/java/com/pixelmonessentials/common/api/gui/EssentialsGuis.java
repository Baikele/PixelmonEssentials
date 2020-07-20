package com.pixelmonessentials.common.api.gui;

import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.ArrayList;

public interface EssentialsGuis {
    int getId();
    void init(EntityPlayerMP playerMP);
    ArrayList<EssentialsButton> getButtons();
    void addButton(EssentialsButton button);
}
