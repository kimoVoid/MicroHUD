package me.kimovoid.microhud.data;

import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.mixin.access.SpawnerAnimalsAccessor;
import me.kimovoid.microhud.mixin.access.WorldServerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.WorldServer;

public class MobCapData {

    public int hostile = -1;
    public int hostileMax = -1;
    public int passive = -1;
    public int passiveMax = -1;
    public int water = -1;
    public int waterMax = -1;
    public int ambient = -1;
    public int ambientMax = -1;

    private int dimension = 0;

    public void getClientMobCap() throws NullPointerException {
        if (!MicroHUD.CONFIG.infoMobCaps) {
            return;
        }

        int dim = Minecraft.getMinecraft().thePlayer.dimension;
        WorldServer[] worlds = Minecraft.getMinecraft().getIntegratedServer().worldServers;

        if (this.dimension != dim) {
            this.dimension = dim;
            this.resetMobCaps();
        }

        switch (dim) {
            case 0: // overworld
                this.hostile = getMobCount(EnumCreatureType.monster, worlds[0]);
                this.hostileMax = getMobCap(EnumCreatureType.monster, worlds[0]);
                this.passive = getMobCount(EnumCreatureType.creature, worlds[0]);
                this.passiveMax = getMobCap(EnumCreatureType.creature, worlds[0]);
                this.water = getMobCount(EnumCreatureType.waterCreature, worlds[0]);
                this.waterMax = getMobCap(EnumCreatureType.waterCreature, worlds[0]);
                this.ambient = getMobCount(EnumCreatureType.ambient, worlds[0]);
                this.ambientMax = getMobCap(EnumCreatureType.ambient, worlds[0]);
                break;
            case -1: // nether
                this.hostile = getMobCount(EnumCreatureType.monster, worlds[1]);
                this.hostileMax = getMobCap(EnumCreatureType.monster, worlds[1]);
                break;
            case 1: // end
                this.hostile = getMobCount(EnumCreatureType.monster, worlds[2]);
                this.hostileMax = getMobCap(EnumCreatureType.monster, worlds[2]);
                break;
        }
    }

    public void resetMobCaps() {
        this.hostile = -1;
        this.hostileMax = -1;
        this.passive = -1;
        this.passiveMax = -1;
        this.water = -1;
        this.waterMax = -1;
        this.ambient = -1;
        this.ambientMax = -1;
    }

    public int getMobCount(EnumCreatureType category, WorldServer world) {
        return world.countEntities(category.getCreatureClass());
    }

    public int getMobCap(EnumCreatureType category, WorldServer world) {
        return category.getMaxNumberOfCreature() * getMobSpawningChunkCount(world) / 256;
    }

    public int getMobSpawningChunkCount(WorldServer world) {
        SpawnerAnimals spawner = ((WorldServerAccessor)world).getAnimalSpawner();
        return ((SpawnerAnimalsAccessor)(Object)spawner).getEligibleChunksForSpawning().size();
    }

    public boolean isEmpty() {
        return this.hostile == -1 && this.passive == -1 && this.water == -1 && this.ambient == -1;
    }
}
