package cc.ghast.auth.listeners;

import cc.ghast.auth.Auth;
import cc.ghast.auth.managers.SessionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class CancelledEvents implements Listener {

    public CancelledEvents(Auth auth){
        Bukkit.getPluginManager().registerEvents(this, auth);
    }

    private SessionManager sessionManager = Auth.getInstance().getSessionManager();
    private List<Player> movingPlayers = new ArrayList<>();
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (!sessionManager.checkSession(e.getPlayer())){
            Location loc = e.getFrom();
            movingPlayers.add(e.getPlayer());
            Bukkit.getScheduler().scheduleAsyncDelayedTask(Auth.getInstance(), () -> {
                e.getPlayer().teleport(loc);
                movingPlayers.remove(e.getPlayer());
            }
            , 1L);
        }
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent e){

        if (!sessionManager.checkSession(e.getPlayer())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onTP(PlayerTeleportEvent e){
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN) && movingPlayers.contains(e.getPlayer())) return;

        if (!sessionManager.checkSession(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBed(PlayerBedEnterEvent e){
        if (!sessionManager.checkSession(e.getPlayer())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        if (!sessionManager.checkSession(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractBlock(PlayerInteractEvent e){
        if (!sessionManager.checkSession(e.getPlayer())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        if (!sessionManager.checkSession(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if (!sessionManager.checkSession(e.getPlayer())){
            e.setCancelled(true);
        }
    }
}
