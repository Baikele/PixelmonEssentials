package com.pixelmonessentials.common.api.gui.bases;

import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.ArrayList;

public class EssentialsGuiBase implements EssentialsGuis {
    private int id;
    private ArrayList<EssentialsButton> buttons;

    public EssentialsGuiBase(int id){
        this.id=id;
    }

    public int getId(){
        return this.id;
    }

    public ArrayList<EssentialsButton> getButtons(){
        return this.buttons;
    }

    public void addButton(EssentialsButton button){
        this.buttons.add(button);
    }

    public void init(EntityPlayerMP playerMP){
    }
}
