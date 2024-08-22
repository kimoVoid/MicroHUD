package me.kimovoid.microhud.mixin.info.bbs;

import me.kimovoid.microhud.data.DataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class PlayerControllerMPMixin {

    @Shadow @Final private Minecraft mc;

    @Inject(method = "onPlayerDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBlockDestroyedByPlayer(Lnet/minecraft/world/World;IIII)V"))
    private void countBlockBreak(int p_78751_1_, int p_78751_2_, int p_78751_3_, int p_78751_4_, CallbackInfoReturnable<Boolean> cir) {
        DataStorage.INSTANCE.onPlayerBlockBreak(this.mc);
    }
}
