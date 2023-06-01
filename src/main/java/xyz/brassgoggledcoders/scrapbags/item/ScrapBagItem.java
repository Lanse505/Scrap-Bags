package xyz.brassgoggledcoders.scrapbags.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.scrapbags.ScrapBags;
import xyz.brassgoggledcoders.scrapbags.content.ScrapBagsItems;

import javax.annotation.ParametersAreNonnullByDefault;

public class ScrapBagItem extends Item {
    public ScrapBagItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel) {
            ResourceLocation itemName = ForgeRegistries.ITEMS.getKey(this);

            if (itemName != null) {
                LootTable lootTable = serverLevel.getServer()
                        .getLootTables()
                        .get(itemName);

                if (lootTable != LootTable.EMPTY) {
                    LootContext lootContext = new LootContext.Builder(serverLevel)
                            .withParameter(LootContextParams.THIS_ENTITY, pPlayer)
                            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pPlayer.getOnPos()))
                            .withLuck(pPlayer.getLuck())
                            .create(LootContextParamSets.CHEST);

                    lootTable.getRandomItems(lootContext)
                            .forEach(pPlayer.getInventory()::placeItemBackInInventory);
                } else {
                    ScrapBags.LOGGER.error("Failed to find LootTable: {}", itemName);
                }
            } else {
                ScrapBags.LOGGER.error("Scrap Bag is not registered");
            }
        }

        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pPlayer.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }
}
