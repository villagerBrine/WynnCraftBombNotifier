package villagerbrine.wynncraftbombnotifier.Chat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static villagerbrine.wynncraftbombnotifier.WynnCraftBombNotifier.getMinecraftServer;


public class MessageSenderListener {
    public static List<String> chatSendMessage = new ArrayList();
    public static String chatMessageStarter = "";
    private int tick = 0;
    public static boolean isModOn = true;
    public static int ticks = 0;

    public MessageSenderListener() {
    }

    /**
     *  runs chat messages and anti afk
     * @param event tick event
     */
    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public void tickEvent(TickEvent.ClientTickEvent event) {
        ticks += 1;
        if (chatSendMessage.size() != 0) {
            if (this.tick == 5) {
                this.tick = 0;
                Minecraft.getMinecraft().player.sendChatMessage(chatSendMessage.get(0));
                chatSendMessage.remove(0);
            } else {
                this.tick = this.tick + 1;
            }
        }
        if (ticks == 6000) {
            chatShowMessage(ChatFormatting.GREEN +"You are now" + ChatFormatting.AQUA + " AFK" + ChatFormatting.GREEN + "!");
        }
    }

    /**
     * gets the worldserver of wynncraft
     * @return world server on wynncraft
     */
    public String checkWorldServer() {
        Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getConnection().getPlayerInfoMap();
        for (NetworkPlayerInfo info : players) {
            if (info.getDisplayName() != null) {
                if (info.getDisplayName().getUnformattedText().contains("Global [WC")) {
                    return info.getDisplayName().getUnformattedText().replace("Global ", "");
                }
            }
        }
        return "World number not received by BombNotifier";
    }
    /**
    detects bombs and sends messages
     @Param ClientChatReceivedEvent
     */
    @SubscribeEvent
    public void detectBomb(ClientChatReceivedEvent event) {
        String message = event.getMessage().getUnformattedText();
        if (getMinecraftServer("wynncraft")) {
            if (!(ticks >= 6000)) {
                if (isModOn) {
                    message = StringUtils.stripControlCodes(message);
                    if (message.startsWith("[Bomb Bell]")) {
                        String[] splitMessage = message.split(" ", 3);
                        splitMessage[2] = splitMessage[2].replace("has thrown a ", "");
                        chatSendMessage.add("/g " + splitMessage[2]);
                    } else {
                        String[] splitMessage = message.split(" ", 2);
                        if (splitMessage.length > 1) {
                            if (splitMessage[1].startsWith("has thrown a")) {
                                String world = checkWorldServer();
                                world = StringUtils.stripControlCodes(world);
                                splitMessage[1] = splitMessage[1].replace("has thrown a", "");
                                String[] secondMessagesSplit = splitMessage[1].split(" ");
                                secondMessagesSplit[1] = StringUtils.stripControlCodes(secondMessagesSplit[1]);
                                chatSendMessage.add("/g " + secondMessagesSplit[1] + " in" + world);
                            }
                        }
                    }
                }
            }
        }

    }
    @SubscribeEvent
    public void setChatMessage(ClientChatEvent event) {
        if (!(event.getMessage().charAt(0) == '/')) {
            event.setMessage(chatMessageStarter + " " + event.getMessage());
        }
    }
    @SubscribeEvent
    public void antiAfk(MouseEvent event) {
        if (ticks > 6000) {
            chatShowMessage(ChatFormatting.GREEN +"You are now not" + ChatFormatting.AQUA + " AFK" + ChatFormatting.GREEN + "!");
        }
        ticks = 0;
    }
    public static void chatShowMessage(String string) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("[" + ChatFormatting.RED + "BombNotifier" + ChatFormatting.RESET + "]: " + string));
    }
    public static void chatModHiddenShowMessage(String string) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(string));
    }
}
