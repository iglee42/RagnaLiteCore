package fr.iglee42.ragnalitecore;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.common.items.vehicles.RocketItem;
import fr.iglee42.ragnalitecore.rift.RiftingBlock;
import fr.iglee42.ragnalitecore.rift.RiftingBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class RLCBlocks {

    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, RagnaLiteCore.MODID);
    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITIES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, RagnaLiteCore.MODID);

    public static final RegistryEntry<RiftingBlock> RIFTING_BLOCK = BLOCKS.register("rifting_block", () -> new RiftingBlock(Block.Properties.copy(Blocks.BEACON)));
    public static final RegistryEntry<BlockItem> RIFTING_BLOCK_ITEM = RLCItems.ITEMS.register("rifting_block", () -> new BlockItem(RIFTING_BLOCK.get(), new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryEntry<BlockEntityType<RiftingBlockEntity>> RIFTING_BLOCK_ENTITY = BLOCK_ENTITIES.register("rifting_block", () -> BlockEntityType.Builder.of(RiftingBlockEntity::new, RIFTING_BLOCK.get()).build(null));


}
