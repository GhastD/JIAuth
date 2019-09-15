package cc.ghast.auth.commands;

import cc.ghast.auth.Auth;
import cc.ghast.auth.managers.AuthManager;
import cc.ghast.auth.managers.ConfigManager;
import cc.ghast.auth.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {

    public RegisterCommand(Auth auth){
        auth.getCommand("register").setExecutor(this);
    }

    private AuthManager authManager = Auth.getInstance().getAuthManager();
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)) return Chat.invalidEntity();
        Player player = (Player) sender;
        if (!player.hasPermission(ConfigManager.getSettings().getString("permissions.register"))){
            player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.invalid-permission")));
            return false;
        }
        switch (args.length){
            case 2: {
                if (authManager.isRegistered(player)){
                    player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.already-registered")));
                    return false;
                }
                if (!args[0].equals(args[1])){
                    player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.different-register-password")));
                    return false;
                }
                authManager.registerPassword(player, args[0]);
                Auth.getInstance().getSessionManager().startSession(player);
                player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.succesful-registration")));
                return true;
            }
            default: {
                player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.missing-register-password")));
                return false;
            }
        }
    }


}
