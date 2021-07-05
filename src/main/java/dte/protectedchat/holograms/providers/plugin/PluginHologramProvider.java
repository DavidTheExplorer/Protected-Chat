package dte.protectedchat.holograms.providers.plugin;

import org.bukkit.Bukkit;

import dte.protectedchat.holograms.providers.ChatHologramProvider;

public abstract class PluginHologramProvider implements ChatHologramProvider
{
	private final String pluginName;
	
	protected PluginHologramProvider(String pluginName) 
	{
		this.pluginName = pluginName;
	}
	
	@Override
	public String getName() 
	{
		return this.pluginName;
	}
	
	@Override
	public boolean isAvailable() 
	{
		return Bukkit.getPluginManager().isPluginEnabled(this.pluginName);
	}
}