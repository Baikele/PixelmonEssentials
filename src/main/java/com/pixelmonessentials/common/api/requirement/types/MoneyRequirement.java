package com.pixelmonessentials.common.api.requirement.types;

import com.pixelmonessentials.common.api.requirement.Requirement;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import net.minecraft.entity.player.EntityPlayerMP;

public class MoneyRequirement implements Requirement {
    private final String name="MONEY";

    public String getName(){
        return this.name;
    }

    public boolean hasRequirement(String data, EntityPlayerMP player){
        IPixelmonBankAccount bankAccount=(IPixelmonBankAccount) Pixelmon.moneyManager.getBankAccount(player).orElse(null);
        int amount=Integer.parseInt(data);
        if(bankAccount!=null){
            return bankAccount.getMoney() >= amount;
        }
        return false;
    }
}
