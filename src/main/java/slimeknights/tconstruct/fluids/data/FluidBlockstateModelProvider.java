package slimeknights.tconstruct.fluids.data;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import slimeknights.mantle.data.GenericDataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/** Quick and dirty data provider to generate blockstate files for fluids */
public class FluidBlockstateModelProvider extends GenericDataProvider {
  private final String modId;
  public FluidBlockstateModelProvider(DataGenerator generator, String modId) {
    super(generator, PackOutput.Target.RESOURCE_PACK, "blockstates");
    this.modId = modId;
  }

  @Override
  public CompletableFuture<?> run(CachedOutput cachedOutput) {
    List<CompletableFuture<?>> futures = new ArrayList<>();

    // Statically created JSON to reference block/fluid, acting as a dummy model
    JsonObject normal = new JsonObject();
    normal.addProperty("model", "tconstruct:block/fluid");
    JsonObject variants = new JsonObject();
    variants.add("", normal);
    JsonObject blockstate = new JsonObject();
    blockstate.add("variants", variants);

    // Loop over all liquid blocks, adding a blockstate for them asynchronously
    for (Entry<ResourceKey<Block>, Block> entry : BuiltInRegistries.BLOCK.entrySet()) {
      ResourceLocation id = entry.getKey().location();
      if (id.getNamespace().equals(modId) && entry.getValue() instanceof LiquidBlock) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
          saveJson(cachedOutput, id, blockstate);
        });
        futures.add(future);
      }
    }

    // Combine all asynchronous save tasks and propagate any exceptions that may occur
    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
  }


  @Override
  public String getName() {
    return modId + " Fluid Blockstate Model Provider";
  }
}
