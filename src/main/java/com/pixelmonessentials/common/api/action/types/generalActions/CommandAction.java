package com.pixelmonessentials.common.api.action.types.generalActions;

import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionStringData;
import net.minecraft.command.ICommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.rcon.RConConsoleSource;
import noppes.npcs.api.wrapper.PlayerWrapper;

public class CommandAction extends ActionBase {
    public CommandAction(){
        super("COMMAND");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        if(data instanceof ActionStringData){
            PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
            String command=((ActionStringData) data).getValue().replace("@dp",playerMP.getName());
            ICommandManager icommandmanager = playerMP.getEntityWorld().getMinecraftServer().getCommandManager();
            icommandmanager.executeCommand(new RConConsoleSource(playerWrapper.getMCEntity().getEntityWorld().getMinecraftServer()), command);
        }
    }
}
