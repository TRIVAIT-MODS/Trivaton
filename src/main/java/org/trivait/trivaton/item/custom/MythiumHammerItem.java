package org.trivait.trivaton.item.custom;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class MythiumHammerItem extends HammerItem{
    public MythiumHammerItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initalBlockPos, PlayerEntity player) {
        List<BlockPos> positions = new ArrayList<>();
        HitResult hit = player.raycast(player.getAttributeValue(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE), 0, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;

            if (player.isSneaking()) {
                positions.add(blockHit.getBlockPos());
                return positions;
            }
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ() + z));
                    }
                }
            }
        }

        return positions;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Text text = Text.translatable("item.trivaton.hammer.desc").setStyle(Style.EMPTY.withColor(Formatting.GRAY)).append(Text.literal("3x3x3").setStyle(Style.EMPTY.withColor(Formatting.AQUA)));
        tooltip.add(text);
    }
}
