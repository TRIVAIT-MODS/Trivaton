package org.trivait.trivaton.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.trivait.trivaton.item.component.ModDataComponentTypes;

public class CreativeCircuitBoardItem extends CircuitBoardItem{

    public CreativeCircuitBoardItem(Settings settings) {
        super(settings, 10);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            stack.set(ModDataComponentTypes.CREATIVE_CIRCUIT_BOARD_LEVEL, player.isSneaking() ? getPreviousLevel(stack) : getNextLevel(stack));
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS);
            player.sendMessage(Text.translatable("item.trivaton.circuit_board.desc").setStyle(Style.EMPTY.withColor(Formatting.GRAY)).append(Text.literal(""+getLevel(stack)).setStyle(Style.EMPTY.withColor(Formatting.AQUA))), true);

            return TypedActionResult.success(stack);
        }
        return TypedActionResult.pass(stack);
    }

    private int getNextLevel(ItemStack stack) {
        switch (getLevel(stack)){
            case 10 -> {
                return 15;
            }
            case 15 -> {
                return 20;
            }
            case 20 -> {
                return 25;
            }
            case 25 -> {
                return 30;
            }
            case 30 -> {
                return 5;
            }
            default -> {
                return 10;
            }
        }
    }
    private int getPreviousLevel(ItemStack stack) {
        switch (getLevel(stack)){
            case 10 -> {
                return 5;
            }
            case 15 -> {
                return 10;
            }
            case 20 -> {
                return 15;
            }
            case 25 -> {
                return 20;
            }
            case 30 -> {
                return 25;
            }
            case 5 -> {
                return 30;
            }
            default -> {
                return 10;
            }
        }
    }

    @Override
    public int getLevel(ItemStack stack) {
        return stack.get(ModDataComponentTypes.CREATIVE_CIRCUIT_BOARD_LEVEL) != null ? stack.get(ModDataComponentTypes.CREATIVE_CIRCUIT_BOARD_LEVEL) : 10;
    }
}
