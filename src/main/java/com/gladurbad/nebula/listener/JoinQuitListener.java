package com.gladurbad.nebula.listener;

import com.gladurbad.nebula.Nebula;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        Nebula.instance.getPlayerDataManager().add(event.getPlayer());
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        Nebula.instance.getPlayerDataManager().remove(event.getPlayer());
    }
}
