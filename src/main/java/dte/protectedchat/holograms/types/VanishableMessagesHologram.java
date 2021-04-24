package dte.protectedchat.holograms.types;

import dte.protectedchat.holograms.MessagesHologram;

public interface VanishableMessagesHologram extends MessagesHologram
{
	void vanish();
	void appear();
	boolean isVanished();
}
