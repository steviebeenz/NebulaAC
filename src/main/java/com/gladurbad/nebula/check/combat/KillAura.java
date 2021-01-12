package com.gladurbad.nebula.check.combat;

import com.gladurbad.nebula.Nebula;
import com.gladurbad.nebula.check.Check;
import com.gladurbad.nebula.data.PlayerData;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
            this.checkAimbotHeuristic2(event, playerData, dataStorage);
            this.checkAimbotHeuristic3(event, playerData, dataStorage);
        }
    }

    public void checkAimbotHeuristic3(final PlayerMoveEvent event, final PlayerData playerData, final PlayerData.DataStorage dataStorage) {
        final float deltaYaw = event.getTo().getYaw() % 360F - event.getFrom().getYaw() % 360F;
        final float deltaPitch = Math.abs(event.getTo().getPitch() - event.getFrom().getPitch());

        if (deltaPitch < 0.1 && deltaYaw > 3.5) {
            if (++dataStorage.aim3Verbose > 10) {
                dataStorage.aim3Verbose = 0;
                flag(playerData, String.format("deltaYaw=%.2f, deltaPitch=%.2f", deltaYaw, deltaPitch));
            }
        } else {
            dataStorage.aim3Verbose = dataStorage.aim3Verbose > 0 ? 1 : 0;
        }
    }

    public void checkAimbotHeuristic2(final PlayerMoveEvent event, final PlayerData playerData, final PlayerData.DataStorage dataStorage) {
        ++dataStorage.aim2TotalMoves;

        if (event.getTo().getYaw() == event.getFrom().getYaw() && event.getTo().getPitch() == event.getFrom().getPitch()
                && event.getTo().distance(event.getFrom()) > 0) ++dataStorage.aim2Moves;

        if ((event.getTo().getYaw() != event.getFrom().getYaw() || event.getTo().getPitch() != event.getFrom().getPitch())
                && event.getTo().distance(event.getFrom()) > 0) ++dataStorage.aim2PosLooks;

        if (dataStorage.aim2TotalMoves == 50) {
            if (dataStorage.aim2Moves >= 25 && dataStorage.aim2PosLooks <= 35) {
                if (++dataStorage.aim2Verbose > 2) {
                    flag(playerData, "moves=" + dataStorage.aim2Moves + " moveLooks=" + dataStorage.aim2PosLooks);
                }
            } else {
                dataStorage.aim2Verbose -= dataStorage.aim2Verbose > 0 ? 1 : 0;
            }
            dataStorage.aim2PosLooks = dataStorage.aim2Moves = dataStorage.aim2Looks = dataStorage.aim2TotalMoves = 0;
        }
    }

    public void checkAimbotHeuristic1(final PlayerMoveEvent event, final PlayerData playerData, final PlayerData.DataStorage dataStorage) {
        final float deltaYaw = event.getTo().getYaw() % 360F - event.getFrom().getYaw() % 360F;
        final float deltaPitch = event.getTo().getPitch() - event.getFrom().getPitch();

        if (deltaYaw == 0 || deltaPitch == 0) return;

        if (deltaYaw < 20F && deltaPitch < 5F) {
            dataStorage.pitchSamples.add(deltaPitch);
        }

        if (dataStorage.pitchSamples.size() == 100) {
            List<Float> distinctList = dataStorage.pitchSamples.stream().distinct().collect(Collectors.toList());
            int duplicates = dataStorage.pitchSamples.size() - distinctList.size();

            if (duplicates < 7) {
                if (++dataStorage.aimHeuristic1Verbose > 3) {
                    flag(playerData, "duplicates=" + duplicates);
                }
            } else {
                dataStorage.aimHeuristic1Verbose -= dataStorage.aimHeuristic1Verbose > 0 ? 1 : 0;
            }

            dataStorage.pitchSamples.clear();
        }
    }
}
