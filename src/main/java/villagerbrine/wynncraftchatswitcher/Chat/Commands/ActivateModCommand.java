package villagerbrine.wynncraftchatswitcher.Chat.Commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static villagerbrine.wynncraftchatswitcher.Chat.MessageSenderListener.*;
import static villagerbrine.wynncraftchatswitcher.WynnCraftBombNotifier.boolToStringMap;

public class ActivateModCommand implements ICommand {
    @Override
    public String getName() {
        return "activateMod";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "bombnotifier";
    }

    @Override
    public List<String> getAliases() {
        List<String> list = new ArrayList<>();
        list.add("bn");
        return list;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            if (args[0].toLowerCase().contains("true") && args[0].toLowerCase().contains("on")) {
                isModOn = true;
                chatShowMessage(ChatFormatting.GREEN + "You have toggled bomb notifications for your guild " +ChatFormatting.GOLD + "on" + ChatFormatting.GREEN + "!");
                return;
            } else if (args[0].toLowerCase().contains("false") && args[0].toLowerCase().contains("off")) {
                isModOn = false;
                chatShowMessage(ChatFormatting.GREEN + "You have toggled bomb notifications for your guild " +ChatFormatting.GOLD + "off" + ChatFormatting.GREEN + "!");
                return;
            }
        }
        isModOn = !isModOn;
        chatShowMessage(ChatFormatting.GREEN + "You have toggled bomb notifications for your guild " +ChatFormatting.GOLD + boolToStringMap.get(isModOn) + ChatFormatting.GREEN + "!");
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
