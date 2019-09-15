package cc.ghast.auth.listeners;

import cc.ghast.auth.Auth;
import cc.ghast.auth.managers.ConfigManager;
import cc.ghast.auth.utils.Chat;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

import java.net.InetSocketAddress;

public class JoinEvent implements Listener {
    public JoinEvent(Auth auth){
        Bukkit.getPluginManager().registerEvents(this, auth);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        InetSocketAddress IPAdressPlayer = e.getPlayer().getAddress();
        String sfullip = IPAdressPlayer.toString();
        String[] fullip;
        String[] ipandport;
        fullip = sfullip.split("/");
        String sIpandPort = fullip[1];
        ipandport = sIpandPort.split(":");
        String sIp = ipandport[0];
        if (Auth.getInstance().getSessionManager().dupeIp(e.getPlayer().getAddress().toString())){
            if (Auth.getInstance().getSessionManager().dupeIpCount(sIp) > ConfigManager.getSettings().getInt("settings.max-players-per-ip"))
                e.setJoinMessage("");
                e.getPlayer().kickPlayer(Chat.translate(ConfigManager.getSettings().getString("messages.kick-message")));

        } else if (Auth.getInstance().getAuthManager().checkAlias(sIp).size() > ConfigManager.getSettings().getInt("settings.max-players-per-ip")){
            e.setJoinMessage("");
            e.getPlayer().kickPlayer(Chat.translate(ConfigManager.getSettings().getString("messages.kick-message")));

        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        if (e.getReason().equals(Chat.translate(ConfigManager.getSettings().getString("messages.kick-message")))){
            e.setLeaveMessage("");
        }
    }
}
