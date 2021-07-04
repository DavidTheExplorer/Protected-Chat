package dte.protectedchat.service;

import java.util.Iterator;

import org.bukkit.entity.Player;

public abstract class AbstractProtectionService implements ProtectionService 
{
	@Override
	public Iterator<Player> iterator() 
	{
		return getProtectedPlayers().iterator();
	}
}
