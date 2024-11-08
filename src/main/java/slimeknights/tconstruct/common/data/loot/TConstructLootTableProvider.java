package slimeknights.tconstruct.common.data.loot;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import slimeknights.tconstruct.TConstruct;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TConstructLootTableProvider extends LootTableProvider {

  private LootTableProvider x;

  public TConstructLootTableProvider(DataGenerator pOutput) {
    super(pOutput.getPackOutput(), Set.of(), List.of(
      new SubProviderEntry(BlockLootTableProvider::new, LootContextParamSets.BLOCK),
      new SubProviderEntry(AdvancementLootTableProvider::new, LootContextParamSets.ADVANCEMENT_REWARD),
      new SubProviderEntry(EntityLootTableProvider::new, LootContextParamSets.ENTITY)
    ));
  }

  @Override
  protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
    map.forEach((loc, table) -> {
      LootTable lootTable = map.get(loc);
      lootTable.validate(validationtracker);
    });
    // Remove vanilla's tables, which we also loaded so we can redirect stuff to them.
    // This ensures the remaining generator logic doesn't write those to files.
    map.keySet().removeIf((loc) -> !loc.getNamespace().equals(TConstruct.MOD_ID));
  }

  /**
   * Gets a name for this provider, to use in logging.
   */
  // TODO: Fix this
  /*
  @Override
  public String getName() {
    return "TConstruct LootTables";
  }
   */
}
