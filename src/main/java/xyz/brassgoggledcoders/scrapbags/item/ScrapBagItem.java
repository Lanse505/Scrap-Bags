package xyz.brassgoggledcoders.scrapbags.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import xyz.brassgoggledcoders.scrapbags.ScrapBags;

public class ScrapBagItem extends Item {

    public ScrapBagItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel) {
            ResourceLocation itemName = BuiltInRegistries.ITEM.getKey(this);
            if (!itemName.getPath().equals("empty")) {
                LootTable lootTable = serverLevel.getServer()
                        .getLootData()
                        .getLootTable(itemName);

                if (lootTable != LootTable.EMPTY) {
                    LootParams params = new LootParams.Builder(serverLevel)
                            .withParameter(LootContextParams.THIS_ENTITY, pPlayer)
                            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pPlayer.getOnPos()))
                            .withLuck(pPlayer.getLuck())
                            .create(LootContextParamSets.CHEST);

                    lootTable.getRandomItems(params).forEach(pPlayer.getInventory()::placeItemBackInInventory);
                } else {
                    ScrapBags.LOGGER.error("Failed to find LootTable: {}", itemName);
                }
            }
        }
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pPlayer.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }
}