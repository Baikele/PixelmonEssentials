package com.pixelmonessentials.common.guis.npc;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.bases.EssentialsFormGuiBase;
import com.pixelmonessentials.common.util.NpcScriptDataManipulator;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;
import noppes.npcs.entity.EntityNPCInterface;

public class OtherSettingsGui extends EssentialsFormGuiBase {
    private EntityNPCInterface npc;

    public OtherSettingsGui(){
        super(1010, 500);
        this.addButton(new EssentialsButton(501, new ActionData("CLOSE_GUI")));
    }

    public void setNpc(EntityNPCInterface npc){
        this.npc=npc;
    }

    @Override
    public void init(EntityPlayerMP player){
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        CustomGuiWrapper gui=this.getGui();
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
        playerWrapper.showCustomGui(gui);
    }

    public CustomGuiWrapper getGui(){
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(100, "Other Settings", 90, 10, 200, 20);
        gui.addLabel(101, "Dialog on sight:", 45, 50, 200, 20);
        Object dialogObject=NpcScriptDataManipulator.getJavascriptVariable(new NPCWrapper(this.npc), "losDialog");
        if(dialogObject!=null){
            gui.addTextField(401, 130, 50, 35, 20).setText(((int)dialogObject)+"");
        }
        else{
            gui.addTextField(401, 130, 50, 35, 20);
        }
        gui.addButton(500, "Save", 40, 200, 80, 20);
        gui.addButton(501, "Cancel", 140, 200, 80, 20);
        return gui;
    }

    @Override
    public void sendForm(EntityPlayerMP playerMP){
        String errorMessage="";
        CustomGuiWrapper gui= CustomGuiController.getOpenGui(playerMP);
        String dialogText=((CustomGuiTextFieldWrapper)gui.getComponent(401)).getText();
        int dialog=0;
        if(dialogText==null){
            NpcScriptDataManipulator.deleteJavascriptVariable(new NPCWrapper(this.npc), "losDialog");
            playerMP.closeScreen();
            return;
        }
        try{
            dialog=Integer.parseInt(dialogText);
            if(dialog<=0){
                errorMessage="The dialog id entered was lower or equal than 0!";
            }
        }catch (NumberFormatException e){
            errorMessage="The sight dialog entered was not a number!";
        }
        if(!errorMessage.equals("")){
            gui.addLabel(404, errorMessage, 5, 25, 200, 20, 16711680);
            gui.update(new PlayerWrapper(playerMP));
        }
        else{
            NpcScriptDataManipulator.setJavascriptVariable(new NPCWrapper(this.npc), "losDialog", dialog);
            playerMP.closeScreen();
        }
    }
}
