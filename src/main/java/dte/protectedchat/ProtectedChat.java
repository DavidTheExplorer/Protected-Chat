package dte.protectedchat;

import org.bukkit.ChatColor;

import dte.protectedchat.commands.ChatProtectCommand;
import dte.protectedchat.holograms.displayers.SimpleHologramDisplayer;
import dte.protectedchat.holograms.providers.ChatHologramProvider;
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
		
		ChatHologramProvider hologramProvider = parseHologramProviderFromConfig();
		MessageConfiguration messageConfiguration = parseMessagesConfigurationFromConfig();
		
		//null means either not found or not available; If null was returned, a descriptive explanation was sent to the console
		if(hologramProvider == null)
			return;
		
		this.protectionService = new SimpleProtectionService();
		this.globalChatProtector = new HologramChatProtector(this.protectionService, hologramProvider, messageConfiguration, new SimpleHologramDisplayer());

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
	
	private ChatHologramProvider parseHologramProviderFromConfig() 
	{
		String configProviderName = getConfig().getString("Holograms.Provider");
		ChatHologramProvider hologramProvider = ChatHologramProvider.fromName(configProviderName);
		
		if(hologramProvider == null)
		{
			shutdownFor(ChatColor.RED + String.format("Could not find an Holograms Provider named \"%s\"!", configProviderName));
			return null;
		}
		if(!hologramProvider.isAvailable())
		{
			shutdownFor(ChatColor.RED + String.format("The chosen Holograms Provider(%s) is not Available!", configProviderName));
			return null;
		}
		return hologramProvider;
	}
	
	private void registerListeners()
	{
		registerListeners(
				new ChatProtectionListener(this.protectionService), 
				new ChatProtectionDisableListener(this.protectionService));
	}
	
	private MessageConfiguration parseMessagesConfigurationFromConfig()
	{
		String format = ChatColorUtils.colorize(getConfig().getString("Holograms.Messages Format"));
		
		return new MessageConfiguration(format);
	}
}