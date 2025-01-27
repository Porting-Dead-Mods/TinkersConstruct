package slimeknights.tconstruct.fluids.data;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import slimeknights.mantle.data.GenericDataProvider;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

/** Quick and dirty data provider to generate fluid bucket models */
public class FluidBucketModelProvider extends GenericDataProvider {
  private final String modId;
  public FluidBucketModelProvider(DataGenerator generator, String modId) {
    super(generator.getPackOutput(), PackOutput.Target.RESOURCE_PACK, "models/item");
    this.modId = modId;
  }

  /** Makes the JSON for a given bucket */
  private static JsonObject makeJson(BucketItem bucket) {
    JsonObject json = new JsonObject();
    json.addProperty("parent", "forge:item/bucket_drip");
    // using our own model as the forge one expects us to use item colors to handle tints, when we could just bake it in
    json.addProperty("loader", "tconstruct:fluid_container");
    json.addProperty("flip_gas", bucket.getFluid().getFluidType().isLighterThanAir());
    json.addProperty("fluid", BuiltInRegistries.FLUID.getKey(bucket.getFluid()).toString());
    return json;
  }

  @Override
  public CompletableFuture<?> run(CachedOutput cachedOutput) {
    // loop over all liquid blocks, adding a blockstate for them
    for (Entry<ResourceKey<Item>, Item> entry : BuiltInRegistries.ITEM.entrySet()) {
      ResourceLocation id = entry.getKey().location();
      if (id.getNamespace().equals(modId) && entry.getValue() instanceof BucketItem bucket) {
        saveJson(cachedOutput, id, makeJson(bucket));
      }
    }
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public String getName() {
    return modId + " Fluid Bucket Model Provider";
  }
}
