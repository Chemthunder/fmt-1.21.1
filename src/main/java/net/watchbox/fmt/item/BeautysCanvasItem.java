package net.watchbox.fmt.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.watchbox.fmt.index.FmtDataComponents;

import javax.swing.*;

public class BeautysCanvasItem extends Item implements ColorableItem {
    public int startColor(ItemStack itemStack) {
        return 0xFF5c0024;
    }
    public int endColor(ItemStack itemStack) {
        return 0xFFff3382;
    }
    public int backgroundColor(ItemStack itemStack) {
        return 0xF0210812;
    }



    public BeautysCanvasItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        var component = FmtDataComponents.CANVAS_PETALS;


        if (stack.getOrDefault(component, 0) > 0) {
            stack.set(component, stack.getOrDefault(component, 0) - 1);

            user.getItemCooldownManager().set(stack.getItem(), 200);
        }


        return super.use(world, user, hand);
    }

    private void ability(World world, PlayerEntity user) {

    }

    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) stack.getOrDefault(FmtDataComponents.CANVAS_PETALS, 0) / 6 * 13);
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    public int getItemBarColor(ItemStack stack) {
        return 0xa62b5c;
    }
}
