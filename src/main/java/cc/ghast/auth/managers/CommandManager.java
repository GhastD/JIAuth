package cc.ghast.auth.managers;

import cc.ghast.auth.Auth;
import cc.ghast.auth.commands.LoginCommand;
import cc.ghast.auth.commands.RegisterCommand;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class CommandManager {
    private Auth auth;
    @Getter private LoginCommand loginCommand;
    @Getter private RegisterCommand registerCommand;
    public CommandManager(Auth auth){
        this.auth = auth;
        init();
    }

    private void init(){
        loginCommand = new LoginCommand(auth);
        registerCommand = new RegisterCommand(auth);
    }
}
