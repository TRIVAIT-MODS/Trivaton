package org.trivait.trivaton.block.entity.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.trivait.trivaton.block.entity.custom.GeneratorBlockEntity;
import org.trivait.trivaton.block.entity.custom.SieveBlockEntity;

public class SieveBlockEntityRenderer implements BlockEntityRenderer<SieveBlockEntity> {

    public SieveBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(SieveBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack input = entity.getStack(0);
        ItemStack output_slot_1 = entity.getStack(1);
        ItemStack output_slot_2 = entity.getStack(2);
        ItemStack output_slot_3 = entity.getStack(3);
        ItemStack output_slot_4 = entity.getStack(4);
        ItemStack output_slot_5 = entity.getStack(5);
        ItemStack output_slot_6 = entity.getStack(6);

        renderOutputItem(output_slot_1, itemRenderer, entity, 0.5, 0.15, 0.5, matrices, vertexConsumers);
        renderOutputItem(output_slot_2, itemRenderer, entity, 0.5, 0.15, 0.2, matrices, vertexConsumers);
        renderOutputItem(output_slot_3, itemRenderer, entity, 0.5, 0.15, 0.8, matrices, vertexConsumers);
        renderOutputItem(output_slot_4, itemRenderer, entity, 0.2, 0.15, 0.5, matrices, vertexConsumers);
        renderOutputItem(output_slot_5, itemRenderer, entity, 0.8, 0.15, 0.5, matrices, vertexConsumers);
        renderOutputItem(output_slot_6, itemRenderer, entity, 0.8, 0.15, 0.8, matrices, vertexConsumers);

        matrices.push();
        matrices.translate(0.5, 0.87, 0.5);
        matrices.scale(0.5f, 0.5f, 0.5f);
        renderItem(input.copyWithCount(1), itemRenderer, entity, 0.5f, matrices, vertexConsumers);
        matrices.translate(0.6, 0, 0.6);
        renderItem(input.copyWithCount(input.getCount()-1), itemRenderer, entity, 0.5f, matrices, vertexConsumers);
        matrices.pop();
    }

    private void renderOutputItem(ItemStack stack, ItemRenderer itemRenderer, SieveBlockEntity entity, double x, double y, double z, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        if (stack.isEmpty()) return;
        matrices.push();
        matrices.translate(x, y, z);
        matrices.scale(0.5f, 0.5f, 0.5f);
        renderItem(stack, itemRenderer, entity, 0.5f, matrices, vertexConsumers);
        matrices.pop();
    }

    private void renderItem(ItemStack stack, ItemRenderer renderer, SieveBlockEntity entity, float scale, MatrixStack matrices, VertexConsumerProvider vertexConsumers){
        boolean isBlock = isBlock(renderer, entity, stack);
        if (!isBlock) {
            matrices.translate(0, -(0.099/scale), 0);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
        }
        renderer.renderItem(stack, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        if (!isBlock) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
        }
        int count = stack.getCount();

        if (count>5) {
            matrices.translate(0, 0.14*scale, 0);
            if (isBlock){
                matrices.translate(0.14*scale, 0, 0.14*scale);
            }
            if (!isBlock) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            }
            renderer.renderItem(stack, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            if (!isBlock) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
            }
            if (count>10) {
                matrices.translate(0, 0.14*scale, 0);
                if (isBlock){
                    matrices.translate(0.14*scale, 0, 0.14*scale);
                }
                if (!isBlock) {
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                }
                renderer.renderItem(stack, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                if (!isBlock) {
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
                }
                if (isBlock){
                    matrices.translate(-(0.14*scale), 0, -(0.14*scale));
                }
                matrices.translate(0, -(0.14*scale), 0);
            }
            if (isBlock){
                matrices.translate(0.14*scale, 0, 0.14*scale);
            }
            matrices.translate(0, -(0.14*scale), 0);
        }

        if (isBlock){
            matrices.translate(-(0.14*scale), 0, -(0.14*scale));
        }
        if (!isBlock) {
            matrices.translate(0, (0.099/scale), 0);
        }
    }

    private boolean isBlock(ItemRenderer renderer, SieveBlockEntity entity, ItemStack stack) {
        BakedModel bakedModel = renderer.getModel(stack, entity.getWorld(), null, 0);
        return bakedModel.useAmbientOcclusion();
    }

    private int getLightingLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
