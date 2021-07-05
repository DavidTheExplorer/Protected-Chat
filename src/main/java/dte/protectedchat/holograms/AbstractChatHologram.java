package dte.protectedchat.holograms;

import java.util.Iterator;
import java.util.Objects;

import org.bukkit.entity.Player;

public abstract class AbstractChatHologram implements ChatHologram
{
	protected final Player owner;
	
	public AbstractChatHologram(Player owner) 
	{
		this.owner = owner;
	}
	
	@Override
	public Player getOwner() 
	{
		return this.owner;
	}

	@Override
	public int size() 
	{
		return getMessages().size();
	}
	
	@Override
	public Iterator<String> iterator() 
	{
		return getMessages().iterator();
	}
	
	@Override
	public int hashCode() 
	{
		return Objects.hash(getLocation());
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (this == object)
			return true;
		
		if (object == null)
			return false;
		
		if (getClass() != object.getClass())
			return false;
		
		ChatHologram other = (ChatHologram) object;
		
		return Objects.equals(getLocation(), other.getLocation());
	}
}