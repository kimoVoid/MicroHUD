package me.kimovoid.microhud.mixin.access;

import net.minecraft.world.SpawnerAnimals;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;

@Mixin(SpawnerAnimals.class)
public interface SpawnerAnimalsAccessor {

    @Accessor("eligibleChunksForSpawning")
    HashMap getEligibleChunksForSpawning();
}
