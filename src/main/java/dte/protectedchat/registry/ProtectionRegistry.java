package dte.protectedchat.registry;

import java.util.Collection;

import org.bukkit.entity.Player;

import dte.protectedchat.protectors.ChatProtector;

public interface ProtectionRegistry
{
	void protectWith(Player player, ChatProtector protector);
	void disable(Player protectedPlayer);
	boolean isProtected(Player player);
	ChatProtector getProtectorOf(Player protectedPlayer);
	
	Collection<Player> getPlayersProtectedBy(ChatProtector protector);
	Collection<Player> getProtectedPlayers();
	void clear();
}