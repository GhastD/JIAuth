package cc.ghast.auth;

import cc.ghast.auth.listeners.CancelledEvents;
import cc.ghast.auth.listeners.JoinEvent;
import cc.ghast.auth.managers.AuthManager;
import cc.ghast.auth.managers.CommandManager;
import cc.ghast.auth.managers.ConfigManager;
import cc.ghast.auth.managers.SessionManager;
import cc.ghast.auth.utils.Chat;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Auth extends JavaPlugin {
    @Getter private static Auth instance;
    @Getter private AuthManager authManager;
    @Getter private SessionManager sessionManager;
    @Getter private ConfigManager configManager;
    @Getter private CommandManager commandManager;

    public void onEnable(){
        instance = this;
        initManagers();
        initListeners();
        Chat.sendConsoleMessage("&aSuccesfully loaded JIAuth");
    }

    private void initManagers(){
        Chat.sendConsoleMessage("&6Starting managers");
        authManager = new AuthManager(this);
        Chat.sendConsoleMessage("&aInitialized auth manager");
        sessionManager = new SessionManager(this);
        Chat.sendConsoleMessage("&aInitialized session manager");
        configManager = new ConfigManager(this);
        Chat.sendConsoleMessage("&aInitialized config manager");
        commandManager = new CommandManager(this);
        Chat.sendConsoleMessage("&aInitialized command manager");
    }

    private void initListeners(){
        Chat.sendConsoleMessage("&6Starting listeners");
        new CancelledEvents(this);
        Chat.sendConsoleMessage("&aInitialized CancelledEvents listeners");
        new JoinEvent(this);
        Chat.sendConsoleMessage("&aInitialized Join/leave listener");
    }
}
