package com.pixelmonessentials.common.guis;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.ArrayList;

public class NpcHomeGui implements EssentialsGuis {
    private static final int id=1000;
    private ArrayList<EssentialsButton> buttons=new ArrayList<EssentialsButton>();

    public NpcHomeGui(){
    }

    public int getId(){
        return this.id;
    }

    public ArrayList<EssentialsButton> getButtons(){
        return this.buttons;
    }

    public void addButton(EssentialsButton button) {
        this.buttons.add(button);
    }

    public void init(EntityPlayerMP player, EntityNPCInterface npc){
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        NPCWrapper npcWrapper=new NPCWrapper(npc);
        CustomGuiWrapper gui=new CustomGuiWrapper(id, 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(0, "What do you want to do with this NPC?", 35, 10, 200, 20);
        gui.addButton(1, "Trainer Battle", 80, 50, 80, 20);
        ActionData trainerButtonAction=new ActionData("OPEN_GUI", npc.getUniqueID().toString()+"@"+1001);
        EssentialsButton trainerButton=new EssentialsButton(1, trainerButtonAction);
        this.addButton(trainerButton);
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
        playerWrapper.showCustomGui(npcWrapper, gui);
    }
}
