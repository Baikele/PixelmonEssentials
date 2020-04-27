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

public class TrainerDataGui implements EssentialsGuis {
    private static final int id=1001;
    private static String team="None";
    private ArrayList<EssentialsButton> buttons=new ArrayList<EssentialsButton>();

    public TrainerDataGui(){

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
        CustomGuiWrapper gui=new CustomGuiWrapper();
        gui.setDoesPauseGame(false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(0, "Trainer Data", 90, 10, 200, 20);
        gui.addLabel(1, "Init Dialog ID", 20, 40, 200, 20);
        gui.addTextField(1, 110, 40, 30, 20);
        gui.addLabel(2, "Win Dialog ID", 20, 70, 200, 20);
        gui.addTextField(2, 110, 70, 30, 20);
        gui.addLabel(3, "Loss Dialog ID", 20, 100, 200, 20);
        gui.addTextField(3, 110, 100, 30, 20);
        gui.addLabel(4, "Team:", 150, 40, 100, 20);
        gui.addLabel(41, team, 180, 40, 100, 20);
        gui.addButton(4, "Select", 150, 60, 50, 20);
        gui.addLabel(5, "Encounter Theme", 20, 130, 200, 20);
        gui.addTextField(5, 130, 130, 100, 20);
        gui.addLabel(6, "Battle Theme", 20, 155, 200, 20);
        gui.addTextField(6, 130, 155, 100, 20);
        gui.addLabel(7, "Victory Theme", 20, 180, 200, 20);
        gui.addTextField(7, 130, 180, 100, 20);
        gui.addButton(8, "Save", 50, 215, 50, 20);
        this.buttons.add(new EssentialsButton(8, new ActionData("SAVE_TRAINER", npc.getUniqueID().toString())));
        gui.addButton(9, "Cancel", 150, 215, 50, 20);
        this.buttons.add(new EssentialsButton(9, new ActionData("CLOSE_GUI", "It doesn't even matter")));
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
        playerWrapper.showCustomGui(npcWrapper, gui);
    }
}
