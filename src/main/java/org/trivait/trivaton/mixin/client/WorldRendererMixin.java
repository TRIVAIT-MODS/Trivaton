package org.trivait.trivaton.mixin.client;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.BlockBreakingInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.trivait.trivaton.item.custom.HammerItem;

import java.util.List;
import java.util.SortedSet;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, net.minecraft.block.BlockState state);

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;drawBlockOutline(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/entity/Entity;DDDLnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"
            )
    )
    private void drawHammerOutline(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        if (this.client.crosshairTarget instanceof BlockHitResult blockHitResult && blockHitResult.getType() == HitResult.Type.BLOCK) {
            Entity cameraEntity = camera.getFocusedEntity();

            if (cameraEntity instanceof PlayerEntity player) {
                ItemStack heldItem = player.getMainHandStack();

                if (heldItem.getItem() instanceof HammerItem) {
                    BlockPos initialPos = blockHitResult.getBlockPos();

                    List<BlockPos> extraBlocks = HammerItem.getBlocksToBeDestroyed(1, initialPos, player);

                    VertexConsumerProvider.Immediate immediate = this.client.getBufferBuilders().getEntityVertexConsumers();
                    VertexConsumer vertexConsumer = immediate.getBuffer(RenderLayer.getLines());

                    double cameraX = camera.getPos().getX();
                    double cameraY = camera.getPos().getY();
                    double cameraZ = camera.getPos().getZ();

                    MatrixStack matrixStack = new MatrixStack();

                    for (BlockPos pos : extraBlocks) {
                        if (pos.equals(initialPos)) continue;

                        net.minecraft.block.BlockState state = this.client.world.getBlockState(pos);

                        if (!state.isAir() && this.client.world.getWorldBorder().contains(pos)) {
                            this.drawBlockOutline(matrixStack, vertexConsumer, cameraEntity, cameraX, cameraY, cameraZ, pos, state);
                        }
                    }
                }
            }
        }
    }
}
