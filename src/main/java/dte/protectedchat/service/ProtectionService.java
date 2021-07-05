package dte.protectedchat.service;

import java.util.Collection;

import org.bukkit.entity.Player;

import dte.protectedchat.protectors.ChatProtector;

public interface ProtectionService extends Iterable<Player>
{
	void protect(Player player, ChatProtector protector);
	void disable(Player protectedPlayer);
	boolean isProtected(Player player);
	ChatProtector getProtectorOf(Player protectedPlayer);
	
	Collection<Player> getPlayersProtectedBy(ChatProtector protector);
	Collection<Player> getProtectedPlayers();
	void clear();
}