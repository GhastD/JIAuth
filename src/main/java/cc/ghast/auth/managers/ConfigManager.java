package cc.ghast.auth.managers;

import cc.ghast.auth.Auth;
import cc.ghast.auth.utils.Configuration;
import lombok.Getter;

public class ConfigManager {
    private Auth auth;
    @Getter private static Configuration settings;
    @Getter private static Configuration data;
    public ConfigManager(Auth auth){
        this.auth = auth;
        init();
    }

    public void init(){
        settings = new Configuration("settings.yml", auth);
        data = new Configuration("data.yml", auth);
    }
    public void disinit(){
        settings.save();
        data.save();
    }

    public void reload(){
        settings.save();
        data.save();
        init();
    }
}
