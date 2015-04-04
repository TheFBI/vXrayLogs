package me.thefbi.listeners;

import java.util.HashMap;

import me.thefbi.XrayLog;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GoldBlockBreakListener implements Listener {
	
private HashMap<Block, Location> blocksToIgnore = new HashMap<Block, Location>();
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent e)
	{
		if(e.getBlock().getType().equals(Material.GOLD_ORE))
		{
			int goldCount = 0;

			for (int x = -5; x < 5; x++) {
		        for (int y = -5; y < 5; y++) {
		          for (int z = -5; z < 5; z++)
		          {
		            Block block = e.getBlock().getLocation().add(x, y, z).getBlock();
		            if(block.getType().equals(Material.GOLD_ORE) && !this.blocksToIgnore.containsKey(block))
		            {
		            	goldCount++;
		            		this.blocksToIgnore.put(block, block.getLocation());
		            } else {
		            	continue;
		            }
		          }
		        }
			}
			
				boolean moreThan = false;
				if(goldCount >= 1)
				{
					moreThan = true;
				}
				if(moreThan)
				{		
				int last = XrayLog.instance.logs.getInt("XrayLog." + e.getPlayer().getName() + ".Gold_Ore");
				XrayLog.instance.logs.set("XrayLog." + e.getPlayer().getName() + ".Gold_Ore", goldCount + last);
				XrayLog.instance.logs.set("XrayLog." + e.getPlayer().getName() + ".Last-GoldOre-Location", (int) e.getPlayer().getLocation().getX() + ", " + (int) e.getPlayer().getLocation().getY() + ", " + (int) e.getPlayer().getLocation().getZ());
				XrayLog.instance.logs.saveConfig();
				}
				
				if(XrayLog.instance.displayNotification && moreThan)
				{
					Bukkit.broadcast(XrayLog.instance.prefix + ChatColor.RED + e.getPlayer().getName() + ChatColor.GRAY + " Found " + ChatColor.AQUA + goldCount + ChatColor.GRAY + " Gold Ore.", "xraylog.show");
				}
		}			
	}

}
