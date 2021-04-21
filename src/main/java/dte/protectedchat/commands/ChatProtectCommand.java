package dte.protectedchat.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dte.protectedchat.protectors.ChatProtector;
import dte.protectedchat.registry.ProtectionRegistry;

public class ChatProtectCommand implements CommandExecutor
{
	private final ProtectionRegistry protectionRegistry;
	private final ChatProtector globalChatProtector;

	public ChatProtectCommand(ProtectionRegistry protectionRegistry, ChatProtector globalChatProtector) 
	{
		this.protectionRegistry = protectionRegistry;
		this.globalChatProtector = globalChatProtector;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(!(sender instanceof Player)) 
		{
			sender.sendMessage(ChatColor.RED + "Only players may execute this command.");
			return false;
		}
		Player player = (Player) sender;
		
		if(!player.hasPermission("protectedchat.use")) 
		{
			player.sendMessage(ChatColor.RED + "You don't have enough permissions to use this command.");
			return false;
		}
		
		switch(args.length)
		{
		case 0:
			if(this.protectionRegistry.isProtected(player))
				this.protectionRegistry.disable(player);
			else 
				this.protectionRegistry.protectWith(player, this.globalChatProtector);
			
			player.sendMessage(getStatusColor(player) + getStatusVerb(player) + " your chat.");
			return true;
		case 2:
		{
			if(args[0].equalsIgnoreCase("info"))
			{
				Player target = Bukkit.getPlayer(args[1]);

				if(target == null) 
				{
					player.sendMessage(ChatColor.DARK_RED + args[1] + ChatColor.RED + " is not online!");
					return false;
				}
				player.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.GRAY + "'s chat is " + getStatusColor(player) + getStatusVerb(player) + ChatColor.GRAY + "!");
				return true;
			}
		}
		default:
			player.sendMessage(ChatColor.RED + "/chatprotect");
			player.sendMessage(ChatColor.RED + "/chatprotect info [player]");
			return false;
		}
	}

	private ChatColor getStatusColor(Player player) 
	{
		return this.protectionRegistry.isProtected(player) ? ChatColor.GOLD : ChatColor.RED;
	}

	private String getStatusVerb(Player player) 
	{
		return this.protectionRegistry.isProtected(player) ? "Protected" : "Unprotected";
	}
}