package com.pixelmonessentials.common.battles.rules;

import com.google.common.io.Files;
import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class CustomRulesManager {
    public ArrayList<CustomRules> rulesets;
    public File rulesDir;

    public CustomRulesManager(){
        this.rulesets=new ArrayList<CustomRules>();
    }

    public void init(){
        this.rulesDir= new File(PixelmonEssentials.configFolder+"/rulesets/");
        if(!this.rulesDir.exists()){
            this.rulesDir.mkdir();
        }
        else{
            File[] files= this.rulesDir.listFiles();
            for(File file:files){
                if(file.getName().endsWith(".cfg")){
                    try {
                        this.load(file);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void load(File file) throws IOException {
        List<String> lines= Files.readLines(file, Charset.defaultCharset());
        String data="";
        for(int i=0;i<lines.size();i++){
            data+=lines.get(i);
            if(i!=lines.size()-1){
                data+="\n";
            }
        }
        String name=file.getName().replace(".cfg", "");
        BattleRules rules=new BattleRules();
        rules.importText(data);
        CustomRules rule=new CustomRules(name, rules);
        this.rulesets.add(rule);
    }

    public void saveAll(){
        File[] files=this.rulesDir.listFiles();
        for(File file:files){
            file.delete();
        }
        for(CustomRules ruleset:this.rulesets){
            save(ruleset);
        }
    }

    public void save(CustomRules rules){
        File file=new File(this.rulesDir, rules.getName()+".cfg");
        try {
            file.createNewFile();
            FileWriter writer=new FileWriter(file);
            writer.append(rules.getRules().exportText());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getRuleNames(){
        String[] names=new String[this.rulesets.size()];
        for(int i=0;i<this.rulesets.size();i++){
            names[i]=this.rulesets.get(i).getName();
        }
        return names;
    }

    public CustomRules getRules(String name){
        for(CustomRules rules:this.rulesets){
            if(rules.getName().equals(name)){
                return rules;
            }
        }
        return null;
    }
}
