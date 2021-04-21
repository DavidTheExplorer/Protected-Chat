package dte.protectedchat.protectors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import dte.protectedchat.ProtectedChat;
import dte.protectedchat.exceptions.HologramsProviderException;
import dte.protectedchat.holograms.MessagesHologram;
import dte.protectedchat.holograms.displayer.HologramsDisplayer;
import dte.protectedchat.holograms.implementations.MessagesHDHologram;
import dte.protectedchat.holograms.tasks.HologramsFollowTask;
import dte.protectedchat.registry.ProtectionRegistry;

public class HologramChatProtector implements ChatProtector
{
	private final Map<Player, MessagesHologram> playersHologram = new HashMap<>();
	private final HologramsDisplayer hologramsDisplayer;

	private static final HologramsProvider CONFIG_PROVIDER = parseProviderFromConfig();
	
	public HologramChatProtector(ProtectionRegistry protectionRegistry, HologramsDisplayer hologramsDisplayer) 
	{
		this.hologramsDisplayer = hologramsDisplayer;
		
		new HologramsFollowTask(protectionRegistry, hologramsDisplayer).runTaskTimer(ProtectedChat.getInstance(), 0, 5);
	}

	@Override
	public void onChat(Player protectedPlayer, String message)
	{
		MessagesHologram playerHologram = this.playersHologram.computeIfAbsent(protectedPlayer, this::createHologramFor);
		playerHologram.addMessage(message);
		
		this.hologramsDisplayer.refreshFor(protectedPlayer, playerHologram);
	}

	@Override
	public void disable(Player protectedPlayer)
	{
		MessagesHologram playerHologram = this.playersHologram.remove(protectedPlayer);
		
		//the player might not have spoken, so they don't have an hologram
		if(playerHologram != null)
			playerHologram.delete();
	}

	public Optional<MessagesHologram> getHologramOf(Player owner)
	{
		return Optional.ofNullable(this.playersHologram.get(owner));
	}

	private MessagesHologram createHologramFor(Player owner)
	{
		MessagesHologram playerHologram = CONFIG_PROVIDER.createHologram(owner, owner.getLocation());
		
		//remove the hologram after 5 seconds TODO: (make it based on the message's length)
		Bukkit.getScheduler().runTaskLater(ProtectedChat.getInstance(), () -> 
		{
			playerHologram.delete();
			this.playersHologram.remove(owner);
		}, 20 * 5);
		
		return playerHologram;
	}
	
	private static HologramsProvider parseProviderFromConfig()
	{
		String configProviderName = ProtectedChat.getInstance().getConfig().getString("Holograms Provider");

		return Arrays.stream(HologramsProvider.values())
				.filter(provider -> StringUtils.remove(provider.name(), '_').equalsIgnoreCase(configProviderName))
				.findFirst()
				.orElseThrow(() -> new HologramsProviderException(String.format("Unknown Holograms Provider: %s", configProviderName)));
		
		/*if(!configProvider.available)
			throw new HologramsProviderException(String.format("The chosen Holograms Provider(%s) is not on the server!", configProviderName));
		
		return configProvider;*/
	}

	private enum HologramsProvider
	{
		HOLOGRAPHIC_DISPLAYS
		{
			@Override
			public MessagesHologram createHologram(Player owner, Location spawnLocation) 
			{
				return new MessagesHDHologram(owner, HologramsAPI.createHologram(ProtectedChat.getInstance(), spawnLocation));
			}
		};
		/*final String pluginName;
		final boolean available;
		
		HologramsProvider(String pluginName)
		{
			this.pluginName = pluginName;
			this.available = Bukkit.getPluginManager().isPluginEnabled(pluginName);
		}*/
		public abstract MessagesHologram createHologram(Player owner, Location location);
	}
}