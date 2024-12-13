package net.nils.tutorialmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.nils.tutorialmod.block.ModBlocks;
import net.nils.tutorialmod.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        List<ItemConvertible> FIRE_INFUSED_STAR_SMELTING = List.of(
                Items.NETHER_STAR
        );

        offerSmelting(recipeExporter, FIRE_INFUSED_STAR_SMELTING, RecipeCategory.MISC, ModItems.FIRE_INFUSED_STAR, 20000.0f, 36000, "fire_infused_star");
        offerBlasting(recipeExporter, FIRE_INFUSED_STAR_SMELTING, RecipeCategory.MISC, ModItems.FIRE_INFUSED_STAR, 15000.0f, 25000, "fire_infused_star");

        offerReversibleCompactingRecipes(recipeExporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PINK_GARNET, RecipeCategory.DECORATIONS, ModBlocks.PINK_GARNET_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CHISEL)
                .pattern("  G")
                .pattern(" S ")
                .pattern("S  ")
                .input('G', ModItems.PINK_GARNET)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PINK_GARNET), conditionsFromItem(ModItems.PINK_GARNET))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FIRE_INFUSED_STAR)
                .pattern("EBE")
                .pattern("BNB")
                .pattern("EBE")
                .input('E', Items.ENDER_EYE)
                .input('B', Items.BLAZE_POWDER)
                .input('N', Items.NETHER_STAR)
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MAGIC_BLOCK)
                .pattern("DDD")
                .pattern("DFD")
                .pattern("DDD")
                .input('D', Items.DIAMOND)
                .input('F', ModItems.FIRE_INFUSED_STAR)
                .criterion(hasItem(ModItems.FIRE_INFUSED_STAR), conditionsFromItem(ModItems.FIRE_INFUSED_STAR))
                .offerTo(recipeExporter);

    }
}
