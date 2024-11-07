package slimeknights.tconstruct.common.data.loot;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
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

  private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> lootTables = ImmutableList.of(
    Pair.of((Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>) new BlockLootTableProvider(Collections.emptySet(), FeatureFlags.VANILLA_SET), LootContextParamSets.BLOCK),
    Pair.of((Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>) new AdvancementLootTableProvider(), LootContextParamSets.ADVANCEMENT_REWARD),
    Pair.of((Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>) new EntityLootTableProvider(FeatureFlagSet.of()), LootContextParamSets.ENTITY)
  );

  public TConstructLootTableProvider(PackOutput pOutput, Set<ResourceLocation> pRequiredTables, List<SubProviderEntry> pSubProviders) {
    super(pOutput, pRequiredTables, pSubProviders);
  }


  @Override
  protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
    return lootTables;
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
