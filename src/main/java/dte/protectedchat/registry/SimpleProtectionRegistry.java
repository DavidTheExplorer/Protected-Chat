package dte.protectedchat.registry;

import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import dte.protectedchat.protectors.ChatProtector;

public class SimpleProtectionRegistry implements ProtectionRegistry
{
	private final Map<Player, ChatProtector> playersProtectors = new HashMap<>();
	
	@Override
	public void protectWith(Player player, ChatProtector protector) 
	{
		this.playersProtectors.put(player, protector);
	}

	@Override
	public void disable(Player protectedPlayer) 
	{
		ChatProtector protector = this.playersProtectors.remove(protectedPlayer);
		
		if(protector != null)
			protector.disable(protectedPlayer);
	}
	
	@Override
	public boolean isProtected(Player player) 
	{
		return this.playersProtectors.containsKey(player);
	}

	@Override
	public ChatProtector getProtectorOf(Player protectedPlayer) 
	{
		return this.playersProtectors.get(protectedPlayer);
	}
	
	@Override
	public Collection<Player> getProtectedPlayers() 
	{
		return this.playersProtectors.keySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P extends ChatProtector> Map<Player, P> getPlayersProtectedBy(Class<P> protectorClass) 
	{
		return this.playersProtectors.entrySet().stream()
				.filter(entry -> entry.getValue().getClass().isAssignableFrom(protectorClass))
				.collect(toMap(Entry::getKey, entry -> (P) entry.getValue()));
	}
}
