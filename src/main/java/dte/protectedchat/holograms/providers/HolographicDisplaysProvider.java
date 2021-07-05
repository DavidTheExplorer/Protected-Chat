package dte.protectedchat.holograms.providers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import dte.protectedchat.ProtectedChat;
import dte.protectedchat.holograms.ChatHologram;
import dte.protectedchat.holograms.HDChatHologram;
import dte.protectedchat.holograms.providers.plugin.PluginHologramProvider;

public class HolographicDisplaysProvider extends PluginHologramProvider
{
	private static final ProtectedChat PROTECTED_CHAT = ProtectedChat.getInstance();
	
	public HolographicDisplaysProvider()
	{
		super("HolographicDisplays");
	}

	@Override
	public ChatHologram createHologram(Player owner, Location spawnLocation) 
	{
		return new HDChatHologram(owner, HologramsAPI.createHologram(PROTECTED_CHAT, spawnLocation));
	}
}