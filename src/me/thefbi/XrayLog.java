package me.thefbi;

import me.thefbi.listeners.DiamondBlockBreakListener;
import me.thefbi.listeners.EmeraldBlockBreakListener;
import me.thefbi.listeners.GoldBlockBreakListener;
import me.thefbi.listeners.IronBlockBreakListener;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class XrayLog extends JavaPlugin{
	
	public static XrayLog instance;
	
	public String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "XrayLogger" + ChatColor.GRAY + "] ";
	
	public boolean displayNotification = true;
		
	MyConfigManager mgr;
	public MyConfig logs;
	public MyConfig settings;
	
	public void onEnable()
	{
		mgr = new MyConfigManager(this);
		logs = mgr.getNewConfig("PlayerLogs.yml");
		settings = mgr.getNewConfig("Settings.yml");
		this.displayNotification = settings.getBoolean("broadcast-notifications");
		settings.set("broadcast-notifications", displayNotification);
		settings.saveConfig();
		instance = this;
		Bukkit.getServer().getPluginManager().registerEvents(new DiamondBlockBreakListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EmeraldBlockBreakListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GoldBlockBreakListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new IronBlockBreakListener(), this);

	}
	
	public void onDisable()
	{
		instance = null;
	}
	
}
