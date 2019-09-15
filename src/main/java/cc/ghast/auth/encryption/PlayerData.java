package cc.ghast.auth.encryption;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerData {
    @Getter private String ip;
    @Getter private List<Player> playersCount = new ArrayList<>();

    public PlayerData(String ip, Player... players){
        this.ip = ip;
        Collections.addAll(Arrays.asList(playersCount, players));
    }
}
