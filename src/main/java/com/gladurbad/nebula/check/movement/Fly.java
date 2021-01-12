package com.gladurbad.nebula.check.movement;

import com.gladurbad.nebula.Nebula;
import com.gladurbad.nebula.check.Check;
import com.gladurbad.nebula.data.PlayerData;
import com.gladurbad.nebula.util.MovementUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Fly extends Check implements Listener {

    public Fly(final String name) {
        super(name);
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final PlayerData playerData = Nebula.instance.getPlayerDataManager().getPlayerData((Player) event.getEntity());

            if (playerData != null) {
                final PlayerData.DataStorage dataStorage = playerData.getDataStorage();

                dataStorage.lastDamageTakenTime = System.currentTimeMillis();
            }
        }
    }
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final PlayerData playerData = Nebula.instance.getPlayerDataManager().getPlayerData(event.getPlayer());

        if (playerData != null) {
            final PlayerData.DataStorage dataStorage = playerData.getDataStorage();

            this.checkHorizontalFromGroundDistance(event, playerData, dataStorage);
            this.checkFromGroundDistance(event, playerData, dataStorage);
            this.checkFakeGround(event, playerData, dataStorage);
        }
    }

    public void checkHorizontalFromGroundDistance(final PlayerMoveEvent event, final PlayerData playerData, final PlayerData.DataStorage dataStorage) {
        if (playerData.getBukkitPlayer().hasPotionEffect(PotionEffectType.SPEED)) return;
        if (playerData.getBukkitPlayer().getWalkSpeed() > 1) return;
        if (System.currentTimeMillis() - dataStorage.lastDamageTakenTime < 1500L) return;

        if (MovementUtil.getCollidingBlocks(event.getTo()).stream().anyMatch(block -> block.getType() != Material.AIR)) {
            dataStorage.lastHorizontalFlyDistanceLocation = event.getTo();
        }

        if (dataStorage.lastHorizontalFlyDistanceLocation == null) return;

        if (dataStorage.lastHorizontalFlyDistanceLocation.toVector().setY(0).distance(event.getTo().toVector().setY(0)) > 7) {
            flag(playerData, "flew too far from ground");
            playerData.getBukkitPlayer().teleport(dataStorage.lastHorizontalFlyDistanceLocation);
        }
    }

    public void checkFakeGround(final PlayerMoveEvent event, final PlayerData playerData, final PlayerData.DataStorage dataStorage) {
        if (dataStorage.flyFakeGroundLastOnGroundLocation == null) return;
        if (System.currentTimeMillis() - dataStorage.lastDamageTakenTime < 1500L) return;

        final boolean clientGround = playerData.getBukkitPlayer().isOnGround();
        final boolean positionGround = event.getTo().getY() % (1D/64) == 0;

        if (MovementUtil.getCollidingBlocks(event.getTo()).stream().anyMatch(block -> block.getType() != Material.AIR))
            dataStorage.flyFakeGroundLastOnGroundLocation = event.getTo();

        if (MovementUtil.getCollidingBlocks(event.getTo()).stream().anyMatch(block -> block.getType() == Material.SLIME_BLOCK))
            return;

        if (clientGround != positionGround) {
            if (++dataStorage.flyFakeGroundVerbose > 5) {
                flag(playerData, "cg=" + clientGround + " pg=" + positionGround);
                playerData.getBukkitPlayer().teleport(dataStorage.flyFakeGroundLastOnGroundLocation);
                dataStorage.flyFakeGroundVerbose = 0;
            }
        } else {
            dataStorage.flyFakeGroundVerbose -= dataStorage.flyFakeGroundVerbose > 0 ? 0.5 : 0;
        }
    }

    public void checkFromGroundDistance(final PlayerMoveEvent event, final PlayerData playerData, final PlayerData.DataStorage dataStorage) {
        if (playerData.getBukkitPlayer().hasPotionEffect(PotionEffectType.JUMP)) return;
        if (System.currentTimeMillis() - dataStorage.lastDamageTakenTime < 1500L) return;
        if (playerData.getBukkitPlayer().isOnGround()) dataStorage.flyLastGroundY = event.getTo().getY();
        else {
            final double difference = event.getTo().getY() - dataStorage.flyLastGroundY;
            if (difference > 10) {
                flag(playerData, "distance=" + difference);
                playerData.getBukkitPlayer().teleport(event.getTo().clone().add(new Vector(0, -difference, 0)));
            }
        }
    }
}
