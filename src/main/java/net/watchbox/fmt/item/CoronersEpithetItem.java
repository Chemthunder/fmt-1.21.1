package net.watchbox.fmt.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.CustomHitParticleItem;
import net.acoyt.acornlib.api.item.KillEffectItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.watchbox.fmt.index.FmtDataComponents;
import net.watchbox.fmt.index.FmtSounds;

import java.util.List;

public class CoronersEpithetItem extends Item implements CustomHitParticleItem, ColorableItem, KillEffectItem {
    public int startColor(ItemStack itemStack) {return 0xFF08060a;}
    public int endColor(ItemStack itemStack) {return 0xFF342b38;}
    public int backgroundColor(ItemStack itemStack) {return 0xF008060a;}

    public CoronersEpithetItem(Settings settings) {
        super(settings);
    }

    public static final SweepParticleEffect[] EFFECTS = new SweepParticleEffect[]{
            new SweepParticleEffect(0x2b1f2e, 0x161018),
            new SweepParticleEffect(0x16111a, 0x0f0c12)
    };

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 8.5F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.9F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    public void spawnHitParticles(PlayerEntity player) {
        ParticleUtils.spawnSweepParticles(EFFECTS[player.getRandom().nextInt(EFFECTS.length)], player);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {

        if (clickType == ClickType.RIGHT) {
            boolean bl = stack.getOrDefault(FmtDataComponents.EPITHET_TWO_HANDED, true);
            stack.set(FmtDataComponents.EPITHET_TWO_HANDED, !bl);
            if (player.getWorld().isClient) {
                player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 1);
            }
            return true;
        }
        return false;
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var component = FmtDataComponents.EPITHET_TWO_HANDED;

        if (stack.getOrDefault(component, false) == true) {
            tooltip.add(Text.literal("✔ Two-Handed").formatted(Formatting.DARK_GREEN));
        } else {
            tooltip.add(Text.literal("× Two-Handed").formatted(Formatting.DARK_RED));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    public void killEntity(World world, ItemStack stack, LivingEntity user, LivingEntity victim) {
        user.playSound(FmtSounds.EPITHET_EXECUTE, 1, 1);
    }
}
