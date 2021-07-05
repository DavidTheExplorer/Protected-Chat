package dte.protectedchat.commands;

import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.YELLOW;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dte.protectedchat.protectors.ChatProtector;
import dte.protectedchat.service.ProtectionService;

public class ChatProtectCommand implements CommandExecutor
{
	private final ProtectionService protectionService;
	private final ChatProtector globalChatProtector;
	
	public ChatProtectCommand(ProtectionService protectionService, ChatProtector globalChatProtector) 
	{
		this.protectionService = protectionService;
		this.globalChatProtector = globalChatProtector;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(!(sender instanceof Player)) 
		{
			sender.sendMessage(RED + "Only players may execute this command.");
			return false;
		}
		Player player = (Player) sender;
		
		if(!player.hasPermission("protectedchat.use")) 
		{
			player.sendMessage(RED + "You don't have enough permissions to use this command.");
			return false;
		}
		
		switch(args.length)
		{
		case 0:
			if(this.protectionService.isProtected(player)) 
			{
				this.protectionService.disable(player);
				player.sendMessage(RED + "Your messages will now be sent to the global chat.");
			}
			else 
			{
				this.protectionService.protect(player, this.globalChatProtector);
				player.sendMessage(GRAY + "Your chat is now protected by " + GOLD + "Holograms" + GRAY + ".");
			}
			return true;
			
		case 2:
		{
			if(args[0].equalsIgnoreCase("info"))
			{
				Player target = Bukkit.getPlayer(args[1]);

				if(target == null)
				{
					player.sendMessage(DARK_RED + args[1] + RED + " is not online!");
					return false;
				}
				player.sendMessage(YELLOW + target.getName() + GRAY + "'s chat is " + getStatusColor(player) + getStatus(player) + GRAY + "!");
				return true;
			}
		}
		
		default:
			player.sendMessage(ChatColor.RED + "/chatprotect");
			player.sendMessage(ChatColor.RED + "/chatprotect info [player]");
			player.sendMessage(ChatColor.RED + "/chatprotect reload");
			return false;
		}
	}
	
	private ChatColor getStatusColor(Player player) 
	{
		return this.protectionService.isProtected(player) ? GOLD : RED;
	}

	private String getStatus(Player player) 
	{
		return this.protectionService.isProtected(player) ? "Protected" : "Unprotected";
	}
}