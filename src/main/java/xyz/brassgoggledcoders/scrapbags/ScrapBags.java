package xyz.brassgoggledcoders.scrapbags;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import xyz.brassgoggledcoders.scrapbags.content.ScrapBagItems;

@Mod(ScrapBags.MODID)
public class ScrapBags
{
    public static final String MODID = "scrapbags";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ScrapBags(IEventBus modEventBus, ModContainer modContainer) {
        ScrapBagItems.setup();
    }
}