package com.pixelmonessentials.common.guis;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionNpcGuiData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.bases.EssentialsGuiBase;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.entity.EntityNPCInterface;

public class NpcHomeGui extends EssentialsGuiBase {
    EntityNPCInterface npc;

    public NpcHomeGui(){
        super(1000);
    }

    public void setNpc(EntityNPCInterface npc){
        this.npc=npc;
    }

    public EntityNPCInterface getNpc(){
        return this.npc;
    }

    @Override
    public void init(EntityPlayerMP player){
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        NPCWrapper npcWrapper=new NPCWrapper(npc);
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(0, "What do you want to do with this NPC?", 35, 10, 200, 20);
        gui.addButton(1, "Trainer Battle", 80, 50, 80, 20);
        ActionNpcGuiData trainerButtonAction=new ActionNpcGuiData("EDIT_TRAINER", npc, 1001);
        EssentialsButton trainerButton=new EssentialsButton(1, trainerButtonAction);
        gui.addButton(502, "Other", 80, 80, 80, 20);
        ActionNpcGuiData otherButtonAction=new ActionNpcGuiData("OTHER_NPC", npc, 0);
        EssentialsButton otherButton=new EssentialsButton(502, otherButtonAction);
        this.addButton(trainerButton);
        this.addButton(otherButton);
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
        playerWrapper.showCustomGui(gui);
    }
}
