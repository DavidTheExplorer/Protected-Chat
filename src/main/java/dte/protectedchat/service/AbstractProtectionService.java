package dte.protectedchat.service;

import java.util.Iterator;

import org.bukkit.entity.Player;

public abstract class AbstractProtectionService implements ProtectionService 
{
	@Override
	public boolean isProtected(Player player) 
	{
		return getProtectedPlayers().contains(player);
	}
	
	@Override
	public Iterator<Player> iterator() 
	{
		return getProtectedPlayers().iterator();
	}
}
