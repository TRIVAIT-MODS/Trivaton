package org.trivait.trivaton.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CircuitBoardItem extends Item {
    private final int level;

    public CircuitBoardItem(Settings settings, int level) {
        super(settings);
        this.level = level;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xFF0094FF;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Text text = Text.translatable("item.trivaton.circuit_board.desc").setStyle(Style.EMPTY.withColor(Formatting.GRAY)).append(Text.literal(""+level).setStyle(Style.EMPTY.withColor(Formatting.AQUA)));

        tooltip.add(text);

        int maxDamage = stack.getMaxDamage();
        int currentDamage = stack.getDamage();
        int currentDurability = maxDamage - currentDamage;

        tooltip.add(Text.translatable("item.trivaton.circuit_board.desc2")
                .append(Text.literal(String.valueOf(currentDurability)).formatted(Formatting.GREEN))
                .append(" / ")
                .append(Text.literal(String.valueOf(maxDamage)).formatted(Formatting.GRAY))
                .formatted(Formatting.GRAY));

        super.appendTooltip(stack, context, tooltip, type);
    }

    public int getLevel() {
        int maxDamage = new ItemStack(this).getMaxDamage();
        int currentDamage = new ItemStack(this).getDamage();
        int currentDurability = maxDamage - currentDamage;

        return currentDurability != 0 ? level : 0;
    }
}
