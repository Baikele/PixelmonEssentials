package com.pixelmonessentials.common.api.action.types.generalActions;

import com.pixelmonessentials.common.api.action.ActionBase;
import net.minecraft.command.ICommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.rcon.RConConsoleSource;
import noppes.npcs.api.wrapper.PlayerWrapper;

public class CommandAction extends ActionBase {
    public CommandAction(){
        super("COMMAND");
    }

    @Override
    public void doAction(String value, EntityPlayerMP playerMP){
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        String command=value.replace("@dp",playerMP.getName());
        ICommandManager icommandmanager = playerMP.getEntityWorld().getMinecraftServer().getCommandManager();
        icommandmanager.executeCommand(new RConConsoleSource(playerWrapper.getMCEntity().getEntityWorld().getMinecraftServer()), command);
    }
}
