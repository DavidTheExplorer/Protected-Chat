package dte.protectedchat.service;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.entity.Player;

import dte.protectedchat.protectors.ChatProtector;

public abstract class AbstractProtectionService implements ProtectionService 
{
	@Override
	public boolean isProtected(Player player) 
	{
		return getProtectedPlayers().contains(player);
	}
	
	@Override
	public Collection<Player> getPlayersProtectedBy(ChatProtector protector) 
	{
		return getProtectedPlayers().stream()
				.filter(protectedPlayer -> getProtectorOf(protectedPlayer) == protector)
				.collect(toList());
	}
	
	@Override
	public void clear()
	{
		getProtectedPlayers().forEach(this::disable);
	}
	
	@Override
	public Iterator<Player> iterator() 
	{
		return getProtectedPlayers().iterator();
	}
}
