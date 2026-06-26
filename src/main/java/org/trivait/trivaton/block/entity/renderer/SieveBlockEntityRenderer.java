package org.trivait.trivaton.block.entity.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.trivait.trivaton.block.entity.custom.SieveBlockEntity;

public class SieveBlockEntityRenderer implements BlockEntityRenderer<SieveBlockEntity> {

    public SieveBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(SieveBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack ingredient = entity.getStack(0);

        matrices.push();
        matrices.translate(0.5, 0.1, 0.5);
        matrices.scale(0.6f, 0.6f, 0.6f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

        ItemStack result1 = entity.getStack(1);
        if (!result1.isEmpty()) {
            itemRenderer.renderItem(result1, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            if (result1.getCount() > 3) {
                matrices.translate(0.05, 0.01, -0.05);
                itemRenderer.renderItem(result1, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                if (result1.getCount() > 6) {
                    matrices.translate(0.05, 0.01, -0.05);
                    itemRenderer.renderItem(result1, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                            OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    if (result1.getCount() > 9) {
                        matrices.translate(0.05, 0.01, -0.05);
                        itemRenderer.renderItem(result1, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                        matrices.translate(-0.05, -0.01, 0.05);
                    }
                    matrices.translate(-0.05, -0.01, 0.05);
                }
                matrices.translate(-0.05, -0.01, 0.05);
            }
        }
        ItemStack result2 = entity.getStack(2);
        matrices.translate(-0.4, 0, 0);
        if (!result2.isEmpty()) {
            itemRenderer.renderItem(result2, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            if (result2.getCount() > 3) {
                matrices.translate(0.05, 0.01, -0.05);
                itemRenderer.renderItem(result2, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                if (result2.getCount() > 6) {
                    matrices.translate(0.05, 0.01, -0.05);
                    itemRenderer.renderItem(result2, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                            OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    if (result2.getCount() > 9) {
                        matrices.translate(0.05, 0.01, -0.05);
                        itemRenderer.renderItem(result2, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                        matrices.translate(-0.05, -0.01, 0.05);
                    }
                    matrices.translate(-0.05, -0.01, 0.05);
                }
                matrices.translate(-0.05, -0.01, 0.05);
            }
        }

        ItemStack result3 = entity.getStack(3);
        matrices.translate(0.8, 0, 0);
        if (!result3.isEmpty()) {
            itemRenderer.renderItem(result3, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            if (result3.getCount() > 3) {
                matrices.translate(0.05, 0.01, -0.05);
                itemRenderer.renderItem(result3, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                if (result3.getCount() > 6) {
                    matrices.translate(0.05, 0.01, -0.05);
                    itemRenderer.renderItem(result3, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                            OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    if (result3.getCount() > 9) {
                        matrices.translate(0.05, 0.01, -0.05);
                        itemRenderer.renderItem(result3, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                        matrices.translate(-0.05, -0.01, 0.05);
                    }
                    matrices.translate(-0.05, -0.01, 0.05);
                }
                matrices.translate(-0.05, -0.01, 0.05);
            }
        }

        ItemStack result4 = entity.getStack(4);
        matrices.translate(-0.4, 0.4, 0);
        if (!result4.isEmpty()) {
            itemRenderer.renderItem(result4, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            if (result4.getCount() > 3) {
                matrices.translate(0.05, 0.01, -0.05);
                itemRenderer.renderItem(result4, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                if (result4.getCount() > 6) {
                    matrices.translate(0.05, 0.01, -0.05);
                    itemRenderer.renderItem(result4, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                            OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    if (result4.getCount() > 9) {
                        matrices.translate(0.05, 0.01, -0.05);
                        itemRenderer.renderItem(result4, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                        matrices.translate(-0.05, -0.01, 0.05);
                    }
                    matrices.translate(-0.05, -0.01, 0.05);
                }
                matrices.translate(-0.05, -0.01, 0.05);
            }
        }

        ItemStack result5 = entity.getStack(5);
        matrices.translate(0, -0.8, 0);
        if (!result5.isEmpty()) {
            itemRenderer.renderItem(result5, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            if (result5.getCount() > 3) {
                matrices.translate(0.05, 0.01, -0.05);
                itemRenderer.renderItem(result5, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                if (result5.getCount() > 6) {
                    matrices.translate(0.05, 0.01, -0.05);
                    itemRenderer.renderItem(result5, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                            OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    if (result5.getCount() > 9) {
                        matrices.translate(0.05, 0.01, -0.05);
                        itemRenderer.renderItem(result5, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                        matrices.translate(-0.05, -0.01, 0.05);
                    }
                    matrices.translate(-0.05, -0.01, 0.05);
                }
                matrices.translate(-0.05, -0.01, 0.05);
            }
        }

        ItemStack result6 = entity.getStack(6);
        matrices.translate(0.4, 0, 0);
        if (!result6.isEmpty()) {
            itemRenderer.renderItem(result6, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            if (result6.getCount() > 3) {
                matrices.translate(0.05, 0.01, -0.05);
                itemRenderer.renderItem(result6, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                if (result6.getCount() > 6) {
                    matrices.translate(0.05, 0.01, -0.05);
                    itemRenderer.renderItem(result6, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                            OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    if (result6.getCount() > 9) {
                        matrices.translate(0.05, 0.01, -0.05);
                        itemRenderer.renderItem(result6, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                        matrices.translate(-0.05, -0.01, 0.05);
                    }
                    matrices.translate(-0.05, -0.01, 0.05);
                }
                matrices.translate(-0.05, -0.01, 0.05);
            }
        }

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));

        matrices.translate(-0.4, 1.36, 0.4);

        if (ingredient.getCount() > 1) {
            matrices.translate(0.5, 0, -0.5);
            itemRenderer.renderItem(ingredient, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            if (ingredient.getCount() > 3) {
                matrices.translate(0.05, 0.01, -0.05);
                itemRenderer.renderItem(ingredient, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                if (ingredient.getCount() > 6) {
                    matrices.translate(0.05, 0.01, -0.05);
                    itemRenderer.renderItem(ingredient, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                            OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    matrices.translate(-0.05, -0.01, 0.05);
                }
                matrices.translate(-0.05, -0.01, 0.05);
            }
            matrices.translate(-0.5, 0, 0.5);
        }

        matrices.translate(0, -((double) entity.getProgress() / 220), 0);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
        itemRenderer.renderItem(ingredient, ModelTransformationMode.FIXED, getLightingLevel(entity.getWorld(), entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));

        matrices.pop();
    }

    private int getLightingLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
