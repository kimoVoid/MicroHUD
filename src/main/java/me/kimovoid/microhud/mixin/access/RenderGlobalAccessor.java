package me.kimovoid.microhud.mixin.access;

import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderGlobal.class)
public interface RenderGlobalAccessor {

    @Accessor("countEntitiesRendered")
    int getEntitiesRendered();

    @Accessor("countEntitiesTotal")
    int getEntitiesTotal();
}
