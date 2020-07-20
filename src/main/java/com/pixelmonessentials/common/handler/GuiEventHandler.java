package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsFormGui;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.api.gui.EssentialsScrollGui;
import com.pixelmonessentials.common.api.gui.bases.EssentialsMultiselectScrollGuiBase;
import com.pixelmonessentials.common.util.EssentialsLogger;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noppes.npcs.api.event.CustomGuiEvent;

public class GuiEventHandler {
    @SubscribeEvent
    public void onGuiButton(CustomGuiEvent.ButtonEvent event){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(event.player.getMCEntity());
        if(gui!=null){
            if(gui.getId()!=event.gui.getID()){
                return;
            }
            if(gui instanceof EssentialsFormGui){
                if(event.buttonId==((EssentialsFormGui) gui).getSubmitButton()){
                    ((EssentialsFormGui) gui).sendForm(event.player.getMCEntity());
                    return;
                }
            }
            EssentialsButton button=null;
            for(int i=0;i<gui.getButtons().size();i++){
                if(gui.getButtons().get(i).getId()==event.buttonId){
                    button=gui.getButtons().get(i);
                    break;
                }
            }
            PixelmonEssentials.actionHandler.doActions(button.getActions(), event.player.getMCEntity());
        }
    }

    @SubscribeEvent
    public void onScrollChange(CustomGuiEvent.ScrollEvent event){
        EssentialsGuis gui=PixelmonEssentials.essentialsGuisHandler.getGui(event.player.getMCEntity());
        if(gui!=null){
            if(gui.getId()!=event.gui.getID()){
                return;
            }
            if(gui instanceof EssentialsMultiselectScrollGuiBase){
                for(int scroll:((EssentialsMultiselectScrollGuiBase) gui).getMultiselectScrolls()){
                    if(scroll==event.scrollId){
                        ((EssentialsMultiselectScrollGuiBase) gui).updateFromMultiSelect(event.selection);
                        return;
                    }
                }
            }
            if(gui instanceof EssentialsScrollGui){
                ((EssentialsScrollGui) gui).setIndex(event.scrollId, event.selection[0]);
                ((EssentialsScrollGui) gui).updateScroll(event.scrollId, event.player.getMCEntity());
            }
        }
    }
}
