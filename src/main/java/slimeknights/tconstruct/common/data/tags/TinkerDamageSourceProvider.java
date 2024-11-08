package slimeknights.tconstruct.common.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import slimeknights.tconstruct.shared.TinkerDamageTypes;

import java.util.concurrent.CompletableFuture;

public class TinkerDamageSourceProvider extends TagsProvider<DamageType> {

  public TinkerDamageSourceProvider(DataGenerator pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider) {
    super(pOutput.getPackOutput(), Registries.DAMAGE_TYPE, pLookupProvider);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    tag(DamageTypeTags.BYPASSES_ARMOR).add(TinkerDamageTypes.SELF_DESTRUCT).add(TinkerDamageTypes.PLAYER_ATTACK_BYPASS_ARMOR).add(TinkerDamageTypes.MOB_ATTACK_BYPASS_ARMOR);
    tag(DamageTypeTags.IS_EXPLOSION).add(TinkerDamageTypes.SELF_DESTRUCT).add(TinkerDamageTypes.PLAYER_ATTACK_EXPLOSION).add(TinkerDamageTypes.MOB_ATTACK_EXPLOSION);
    tag(DamageTypeTags.BYPASSES_EFFECTS).add(TinkerDamageTypes.BLEEDING).add(TinkerDamageTypes.PLAYER_ATTACK_MAGIC).add(TinkerDamageTypes.MOB_ATTACK_MAGIC);
    tag(DamageTypeTags.IS_FIRE).add(TinkerDamageTypes.SMELTERY_DAMAGE).add(TinkerDamageTypes.PLAYER_ATTACK_FIRE).add(TinkerDamageTypes.MOB_ATTACK_FIRE);
  }
}
