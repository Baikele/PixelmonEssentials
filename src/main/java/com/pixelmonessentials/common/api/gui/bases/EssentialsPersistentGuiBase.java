package com.pixelmonessentials.common.api.gui.bases;

import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsPersistentGui;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;

public class EssentialsPersistentGuiBase implements EssentialsPersistentGui {
    private int id;
    private ArrayList<EssentialsButton> buttons;

    public EssentialsPersistentGuiBase(int id){
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
