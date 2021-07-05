package dte.protectedchat.holograms;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.stream.IntStream;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

import dte.protectedchat.holograms.vanishable.VanishableChatHologram;

/*
 * HolographicDisplays Implementation
 */
public class HDChatHologram extends AbstractChatHologram implements VanishableChatHologram
{
	public final Hologram hdHologram;

	public HDChatHologram(Player owner, Hologram hdHologram)
	{
		super(owner);

		this.hdHologram = hdHologram;
	}

	@Override
	public Location getLocation()
	{
		return this.hdHologram.getLocation();
	}
	
	@Override
	public double getHeight()
	{
		return this.hdHologram.getHeight();
	}

	@Override
	public void moveTo(Location newLocation)
	{
		this.hdHologram.teleport(newLocation);
	}

	@Override
	public void delete() 
	{
		this.hdHologram.delete();
	}


	@Override
	public String getMessage(int index)
	{
		TextLine textLine = (TextLine) this.hdHologram.getLine(index);

		return textLine.getText();
	}

	@Override
	public void addMessage(String message) 
	{
		this.hdHologram.appendTextLine(message);
	}

	@Override
	public void setMessage(int index, String message)
	{
		this.hdHologram.insertTextLine(index, message);
		this.hdHologram.removeLine(index+1);
	}

	@Override
	public void deleteMessage(int index) 
	{
		this.hdHologram.removeLine(index);
	}

	@Override
	public void clear()
	{
		this.hdHologram.clearLines();
	}

	@Override
	public Collection<String> getMessages()
	{
		return IntStream.range(0, this.hdHologram.size())
				.mapToObj(i -> (TextLine) this.hdHologram.getLine(i))
				.map(TextLine::getText)
				.collect(toList());
	}

	@Override
	public int size()
	{
		return this.hdHologram.size();
	}

	@Override
	public void vanish()
	{
		this.hdHologram.getVisibilityManager().setVisibleByDefault(false);
	}

	@Override
	public void appear()
	{
		this.hdHologram.getVisibilityManager().setVisibleByDefault(true);
	}

	@Override
	public boolean isVanished() 
	{
		return this.hdHologram.getVisibilityManager().isVisibleByDefault();
	}
}