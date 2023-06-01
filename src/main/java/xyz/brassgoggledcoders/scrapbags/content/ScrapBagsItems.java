package xyz.brassgoggledcoders.scrapbags.content;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import xyz.brassgoggledcoders.scrapbags.ScrapBags;
import xyz.brassgoggledcoders.scrapbags.item.ScrapBagItem;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScrapBagsItems {

    @SuppressWarnings("unused")
    public static Set<ItemEntry<ScrapBagItem>> SCRAP_BAGS = Stream.of("Blue", "Green", "Grey", "Purple", "Red", "Yellow")
            .map(color -> ScrapBags.getRegistrate()
                    .object("scrap_bag_" + color.toLowerCase(Locale.US))
                    .item(ScrapBagItem::new)
                    .tab(() -> CreativeModeTab.TAB_MISC)
                    .lang(color + " Scrap Bag")
                    .addMiscData(ProviderType.LOOT, provider -> provider.addLootAction(
                            LootContextParamSets.CHEST,
                            consumer -> consumer.accept(
                                    new ResourceLocation(ScrapBags.MODID, "scrap_bag_" + color.toLowerCase(Locale.US)),
                                    LootTable.lootTable()
                                            .withPool(LootPool.lootPool()
                                                    .add(LootTableReference.lootTableReference(BuiltInLootTables.SIMPLE_DUNGEON))
                                            )
                            )
                    ))
                    .register()
            )
            .collect(Collectors.toUnmodifiableSet());

    public static void setup() {

    }
}
