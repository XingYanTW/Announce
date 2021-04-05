package me.xingyan.announce;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.listeners.DiscordChatListener;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static me.xingyan.announce.Main.config;
import static me.xingyan.announce.Main.plugin;

public class cmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0){
            sender.sendMessage("Usage: /announce <text>");
        }else{
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < args.length; i++) {
                builder.append(args[i] + " ");
            }
            String msg = builder.toString();
            config.set("announce", msg);
            config.options().copyDefaults(true);
            plugin.saveConfig();
            String prefix = config.getString("message.prefix");
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + msg));

            boolean discord = config.getBoolean("discord.enable");
            if(discord){
                String channelid = config.getString("discord.channel");
                TextChannel channel = DiscordUtil.getTextChannelById(channelid);
                DiscordUtil.sendMessage(channel, msg);
            }
        }
        return true;
    }

}

