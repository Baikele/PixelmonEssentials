package com.pixelmonessentials.common.api.gui.bases;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsFormGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.ArrayList;

public class EssentialsFormGuiBase implements EssentialsFormGui {
    private int id;
    private int submitButton;
    private ArrayList<EssentialsButton> buttons=new ArrayList<EssentialsButton>();

    public EssentialsFormGuiBase(int id, int submitButton){
        this.id=id;
        this.submitButton=submitButton;
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

    public int getSubmitButton(){
        return this.submitButton;
    }

    public void init(EntityPlayerMP player){
    }

    public void sendForm(EntityPlayerMP playerMP){
    }
}
