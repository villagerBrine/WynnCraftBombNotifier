package villagerbrine.wynncraftchatswitcher;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import villagerbrine.wynncraftchatswitcher.Chat.Commands.ActivateModCommand;
import villagerbrine.wynncraftchatswitcher.Chat.MessageSenderListener;

import java.util.HashMap;

@Mod(
        modid = WynnCraftBombNotifier.MOD_ID,
        name = WynnCraftBombNotifier.MOD_NAME,
        version = WynnCraftBombNotifier.VERSION
)
public class WynnCraftBombNotifier {

    public static final String MOD_ID = "wynncraftbombguildnotifier";
    public static final String MOD_NAME = "WynnCraftBombGuildNotifier";
    public static final String VERSION = "1.0";
    public static HashMap<Boolean, String> boolToStringMap = new HashMap<>();
    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static WynnCraftBombNotifier INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        boolToStringMap.put(true, "on");
        boolToStringMap.put(false, "off");
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new ActivateModCommand());
        MinecraftForge.EVENT_BUS.register(new MessageSenderListener());
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
    public static boolean getMinecraftServer(String ip) {
        if (Minecraft.getMinecraft().getCurrentServerData() != null) {
            return Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains(ip);
        }
        return false;
    }

}
