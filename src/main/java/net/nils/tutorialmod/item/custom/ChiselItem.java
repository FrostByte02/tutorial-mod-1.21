package net.nils.tutorialmod.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.block.Block;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {
    public static final Map<Block, Block> CHISEL_MAP =
                Map.ofEntries(
                    Map.entry(Blocks.STONE, Blocks.STONE_BRICKS),
                    Map.entry(Blocks.END_STONE, Blocks.END_STONE_BRICKS),
                    Map.entry(Blocks.NETHERRACK, Blocks.NETHER_BRICKS),
                    Map.entry(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_MOSAIC),
                    Map.entry(Blocks.STONE_SLAB, Blocks.STONE_BRICK_SLAB),
                    Map.entry(Blocks.STONE_STAIRS, Blocks.STONE_BRICK_STAIRS),
                    Map.entry(Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS),
                    Map.entry(Blocks.COPPER_BLOCK, Blocks.CHISELED_COPPER),
                    Map.entry(Blocks.CHISELED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS),
                    Map.entry(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_BRICKS),
                    Map.entry(Blocks.QUARTZ_BRICKS, Blocks.CHISELED_QUARTZ_BLOCK),
                    Map.entry(Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS),
                    Map.entry(Blocks.DEEPSLATE_BRICKS, Blocks.CHISELED_DEEPSLATE),
                    Map.entry(Blocks.CHISELED_DEEPSLATE, Blocks.CRACKED_DEEPSLATE_BRICKS),
                    Map.entry(Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS),
                    Map.entry(Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_BRICK_SLAB),
                    Map.entry(Blocks.PRISMARINE_STAIRS, Blocks.PRISMARINE_BRICK_STAIRS)
                );


    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if (CHISEL_MAP.containsKey(clickedBlock)) {
            if (!world.isClient) {
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS);

            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel.shift_down.l1"));
        }
        else {
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel.l1"));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}
