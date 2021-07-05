package dte.protectedchat.holograms.vanishable;

import dte.protectedchat.holograms.ChatHologram;

public interface VanishableChatHologram extends ChatHologram
{
	void vanish();
	void appear();
	boolean isVanished();
}
