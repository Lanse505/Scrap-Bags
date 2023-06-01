package xyz.brassgoggledcoders.scrapbags;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.brassgoggledcoders.scrapbags.content.ScrapBagsItems;

@Mod(ScrapBags.MODID)
public class ScrapBags {

    public static final String MODID = "scrapbags";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    private static final Lazy<Registrate> REGISTRATE_LAZY = Lazy.of(() -> Registrate.create(MODID));

    public ScrapBags() {
        ScrapBagsItems.setup();
    }

    public static Registrate getRegistrate() {
        return REGISTRATE_LAZY.get();
    }
}
