package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.battles.CustomRulesGui;
import com.pixelmonessentials.common.util.EssentialsLogger;
import com.pixelmonessentials.common.util.ReflectionHelper;
import com.pixelmonmod.pixelmon.api.comm.ISyncHandler;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.comm.packetHandlers.battles.rules.ProposeBattleRules;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BattleRulePacketHandler implements IMessageHandler<ProposeBattleRules, IMessage> {
    public BattleRulePacketHandler(){
    }

    @Override
    public IMessage onMessage(ProposeBattleRules message, MessageContext ctx){
        int queryID= ReflectionHelper.getPrivateValue(message, "queryID");
        if(queryID==999){
            BattleRules rules=ReflectionHelper.getPrivateValue(message, "rules");
            EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(ctx.getServerHandler().player);
            if(gui instanceof CustomRulesGui){
                ((CustomRulesGui) gui).addCustomRules(rules);
            }
        }
        return null;
    }
}
