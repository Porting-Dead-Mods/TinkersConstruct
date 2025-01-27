package slimeknights.tconstruct.fluids.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

/** Liquid block setting the entity on fire */
public class MobEffectLiquidBlock extends LiquidBlock {
  private final Supplier<MobEffectInstance> effect;
  public MobEffectLiquidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties, Supplier<MobEffectInstance> effect) {
    super(supplier, properties);
    this.effect = effect;
  }

  @Override
  public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
    if (entity.getFluidTypeHeight(getFluid().getFluidType()) > 0 && entity instanceof LivingEntity living) {
      MobEffectInstance effect = this.effect.get();
      effect.setCurativeItems(new ArrayList<>());
      living.addEffect(effect);
    }
  }

  //It's water
  public static Function<Supplier<? extends FlowingFluid>, LiquidBlock> createEffect(int lightLevel, Supplier<MobEffectInstance> effect) {
    return fluid -> new MobEffectLiquidBlock(fluid, Properties.of().mapColor(MapColor.WATER).replaceable().liquid().pushReaction(PushReaction.DESTROY).lightLevel(state -> lightLevel).noCollission().strength(100f).noLootTable(),effect);
  }
 //It's lava
  public static Function<Supplier<? extends FlowingFluid>, LiquidBlock> createEffectLava(int lightLevel, Supplier<MobEffectInstance> effect) {
    return fluid -> new MobEffectLiquidBlock(fluid, Properties.of().mapColor(MapColor.FIRE).replaceable().liquid().pushReaction(PushReaction.DESTROY).lightLevel(state -> lightLevel).noCollission().strength(100f).noLootTable(),effect);
  }
}
