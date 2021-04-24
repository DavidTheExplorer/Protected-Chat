package dte.protectedchat.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ModernJavaPlugin extends JavaPlugin
{
	private final String PLUGIN_NAME = getDescription().getName();
	
	public void logToConsole(String message) 
	{
		Bukkit.getConsoleSender().sendMessage(String.format("[%s] %s", this.PLUGIN_NAME, message));
	}
	public void registerListeners(Listener... listeners)
	{
		for(Listener listener : listeners)
			Bukkit.getPluginManager().registerEvents(listener, this);
	}
	
	protected void shutdownFor(String reason) 
	{
		logToConsole(reason);
		logToConsole(ChatColor.RED + "Shutting Down!");
		
		Bukkit.getPluginManager().disablePlugin(this);
	}
}