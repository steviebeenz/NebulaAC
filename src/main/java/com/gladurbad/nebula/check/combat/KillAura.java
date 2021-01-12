package com.gladurbad.nebula.check.combat;

import com.gladurbad.nebula.Nebula;
import com.gladurbad.nebula.check.Check;
import com.gladurbad.nebula.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Check implements Listener {

    public KillAura(final String name) {
        super(name);
    }

    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            final PlayerData playerData = Nebula.instance.getPlayerDataManager().getPlayerData((Player) event.getDamager());

            if (playerData != null) {
                final PlayerData.DataStorage dataStorage = playerData.getDataStorage();

                if (event.getDamager().getUniqueId() == playerData.getBukkitPlayer().getUniqueId()) {
                    dataStorage.lastAttackTime = System.currentTimeMillis();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final PlayerData playerData = Nebula.instance.getPlayerDataManager().getPlayerData(event.getPlayer());

        if (playerData != null) {
            final PlayerData.DataStorage dataStorage = playerData.getDataStorage();

            if (System.currentTimeMillis() - dataStorage.lastAttackTime > 2000L) return;

            this.checkAimbotHeuristic1(event, playerData, dataStorage);
        }
    }

    public void checkAimbotHeuristic1(final PlayerMoveEvent event, final PlayerData playerData, final PlayerData.DataStorage dataStorage) {
        final float deltaYaw = event.getTo().getYaw() % 360F - event.getFrom().getYaw() % 360F;
        final float deltaPitch = event.getTo().getPitch() - event.getFrom().getPitch();

        if (deltaYaw == 0 || deltaPitch == 0) return;

        if (deltaYaw < 20F && deltaPitch < 20F) {
            dataStorage.pitchSamples.add(deltaPitch);
        }

        if (dataStorage.pitchSamples.size() == 100) {
            List<Float> distinctList = dataStorage.pitchSamples.stream().distinct().collect(Collectors.toList());
            int duplicates = dataStorage.pitchSamples.size() - distinctList.size();

            if (duplicates < 5) {
                if (++dataStorage.aimHeuristic1Verbose > 5) {
                    flag(playerData, "duplicates=" + duplicates);
                }
            } else {
                dataStorage.aimHeuristic1Verbose -= dataStorage.aimHeuristic1Verbose > 0 ? 1 : 0;
            }

            dataStorage.pitchSamples.clear();
        }
    }
}
