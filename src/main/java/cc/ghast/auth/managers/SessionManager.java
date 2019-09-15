package cc.ghast.auth.managers;

import cc.ghast.auth.Auth;
import cc.ghast.auth.encryption.PlayerData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionManager {
    private Auth auth;
    private Map<Player, String> sessionPlayers = new HashMap<>();
    public SessionManager(Auth auth){
        this.auth = auth;
        init();
    }

    private void init(){

    }
    public void startSession(Player player){
        if (!sessionPlayers.containsKey(player)){
            sessionPlayers.put(player, player.getAddress().toString());
        }
    }

    public void destroySession(Player player){
        sessionPlayers.remove(player);
    }

    public boolean checkSession(Player player){
        return sessionPlayers.containsKey(player);
    }

    public boolean dupeIp(String ip){
        return sessionPlayers.containsValue(ip);
    }
    public int dupeIpCount(String ip){
        int ips = 1;
        for (Player player : sessionPlayers.keySet()) {
            if (sessionPlayers.get(player).equals(ip)){
                ips++;
            }
        }
        return ips;
    }
}
