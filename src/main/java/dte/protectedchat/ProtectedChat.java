package dte.protectedchat;

import java.util.Arrays;

import org.bukkit.ChatColor;

import dte.protectedchat.commands.ChatProtectCommand;
import dte.protectedchat.holograms.displayers.SimpleHologramsDisplayer;
import dte.protectedchat.holograms.providers.ChatHologramProvider;
import dte.protectedchat.holograms.providers.HolographicDisplaysProvider;
import dte.protectedchat.listeners.ChatProtectionDisableListener;
import dte.protectedchat.listeners.ChatProtectionListener;
import dte.protectedchat.protectors.ChatProtector;
import dte.protectedchat.protectors.HologramChatProtector;
import dte.protectedchat.protectors.HologramChatProtector.MessageConfiguration;
import dte.protectedchat.service.ProtectionService;
import dte.protectedchat.service.SimpleProtectionService;
import dte.protectedchat.utils.ChatColorUtils;
import dte.protectedchat.utils.ModernJavaPlugin;

public class ProtectedChat extends ModernJavaPlugin
{
	private ProtectionService protectionService;
	private ChatProtector globalChatProtector;

	private static ProtectedChat INSTANCE;

	@Override
	public void onEnable()
	{
		INSTANCE = this;
		
		saveDefaultConfig();
		
		ChatHologramProvider hologramProvider = parseHologramsProviderFromConfig();
		MessageConfiguration messageConfiguration = parseMessagesConfigurationFromConfig();
		
		//if either is null, the plugin was shut down and a descriptive message was sent
		if(hologramProvider == null || messageConfiguration == null)
			return;
		
		this.protectionService = new SimpleProtectionService();
		this.globalChatProtector = new HologramChatProtector(this.protectionService, hologramProvider, messageConfiguration, new SimpleHologramsDisplayer());

		registerListeners();
		registerCommands();
	}
	public static ProtectedChat getInstance()
	{
		return INSTANCE;
	}
	private void registerCommands() 
	{
		getCommand("chatprotect").setExecutor(new ChatProtectCommand(this.protectionService, this.globalChatProtector));
	}
	private void registerListeners()
	{
		registerListeners(
				new ChatProtectionListener(this.protectionService),
				new ChatProtectionDisableListener(this.protectionService));
	}

	private ChatHologramProvider parseHologramsProviderFromConfig() 
	{
		String configProviderName = getConfig().getString("Holograms.Provider");
		
		ChatHologramProvider configProvider = Arrays.stream(new ChatHologramProvider[]{new HolographicDisplaysProvider()})
				.filter(provider -> provider.getName().equalsIgnoreCase(configProviderName))
				.findFirst()
				.orElse(null);
		
		if(configProvider == null) 
		{
			shutdownFor(ChatColor.RED + String.format("Could not find an Holograms Provider named '%s'!", configProviderName));
			return null;
		}
		if(!configProvider.isAvailable()) 
		{
			shutdownFor(ChatColor.RED + String.format("The chosen Holograms Provider(%s) is either Missing or Disabled.", configProviderName));
			return null;
		}
		return configProvider;
	}
	
	private MessageConfiguration parseMessagesConfigurationFromConfig()
	{
		String format = ChatColorUtils.colorize(getConfig().getString("Holograms.Messages Format"));
		
		return new MessageConfiguration(format);
	}
}