package com.gladurbad.nebula.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MovementUtil {

    public List<Block> getCollidingBlocks(final Location location) {
        final ArrayList<Block> blocks = new ArrayList<>();

        for (double x = -0.3; x <= 0.3; x += 0.3) {
            for (double z = -0.3; z <= 0.3; z+= 0.3) {
                for (double y = -0.5001; y <= 2.3; y+= 0.7) {
                    final Block block = location.clone().add(x, y, z).getBlock();

                    if (block != null) {
                        blocks.add(block);
                    }
                }
            }
        }

        return blocks;
    }
}
