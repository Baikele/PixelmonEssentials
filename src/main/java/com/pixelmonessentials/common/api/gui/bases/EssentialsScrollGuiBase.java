package com.pixelmonessentials.common.api.gui.bases;

import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsScrollGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.ArrayList;

public class EssentialsScrollGuiBase implements EssentialsScrollGui {
    private int id;
    ArrayList<EssentialsButton> buttons=new ArrayList<EssentialsButton>();

    public EssentialsScrollGuiBase(int id){
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

    public void init(EntityPlayerMP player){
    }

    public void updateScroll(int id, EntityPlayerMP player){
    }

    public void setIndex(int id, String selection){
    }
}
