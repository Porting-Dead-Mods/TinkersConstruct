package slimeknights.tconstruct.common.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.concurrent.CompletableFuture;

public class BlockEntityTypeTagProvider extends TagsProvider<BlockEntityType<?>> {

  @SuppressWarnings("deprecation")
  public BlockEntityTypeTagProvider(DataGenerator generatorIn, CompletableFuture<HolderLookup.Provider> pProvider) {
    super(generatorIn.getPackOutput(), Registries.BLOCK_ENTITY_TYPE, pProvider);
  }

  @Override
  public String getName() {
    return "Tinkers' Construct Block Entity Type Tags";
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    // TODO: This returns their specific block entity type but we need the general one
    /*
    this.tag(TinkerTags.TileEntityTypes.CRAFTING_STATION_BLACKLIST)
      .add(
        BlockEntityType.FURNACE, BlockEntityType.BLAST_FURNACE, BlockEntityType.SMOKER, BlockEntityType.BREWING_STAND,
        TinkerTables.craftingStationTile.get(), TinkerTables.tinkerStationTile.get(), TinkerTables.partBuilderTile.get(),
        TinkerTables.partChestTile.get(), TinkerTables.tinkersChestTile.get(), TinkerTables.castChestTile.get(),
        TinkerSmeltery.basin.get(), TinkerSmeltery.table.get(),
        TinkerSmeltery.melter.get(), TinkerSmeltery.smeltery.get(), TinkerSmeltery.foundry.get()
      );
   */
  }
}

