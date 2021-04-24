package dte.protectedchat.protectors.holograms.providers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import dte.protectedchat.ProtectedChat;
import dte.protectedchat.holograms.MessagesHDHologram;
import dte.protectedchat.holograms.MessagesHologram;

public class HolographicDisplaysProvider extends PluginHologramsProvider
{
	public HolographicDisplaysProvider()
	{
		super("HolographicDisplays");
	}

	@Override
	public MessagesHologram createHologram(Player owner, Location spawnLocation) 
	{
		return new MessagesHDHologram(owner, HologramsAPI.createHologram(ProtectedChat.getInstance(), spawnLocation));
	}
}