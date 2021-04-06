package me.xingyan.announce;

import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                if(config.getBoolean("discord.embed")){
                    EmbedBuilder embed = new EmbedBuilder();
                    String title = config.getString("message.title");
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm:ss a");
                    LocalDateTime time = LocalDateTime.now();
                    embed.setTitle(title);
                    embed.setDescription(msg);
                    String sendby = config.getString("message.sendby");
                    embed.setFooter(sendby+" "+sender.getName()+" | "+dtf.format(time));
                    embed.setColor(new Color(255,0,0));
                    channel.sendMessage(embed.build()).queue();
                }else{
                    DiscordUtil.sendMessage(channel, msg);
                }

            }
        }
        return true;
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }


}

