package net.nils.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nils.tutorialmod.item.ModItems;

import java.util.List;

public class MagicBlock extends Block {
    public MagicBlock(Settings settings) {
        super(settings);
    }

    public void distributeParticles(World world, BlockPos pos, int increments) {
        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY() + 0.75;
        double centerZ = pos.getZ() + 0.5;
        for (int i = 0; i < 360; i += increments) { // distribute particles every 10 degrees
            double rad = Math.toRadians(i);
            double dx = Math.cos(rad);
            double dz = Math.sin(rad);
            world.addParticle(ParticleTypes.GLOW, centerX, centerY, centerZ, dx, 1, dz);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 5f, 1f);
        distributeParticles(world, pos, 35);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof ItemEntity item) {
            if (item.getStack().getItem() == ModItems.RAW_PINK_GARNET) {
                item.setStack(new ItemStack(ModItems.PINK_GARNET, item.getStack().getCount() * 2));
                world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.BLOCKS, 1f, 1f);
                distributeParticles(world, pos, 10);
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.tutorialmod.magic_block.l1"));
        tooltip.add(Text.translatable("tooltip.tutorialmod.magic_block.l2"));
        tooltip.add(Text.translatable("tooltip.tutorialmod.magic_block.l3"));
        tooltip.add(Text.translatable("tooltip.tutorialmod.magic_block.l4"));
        super.appendTooltip(stack, context, tooltip, options);
    }
}
