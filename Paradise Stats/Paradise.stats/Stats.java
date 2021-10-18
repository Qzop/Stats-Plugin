package Paradise.stats;

import java.text.DecimalFormat;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import Paradise.main.Main;

public class Stats implements Listener
{
	Main plugin = Main.getPlugin(Main.class);
	MySQLKills k = new MySQLKills();
	MySQLDeaths d = new MySQLDeaths();
	MySQLkdr kdr = new MySQLkdr();
	MySQLPlayTime t = new MySQLPlayTime();
	MySQLWins w = new MySQLWins();
	  
	public void stats(Player p) 
	{
	   Inventory i = plugin.getServer().createInventory(null, 9, ChatColor.RED + p.getDisplayName() + "'s Stats");
	   DecimalFormat format = new DecimalFormat("0.00");
	   
	    new BukkitRunnable() 
	    {
	    	public void run() 
	        {
	    		ItemStack kills = new ItemStack(Material.DIAMOND_SWORD, 1, (short)0);
	    		ItemMeta killsmeta = kills.getItemMeta();
	    		killsmeta.setDisplayName(ChatColor.RED + "Kills " + ChatColor.GRAY + "" + ChatColor.WHITE + k.getKills(p.getUniqueId()));
	    		kills.setItemMeta(killsmeta);
	    		
	    		ItemStack wins = new ItemStack(Material.NETHER_STAR, 1, (short)0);
	    		ItemMeta winsmeta = wins.getItemMeta();
	    		winsmeta.setDisplayName(ChatColor.RED + "Wins " + ChatColor.GRAY + "" + ChatColor.WHITE + w.getwins(p.getUniqueId()));
	    		wins.setItemMeta(winsmeta);
	    		
	    		ItemStack death = new ItemStack(Material.SKULL_ITEM, 1, (short)0);
	    		ItemMeta deathmeta = death.getItemMeta();
	    		deathmeta.setDisplayName(ChatColor.RED + "Deaths " + ChatColor.GRAY + "" + ChatColor.WHITE + d.getDeaths(p.getUniqueId()));
	    		death.setItemMeta(deathmeta);
	    		
	    		ItemStack kd = new ItemStack(Material.PAPER, 1, (short)0);
	    		ItemMeta kdmeta = kd.getItemMeta();
	    		String KDR = format.format(Stats.this.kdr.getKdr(p.getUniqueId()));
	    		kdmeta.setDisplayName(ChatColor.RED + "KDR " + ChatColor.GRAY + "" + ChatColor.WHITE + KDR);
	    		kd.setItemMeta(kdmeta);
	    		
	    		ItemStack time = new ItemStack(Material.WATCH, 1, (short)0);
	    		ItemMeta timemeta = time.getItemMeta();
	    		int hours = t.getTime(p.getUniqueId()) / 3600;
	    		
	    		if (hours < 1) 
	    		{
	    			timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + "<1h");
	    		} 
	    		else if (hours >= 1) 
	    		{
	    			timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + hours + "h");
	            
	    			if (hours >= 24) 
	    			{
	    				int days = hours / 24;
	    				int temph = hours - days * 24;
	    				timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + days + "d" + temph + "h");
	              
	    				if (days >= 7) 
	    				{
	    					int weeks = days / 7;
	    					int tempd = days - weeks * 7;
	    					timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + weeks + "w" + tempd + "d" + temph + "h");
	                
	    					if (weeks >= 4) 
	    					{
	    						int months = weeks / 4;
	    						int tempw = weeks - months * 4;
	    						timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + months + "m" + tempw + "w" + tempd + "d" + temph + "h");
	    					} 
	    				} 
	    			} 
	    		} 
	    		
	    		time.setItemMeta(timemeta);
	     
	    		i.setItem(0, kills);
	    		i.setItem(2, kd);
	    		i.setItem(4, death);
	    		i.setItem(6, wins);
	    		i.setItem(8, time);
	          
	    		if(!p.getInventory().getName().equals(ChatColor.RED + p.getDisplayName() + "'s Stats")) 
	    		{
	    			cancel();
	    			return;
	    		} 
	        }
	   
	     }.runTaskTimer(plugin, 0, 20);
	     
	     p.openInventory(i);
	}
	  
	public void statsPlayer(Player p, Player target) 
	{
	    Inventory i = plugin.getServer().createInventory(null, 9, ChatColor.RED + target.getDisplayName() + "'s Stats");
	    DecimalFormat format = new DecimalFormat("0.00");
	    
	    new BukkitRunnable() 
	    {
	    	public void run() 
	    	{
	    		ItemStack kills = new ItemStack(Material.DIAMOND_SWORD, 1, (short) 0);
	    		ItemMeta killsmeta = kills.getItemMeta();
	    		killsmeta.setDisplayName(ChatColor.RED + "Kills " + ChatColor.GRAY + "" + ChatColor.WHITE + k.getKills(target.getUniqueId()));
	    		kills.setItemMeta(killsmeta);
	    		
	    		ItemStack wins = new ItemStack(Material.NETHER_STAR, 1, (short) 0);
	    		ItemMeta winsmeta = wins.getItemMeta();
	    		winsmeta.setDisplayName(ChatColor.RED + "Wins " + ChatColor.GRAY + "" + ChatColor.WHITE + w.getwins(target.getUniqueId()));
	    		wins.setItemMeta(winsmeta);
	    		
	    		ItemStack death = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
	    		ItemMeta deathmeta = death.getItemMeta();
	    		deathmeta.setDisplayName(ChatColor.RED + "Deaths " + ChatColor.GRAY + "" + ChatColor.WHITE + d.getDeaths(target.getUniqueId()));
	    		death.setItemMeta(deathmeta);
	          	
	          	ItemStack kd = new ItemStack(Material.PAPER, 1, (short) 0);
	          	ItemMeta kdmeta = kd.getItemMeta();
	          	String KDR = format.format(Stats.this.kdr.getKdr(target.getUniqueId()));
	          	kdmeta.setDisplayName(ChatColor.RED + "KDR " + ChatColor.GRAY + "" + ChatColor.WHITE + KDR);
	          	kd.setItemMeta(kdmeta);
	          	
	          	ItemStack time = new ItemStack(Material.WATCH, 1, (short) 0);
	          	ItemMeta timemeta = time.getItemMeta();
	          	int hours = t.getTime(p.getUniqueId()) / 3600;
	          	
	          	if (hours < 1) 
	          	{
	          		timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + "<1h");
	          	} 
	          	else if (hours >= 1) 
	          	{
	          		timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + hours + "h");
	          		
	          		if (hours >= 24) 
	          		{
	          			int days = hours / 24;
	          			int temph = hours - days * 24;
	          			timemeta.setDisplayName(ChatColor.RED+ "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + days + "d" + temph + "h");
	          			
	          			if (days >= 7) 
	          			{
	          				int weeks = days / 7;
	          				int tempd = days - weeks * 7;
	          				timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + weeks + "w" + tempd + "d" + temph + "h");
	          				
	          				if (weeks >= 4) 
	          				{
	          					int months = weeks / 4;
	          					int tempw = weeks - months * 4;
	          					timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + months + "m" + tempw + "w" + tempd + "d" + temph + "h");
	          				}		 
	          			} 
	          		} 
	          	} 
	          	
	          	time.setItemMeta(timemeta);
	          	i.setItem(0, kills);
	          	i.setItem(2, kd);
	          	i.setItem(4, death);
	          	i.setItem(6, wins);
	          	i.setItem(8, time);
	          	
	          	if(!p.getInventory().getName().equals(ChatColor.RED + target.getDisplayName() + "'s Stats")) 
	          	{
	          		cancel();
	          		return;
	          	} 
	        }
	    }.runTaskTimer(plugin, 0, 20);
	    
	    p.openInventory(i);
	}
	  
	public void statsOfflinePlayer(Player p, OfflinePlayer target) 
	{
		Inventory i = plugin.getServer().createInventory(null, 9, ChatColor.RED + target.getName() + "'s Stats");
		DecimalFormat format = new DecimalFormat("0.00");
		
	    new BukkitRunnable() 
	    {
	        public void run() 
	        {
	        	ItemStack kills = new ItemStack(Material.DIAMOND_SWORD, 1, (short) 0);
	        	ItemMeta killsmeta = kills.getItemMeta();
	        	killsmeta.setDisplayName(ChatColor.RED + "Kills " + ChatColor.GRAY + "" + ChatColor.WHITE + k.getKills(target.getUniqueId()));
	        	kills.setItemMeta(killsmeta);
	        	
	        	ItemStack wins = new ItemStack(Material.NETHER_STAR, 1, (short) 0);
	        	ItemMeta winsmeta = wins.getItemMeta();
	        	winsmeta.setDisplayName(ChatColor.RED + "Wins " + ChatColor.GRAY + "" + ChatColor.WHITE + w.getwins(target.getUniqueId()));
	        	wins.setItemMeta(winsmeta);
	        	
	        	ItemStack death = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
	        	ItemMeta deathmeta = death.getItemMeta();
	        	deathmeta.setDisplayName(ChatColor.RED + "Deaths " + ChatColor.GRAY + "" + ChatColor.WHITE + d.getDeaths(target.getUniqueId()));
	        	death.setItemMeta(deathmeta);
	        	
	        	ItemStack kd = new ItemStack(Material.PAPER, 1, (short) 0);
	        	ItemMeta kdmeta = kd.getItemMeta();
	        	String KDR = format.format(Stats.this.kdr.getKdr(target.getUniqueId()));
	        	kdmeta.setDisplayName(ChatColor.RED + "KDR " + ChatColor.GRAY + "" + ChatColor.WHITE + KDR);
	        	kd.setItemMeta(kdmeta);
	        	
	        	ItemStack time = new ItemStack(Material.WATCH, 1, (short) 0);
	        	ItemMeta timemeta = time.getItemMeta();
	        	int hours = t.getTime(p.getUniqueId()) / 3600;
	        	
	        	if (hours < 1) 
	        	{
	        		timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + "<1h");
	        	} 
	        	else if (hours >= 1) 
	        	{
	        		timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + hours + "h");
	        		
	        		if (hours >= 24) 
	        		{
	        			int days = hours / 24;
	        			int temph = hours - days * 24;
	        			timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + days + "d" + temph + "h");
	        			
	        			if (days >= 7) 
	        			{
	        				int weeks = days / 7;
	        				int tempd = days - weeks * 7;
	        				timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + weeks + "w" + tempd + "d" + temph + "h");
	        				
	        				if (weeks >= 4) 
	        				{
	        					int months = weeks / 4;
	        					int tempw = weeks - months * 4;
	        					timemeta.setDisplayName(ChatColor.RED + "Time Played " + ChatColor.GRAY + "" + ChatColor.WHITE + months + "m" + tempw + "w" + tempd + "d" + temph + "h");
	        					
	        				} 
	        			} 
	        		} 
	        	} 
	        	
	        	time.setItemMeta(timemeta);
	          
	        	i.setItem(0, kills);
	        	i.setItem(2, kd);
	        	i.setItem(4, death);
	        	i.setItem(6, wins);
	        	i.setItem(8, time);
	          
	        	if(!p.getInventory().getName().equals(ChatColor.RED + target.getName() + "'s Stats")) 
	        	{
	        		cancel();
	        		return;
	        	} 
	        }
	    }.runTaskTimer(plugin, 0, 20);
	    
	    p.openInventory(i);
	}
	  
	public void statsReset(Player p) 
	{
		Inventory i = plugin.getServer().createInventory(null, 9, ChatColor.RED + "Stats Reset");
		ItemStack confirm = new ItemStack(Material.EMERALD, 1, (short) 0);
		ItemMeta greenmeta = confirm.getItemMeta();
		greenmeta.setDisplayName(ChatColor.GREEN + "Confirm"); 
		confirm.setItemMeta(greenmeta);
		  
		ItemStack cancel = new ItemStack(Material.REDSTONE, 1, (short) 0);
		ItemMeta redmeta = cancel.getItemMeta();
		redmeta.setDisplayName(ChatColor.RED + "Cancel");
		cancel.setItemMeta(redmeta);
		  
		i.setItem(3, confirm);
		i.setItem(5, cancel);
		  
		p.openInventory(i);
	}
}