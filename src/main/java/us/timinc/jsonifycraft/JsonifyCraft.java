package us.timinc.jsonifycraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import us.timinc.jsonifycraft.description.BlockDescription;
import us.timinc.jsonifycraft.description.FoodDescription;
import us.timinc.jsonifycraft.description.ItemDescription;
import us.timinc.jsonifycraft.deserializers.GameDeserializer;

@Mod(modid = JsonifyCraft.MODID, name = JsonifyCraft.NAME, version = JsonifyCraft.VERSION)
public class JsonifyCraft {
    public static final String MODID = "jsonifycraft";
    public static final String NAME = "JsonifyCraft";
    public static final String VERSION = "1.2.1";

    private static final Logger LOGGER = LogManager.getLogger(NAME);

    private static DescriptionLoader GAME_OBJECTS;

    public JsonifyCraft() {
        setup();
    }

    public void setup() {
        log("==Setup Start==");
        registerDeserializers();
        loadGameObjects();
        log("==Setup End==");
    }

    public void registerDeserializers() {
        log("--Registering deserializers--");
        GameDeserializer.registerDescription("item", ItemDescription.class);
        GameDeserializer.registerDescription("block", BlockDescription.class);
        GameDeserializer.registerDescription("food", FoodDescription.class);
    }

    private void loadGameObjects() {
        log("--Loading game objects--");
        GAME_OBJECTS = new DescriptionLoader();
    }

    public static void log(String message, Object... variables) {
        LOGGER.info(String.format(message, variables));
    }

    @Mod.EventBusSubscriber
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            GAME_OBJECTS.registerItems(itemRegistryEvent.getRegistry());
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            GAME_OBJECTS.registerBlocks(blockRegistryEvent.getRegistry());
        }
    }
}
