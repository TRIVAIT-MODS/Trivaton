package org.trivait.trivaton.mixin;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.trivait.trivaton.item.ModItems;

import java.util.List;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {

    @Inject(method = "onLanding", at = @At("HEAD"))
    private void onAnvilLand(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity, CallbackInfo ci) {
        if (world.isClient()) return;

        Box box = new Box(pos);
        List<ItemEntity> items = world.getEntitiesByClass(ItemEntity.class, box, itemEntity -> true);

        for (ItemEntity itemEntity : items) {
            ItemStack stack = itemEntity.getStack();

            if (stack.isOf(ModItems.MUSIC_DISC_BASE)) {
                ItemStack newStack = new ItemStack(Items.MUSIC_DISC_11, stack.getCount());

                itemEntity.setStack(newStack);
            }
        }
    }
}
