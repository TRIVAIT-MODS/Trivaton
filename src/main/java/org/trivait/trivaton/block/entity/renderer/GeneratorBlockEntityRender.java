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

public class GeneratorBlockEntityRender implements BlockEntityRenderer<GeneratorBlockEntity> {

    public GeneratorBlockEntityRender(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(GeneratorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack input = entity.getStack(GeneratorBlockEntity.INPUT_SLOT);
        ItemStack[] input_side = new ItemStack[]{
                entity.getStack(GeneratorBlockEntity.SIDE_INPUT_SLOT_1),
                entity.getStack(GeneratorBlockEntity.SIDE_INPUT_SLOT_2),
                entity.getStack(GeneratorBlockEntity.SIDE_INPUT_SLOT_3),
                entity.getStack(GeneratorBlockEntity.SIDE_INPUT_SLOT_4),
                entity.getStack(GeneratorBlockEntity.SIDE_INPUT_SLOT_5),
                entity.getStack(GeneratorBlockEntity.SIDE_INPUT_SLOT_6),
                entity.getStack(GeneratorBlockEntity.SIDE_INPUT_SLOT_7),
                entity.getStack(GeneratorBlockEntity.SIDE_INPUT_SLOT_8)
        };
        ItemStack output = entity.getStack(GeneratorBlockEntity.OUTPUT_SLOT);

        matrices.push();
        matrices.translate(0.5, 0.28, 0.5);
        matrices.scale(0.5f, 0.5f, 0.5f);
        renderItem(input, itemRenderer, entity, 0.5f, matrices, vertexConsumers);
        matrices.translate(0.6, 0, 0.6);
        renderItem(output, itemRenderer, entity, 0.5f, matrices, vertexConsumers);
        matrices.pop();
        matrices.push();
        matrices.translate(0.5, 0.28, 0.5);
        matrices.translate(0, 0.635, 0);
        matrices.scale(0.4f, 0.4f, 0.4f);

        double i = entity.getRotation();

        for (ItemStack stack : input_side) {
            double x = Math.sin(i)*1;
            double z = Math.cos(i)*1;
            matrices.translate(x, 0, z);
            renderItem(stack, itemRenderer, entity, 0.4f, matrices, vertexConsumers);
            matrices.translate(-x, 0, -z);
            i+=Math.toRadians(45);
        }
        matrices.pop();
    }

    private void renderItem(ItemStack stack, ItemRenderer renderer, GeneratorBlockEntity entity, float scale, MatrixStack matrices, VertexConsumerProvider vertexConsumers){
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

    private boolean isBlock(ItemRenderer renderer, GeneratorBlockEntity entity, ItemStack stack) {
        BakedModel bakedModel = renderer.getModel(stack, entity.getWorld(), null, 0);
        return bakedModel.useAmbientOcclusion();
    }

    private int getLightingLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
