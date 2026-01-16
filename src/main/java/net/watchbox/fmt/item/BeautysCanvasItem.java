package net.watchbox.fmt.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.CustomHitParticleItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.watchbox.fmt.cca.entity.ImmobilizedEntityComponent;
import net.watchbox.fmt.index.FmtDataComponents;
import net.watchbox.fmt.index.FmtSounds;

import java.util.List;

public class BeautysCanvasItem extends Item implements ColorableItem, CustomHitParticleItem {
    public int startColor(ItemStack itemStack) {
        return 0xFFfeffde;
    }
    public int endColor(ItemStack itemStack) {
        return 0xFFa85144;
    }
    public int backgroundColor(ItemStack itemStack) {
        return 0xF0260b14;
    }

    public BeautysCanvasItem(Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 8.0F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.6F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {



        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        var component = FmtDataComponents.CANVAS_PETALS;
        if (stack.getOrDefault(component, 0) > 0) {
            stack.set(component, stack.getOrDefault(component, 0) - 1);
            ability(user.getWorld(), user, entity);
            user.getItemCooldownManager().set(stack.getItem(), 100);
        }

        return super.useOnEntity(stack, user, entity, hand);
    }

    private void ability(World world, PlayerEntity user, LivingEntity living) {
        ImmobilizedEntityComponent component = ImmobilizedEntityComponent.KEY.get(living);

        component.immobilizedTicks = 200;
        component.sync();

        user.playSound(FmtSounds.NOCLIP);
    }

    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) stack.getOrDefault(FmtDataComponents.CANVAS_PETALS, 0) / 2 * 13);
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    public int getItemBarColor(ItemStack stack) {
        return 0x91333f;
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("lore.canvas").withColor(0xa85144).append(Text.literal("Ethos").formatted(Formatting.OBFUSCATED)));

        super.appendTooltip(stack, context, tooltip, type);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public static final SweepParticleEffect[] EFFECTS = new SweepParticleEffect[]{
            new SweepParticleEffect(0x693627, 0x3a151b),
            new SweepParticleEffect(0x463439, 0x2a121a)
    };

    public void spawnHitParticles(PlayerEntity player) {
        ParticleUtils.spawnSweepParticles(EFFECTS[player.getRandom().nextInt(EFFECTS.length)], player);
    }
}
