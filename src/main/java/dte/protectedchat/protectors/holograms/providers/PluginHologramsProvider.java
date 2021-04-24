package dte.protectedchat.protectors.holograms.providers;

import org.bukkit.Bukkit;

public abstract class PluginHologramsProvider implements HologramsProvider
{
	private final String pluginName;
	
	protected PluginHologramsProvider(String pluginName) 
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