package net.lebaguette.actinium.datagen;

import net.lebaguette.actinium.Actinium;
import net.lebaguette.actinium.block.ModBlocks;
import net.lebaguette.actinium.fluid.BaseFluidType;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.LiquidBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Actinium.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.URANIUM_ORE);
        blockWithItem(ModBlocks.STEEL_BLOCK);

        simpleBlockWithItem(ModBlocks.PUMPJACK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/pumpjack")));

        fluidBlock(ModBlocks.OIL_BLOCK);
    }

    //makes that you can just pass in the RegistryObject and it makes the custom block and the item for it
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void fluidBlock(RegistryObject<LiquidBlock> block) {
        LiquidBlock liquidBlock = block.get();

        FluidType fluidType = liquidBlock.getFluid().getFluidType();

        if (fluidType instanceof BaseFluidType baseFluidType) {
            simpleBlock(
                    liquidBlock,
                    models().cubeAll(
                            block.getId().getPath(),
                            baseFluidType.getStillTexture()
                    )
            );
        }
    }
}
