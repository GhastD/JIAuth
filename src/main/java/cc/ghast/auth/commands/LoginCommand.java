package cc.ghast.auth.commands;

import cc.ghast.auth.Auth;
import cc.ghast.auth.managers.AuthManager;
import cc.ghast.auth.managers.ConfigManager;
import cc.ghast.auth.managers.SessionManager;
import cc.ghast.auth.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {
    public LoginCommand(Auth auth){
        auth.getCommand("login").setExecutor(this);
    }

    private AuthManager authManager = Auth.getInstance().getAuthManager();
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return Chat.invalidEntity();
        Player player = (Player) sender;
        if (!player.hasPermission(ConfigManager.getSettings().getString("permissions.login"))) {
            player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.invalid-permission")));
            return false;
        }
        if (args.length != 1){
            player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.missing-password")));
            return false;
        }
        if (Auth.getInstance().getSessionManager().checkSession(player)){
            player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.already-loggedin")));
            return false;
        }

        if (authManager.attemptAuth(player, args[0])){
            Auth.getInstance().getSessionManager().startSession(player);
            player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.succesful-login")));
            return true;
        } else {
            player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("messages.invalid-password")));
            return false;
        }
    }
}
