package dte.protectedchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import dte.protectedchat.commands.ChatProtectCommand;
import dte.protectedchat.holograms.displayer.SimpleHologramsDisplayer;
import dte.protectedchat.listeners.ChatProtectionDisableListener;
import dte.protectedchat.listeners.ChatProtectionListener;
import dte.protectedchat.protectors.HologramChatProtector;
import dte.protectedchat.registry.ProtectionRegistry;
import dte.protectedchat.registry.SimpleProtectionRegistry;
import dte.protectedchat.utils.ModernJavaPlugin;

public class ProtectedChat extends ModernJavaPlugin
{
	private ProtectionRegistry protectionRegistry;
	private HologramChatProtector globalChatProtector;
	
	private static ProtectedChat INSTANCE;
	
	@Override
	public void onEnable()
	{
		if(!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) 
		{
			logToConsole(ChatColor.RED + "HolographicsDisplays is either Missing or Disabled.");
			logToConsole(ChatColor.RED + "Shutting Down!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		INSTANCE = this;
		
		saveDefaultConfig();
		
		this.protectionRegistry = new SimpleProtectionRegistry();
		this.globalChatProtector = new HologramChatProtector(this.protectionRegistry, new SimpleHologramsDisplayer());
		
		registerListeners();
		registerCommands();
	}
	public static ProtectedChat getInstance()
	{
		return INSTANCE;
	}
	private void registerCommands() 
	{
		getCommand("chatprotect").setExecutor(new ChatProtectCommand(this.protectionRegistry, this.globalChatProtector));
	}
	private void registerListeners()
	{
		registerListeners(
				new ChatProtectionListener(this.protectionRegistry),
				new ChatProtectionDisableListener(this.protectionRegistry));
	}
}