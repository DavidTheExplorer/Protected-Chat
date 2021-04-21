package dte.protectedchat.holograms;

public interface VanishableMessagesHologram extends MessagesHologram
{
	void vanish();
	void appear();
	boolean isVanished();
}
