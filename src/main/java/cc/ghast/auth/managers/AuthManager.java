package cc.ghast.auth.managers;

import cc.ghast.auth.Auth;
import cc.ghast.auth.encryption.Hashing;
import cc.ghast.auth.encryption.LogTypes;
import cc.ghast.auth.utils.Chat;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class AuthManager {
    private Auth auth;
    private LogTypes type;
    public AuthManager(Auth auth){
        this.auth = auth;
        init();
    }

    private void init(){
        type = LogTypes.FLAT_FILE;
    }

    public boolean isRegistered(Player player) {
        try {
            switch (type){
                case FLAT_FILE: {
                    return ConfigManager.getData().getConfig().contains(player.getUniqueId().toString());
                }
                default:
                    return false;
            }
        } catch (NullPointerException e){
            return false;
        }
    }

    public boolean attemptAuth(Player player, String key){
        String pass = getHash(player);
        if (pass == null) return false;
        String[] realPass = pass.split(":");
        String hash = realPass[0];
        String salt = realPass[1];
        return Hashing.verifyPassword(key, hash, salt);
    }

    public void registerPassword(Player player, String key){
        Random rand = new SecureRandom();
        int salt = rand.nextInt();
        String hashed = Hashing.hashPassword(key, Integer.toString(salt));
        lockPassword(player, hashed.toString(), Integer.toString(salt));
    }

    private void lockPassword(Player player, String key, String salt)
    {
        switch (type){
            case FLAT_FILE: {
                InetSocketAddress IPAdressPlayer = player.getAddress();
                String sfullip = IPAdressPlayer.toString();
                String[] fullip;
                String[] ipandport;
                fullip = sfullip.split("/");
                String sIpandPort = fullip[1];
                ipandport = sIpandPort.split(":");
                String sIp = ipandport[0];
                ConfigManager.getData().set("auth." +player.getUniqueId().toString() + ".pass", key + ":" + salt);
                ConfigManager.getData().set("auth." +player.getUniqueId().toString() + ".ip", sIp);
            }
        }
    }

    private String getHash(Player player){
        try {
            switch (type){
                case FLAT_FILE: {
                    return ConfigManager.getData().getString("auth." + player.getUniqueId().toString() + ".pass");
                }
                default:
                    return null;
            }
        } catch (NullPointerException e){
            Chat.sendConsoleMessage("Error attempting to login user without hash");
            return null;
        }

    }

    public List<String> checkAlias(String ip){
        List<String> aliases = new ArrayList<>();
        ConfigManager.getData().getConfig().getConfigurationSection("auth").getKeys(false).stream().forEach(item->{
            if (ConfigManager.getData().get("auth." + item + ".ip").equals(ip)){
                aliases.add(item);
            }
        });
        return aliases;
    }
}
