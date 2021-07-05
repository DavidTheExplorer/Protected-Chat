package dte.protectedchat.holograms;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Represents an Hologram that displays protected messages(that weren't sent to the global chat)
 */
public interface ChatHologram extends Iterable<String>
{
	Player getOwner();
	Location getLocation();
	double getHeight();
	void moveTo(Location newLocation);
	void delete();
	
	//messages
	String getMessage(int index);
	void addMessage(String message);
	void setMessage(int index, String message);
	void deleteMessage(int index);
	void clearMessages();
	Collection<String> getMessages();
	int size();
}