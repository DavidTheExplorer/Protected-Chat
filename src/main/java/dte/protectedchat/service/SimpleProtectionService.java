package dte.protectedchat.service;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import dte.protectedchat.protectors.ChatProtector;

public class SimpleProtectionService implements ProtectionService
{
	private final Map<Player, ChatProtector> playersProtectors = new HashMap<>();

	@Override
	public void protect(Player player, ChatProtector protector) 
	{
		this.playersProtectors.put(player, protector);
	}

	@Override
	public void disable(Player player) 
	{
		ChatProtector protector = this.playersProtectors.remove(player);

		//the player might not be protected
		if(protector != null)
			protector.disable(player);
	}

	@Override
	public boolean isProtected(Player player) 
	{
		return this.playersProtectors.containsKey(player);
	}

	@Override
	public ChatProtector getProtectorOf(Player player) 
	{
		return this.playersProtectors.get(player);
	}
	
	@Override
	public Collection<Player> getPlayersProtectedBy(ChatProtector protector) 
	{
		return this.playersProtectors.entrySet().stream()
				.filter(entry -> entry.getValue() == protector)
				.map(Entry::getKey)
				.collect(toList());
	}

	@Override
	public Collection<Player> getProtectedPlayers() 
	{
		return this.playersProtectors.keySet();
	}
	
	@Override
	public void clear()
	{
		this.playersProtectors.forEach((player, protector) -> protector.disable(player));
		this.playersProtectors.clear();
	}

	@Override
	public Iterator<Player> iterator() 
	{
		return getProtectedPlayers().iterator();
	}
}
