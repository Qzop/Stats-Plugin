package Paradise.main;

import org.bukkit.event.Listener;

import Paradise.Events.Wins;
import Paradise.stats.MySQLDeaths;
import Paradise.stats.MySQLKills;
import Paradise.stats.MySQLPlayTime;
import Paradise.stats.MySQLWins;
import Paradise.stats.MySQLkdr;
import Paradise.stats.Stats;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.CommandExecute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands extends CommandExecute implements Listener, CommandExecutor
{
	 Stats s = new Stats();
     MySQLDeaths death = new MySQLDeaths();
     MySQLkdr kdr = new MySQLkdr();
     MySQLKills kills = new MySQLKills();
     MySQLPlayTime time = new MySQLPlayTime();
     MySQLWins wins = new MySQLWins();
    
     public String reloadconfig = "sdbreload";
	 public String stats = "stats";
	 public String winner = "statswinner";
	 private Main plugin;
	  
	  public Commands(Main plugin) 
	  {
		  this.plugin = plugin;
	  }
	  
	  @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	  {
		  String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "ParadiseUHC" + ChatColor.GRAY + "]";
	      Player p = (Player)sender;
	    
	      if(label.equalsIgnoreCase(this.reloadconfig))
	      {
	    	  if(p.hasPermission("admin.reloadconfig")) 
		      {
	    		  this.plugin.saveConfig();
		          p.sendMessage(String.valueOf(prefix) + ChatColor.GREEN + " Config was reloaded.");
		      }  
	      }
	      
	    if (label.equalsIgnoreCase(this.stats)) 
	    {
	    	if(args.length == 1) 
	    	{
	    		if (args[0].equals("reset")) 
	    		{
	    			if (p.hasPermission("skript.op")) 
	    			{
	    				s.statsReset(p);
	                } 
	    			else 
	    			{
	    				p.sendMessage(ChatColor.RED + "No permission");
	                } 
	           } 
	    	   else if (args[0].equals("add")) 
	    	   {
	    		   if (p.hasPermission("skript.op")) 
	    		   {
	    			   p.sendMessage(ChatColor.RED + "Usage: /stats add (deaths/kills/wins) (player) (number)");  
	    		   } 
	    		   else 
	    		   {
	    			   p.sendMessage(ChatColor.RED + "No permission");
	    		   } 
	    	   } 
	    	   else if (args[0].equals("clear")) 
	    	   {
	    		   if (p.hasPermission("skript.op")) 
	    		   {
	    			   p.sendMessage(ChatColor.RED + "Usage: /stats clear (player)");
	    		   } 
	    		   else 
	    		   {
	    			   p.sendMessage(ChatColor.RED + "No permission");
	               } 
	           } 
	    	   else 
	    	   {
	    		   Player target = Bukkit.getPlayer(args[0]);
	    		   
	    		   if(target == null) 
	    		   {
	    			   OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
	    			   
	    			   if(t == null) 
	    			   {
	    				   p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not exist!");
	    			   } 
	    			   else if(death.exists(t.getUniqueId()) && wins.exists(t.getUniqueId()) && kdr.exists(t.getUniqueId()) && kills.exists(t.getUniqueId()) && time.exists(t.getUniqueId())) 
	    			   {
	    				   s.statsOfflinePlayer(p, t);
	    			   } 
	    			   else 
	    			   {
	    				   p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not have any stats recorded.");
	    			   } 
	    		   } 
	    		   else if(death.exists(target.getUniqueId()) && wins.exists(target.getUniqueId()) && kdr.exists(target.getUniqueId()) && kills.exists(target.getUniqueId()) && time.exists(target.getUniqueId())) 
	    		   {
	    			   s.statsPlayer(p, target);
	    		   } 
	    		   else
	    		   {
	    			   p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not have any stats recorded.");
	    		   } 
	    	   } 
	      } 
	      else if (args.length > 1) 
	      {
	    	  int num = 0;
	    	  
	    	  if (args[0].equals("add")) 
	    	  {
	    		  if (args[1].equals("deaths")) 
	    		  {
	    			  if (args[2].equals("")) 
	    			  {
	    				  p.sendMessage(ChatColor.RED + "Usage: /stats add deaths (player) (number)");
	    			  } 
	    			  else 
	    			  {
	    				  Player target = Bukkit.getPlayer(args[2]);
	    				  
	    				  if(target == null) 
	    				  {
	    					  OfflinePlayer t = Bukkit.getOfflinePlayer(args[2]);
	    					  
	    					  if(death.exists(t.getUniqueId())) 
	    					  {
	    						  num = Integer.parseInt(args[3]);
	    						  
	    						  if (args[3] == null) 
	    						  {
	    							  p.sendMessage(ChatColor.RED + "Usage: /stats add deaths (player) (number)");
	    						  } 
	    						  else 
	    						  {
	    							  death.addDeaths(t.getUniqueId(), num);
	    						  } 
	    					  } 
	    					  else 
	    					  {
	    						  p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not exist!");
	    					  } 
	    				  } 
	    				  else if(death.exists(target.getUniqueId())) 
	    				  {
	    					  num = Integer.parseInt(args[3]);
	    					  
	    					  if (args[3] == null) 
	    					  {
	    						  p.sendMessage(ChatColor.RED + "Usage: /stats add deaths (player) (number)");
	    					  }
	    					  else 
	    					  {
	    						  death.addDeaths(target.getUniqueId(), num);
	    					  } 
	    				  } 
	    				  else 
	    				  {
	    					  p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not exist!");
	    				  } 
	    			  } 
	    		  } 
	    		  else if(args[1].equals("kills")) 
	    		  {
	    			  if(args[2] == null) 
	    			  {
	    				  p.sendMessage(ChatColor.RED + "Usage: /stats add kills (player) (number)");
	    			  } 
	    			  else 
	    			  {
	    				  Player target = Bukkit.getPlayer(args[2]);
	    				  
	    				  if(target == null) 
	    				  {
	    					  OfflinePlayer t = Bukkit.getOfflinePlayer(args[2]);
	    					  
	    					  if(kills.exists(t.getUniqueId())) 
	    					  {
	    						  num = Integer.parseInt(args[3]);
	                 
	    						  if(args[3] == null) 
	    						  {
	    							  p.sendMessage(ChatColor.RED + "Usage: /stats add kills (player) (number)");
	    						  } 
	    						  else 
	    						  {
	    							  kills.addKills(t.getUniqueId(), num);
	    						  } 
	    					  } 
	    					  else 
	    					  {
	    						  p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not exist!");
	    					  } 
	    				  } 
	    				  else if(kills.exists(target.getUniqueId())) 
	    				  {
	    					  num = Integer.parseInt(args[3]);
	    					  
	    					  if (args[3] == null) 
	    					  {
	    						  p.sendMessage(ChatColor.RED + "Usage: /stats add kills (player) (number)");
	    					  } 	
	    					  else 
	    					  {
	    						  kills.addKills(target.getUniqueId(), num);
	    					  } 
	    				  } 
	    				  else 
	    				  {
	    					  p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not exist!");
	    				  } 
	    			  } 
	    		  } 
	    		  else if(args[1].equals("wins")) 
	    		  {
	    			  if(args[2] == null) 
	    			  {
	    				  p.sendMessage(ChatColor.RED + "Usage: /stats add wins (player) (number)");
	    			  } 
	    			  else 
	    			  {
	    				  Player target = Bukkit.getPlayer(args[2]);
	    				  
	    				  if(target == null) 
	    				  {
	    					  OfflinePlayer t = Bukkit.getOfflinePlayer(args[2]);
	    					  
	    					  if(wins.exists(t.getUniqueId())) 
	    					  {
	    						  num = Integer.parseInt(args[3]);
	    						  
	    						  if(args[3] == null) 
	    						  {
	    							  p.sendMessage(ChatColor.RED + "Usage: /stats add wins (player) (number)");
	    						  } 
	    						  else 
	    						  {
	    							  wins.addwins(t.getUniqueId(), num);
	    						  } 
	    					  } 
	    					  else 
	    					  {
	    						  p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not exist!");
	    					  } 
	    				  } 
	    				  else if(wins.exists(target.getUniqueId())) 
	    				  {
	    					  num = Integer.parseInt(args[3]);
	    					  
	    					  if(args[3] == null) 
	    					  {
	    						  p.sendMessage(ChatColor.RED + "Usage: /stats add wins (player) (number)");
	    					  } 
	    					  else 
	    					  {
	    						  wins.addwins(target.getUniqueId(), num);
	    					  } 
	    				  } 
	    				  else 
	    				  {
	    					  p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not exist!");
	    				  } 
	    			  } 
	    		  } 
	    		  else 
	    		  {
	    			  p.sendMessage(ChatColor.RED + "Usage: /stats add (deaths/kills/wins) (player) (number)");
	    		  } 
	    	  } 
	    	  else if(args[0].equals("clear")) 
	    	  {
	    		  Player target = Bukkit.getPlayer(args[1]);
	    		  
	    		  if (target == null) 
	    		  {
	    			  OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
	    			  
	    			  if(death.exists(t.getUniqueId()) && wins.exists(t.getUniqueId()) && kdr.exists(t.getUniqueId()) && kills.exists(t.getUniqueId()) && time.exists(t.getUniqueId())) 
	    			  {
	    				  death.remove(t.getUniqueId());
	    				  wins.remove(t.getUniqueId());
	    				  kdr.remove(t.getUniqueId());
	    				  kills.remove(t.getUniqueId());
	    				  time.remove(t.getUniqueId());
	    				  
	    				  p.sendMessage(String.valueOf(prefix) + ChatColor.GREEN + " Player stats successfully cleared.");
	    			  } 
	    			  else 
	    			  {
	    				  p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not exist!");
	    			  } 
	    		  } 
	    		  else if(death.exists(target.getUniqueId()) && wins.exists(target.getUniqueId()) && kdr.exists(target.getUniqueId()) && kills.exists(target.getUniqueId()) && time.exists(target.getUniqueId())) 
	    		  {
	    			  death.remove(target.getUniqueId());
	    			  wins.remove(target.getUniqueId());
	    			  kdr.remove(target.getUniqueId());
	    			  kills.remove(target.getUniqueId());
	    			  time.remove(target.getUniqueId());
	    			  
	    			  p.sendMessage(String.valueOf(prefix) + ChatColor.GREEN + " Player stats successfully cleared.");
	    		  } 
	    		  else 
	    		  {
	    			  p.sendMessage(String.valueOf(prefix) + ChatColor.RED + " That player does not exist!");
	    		  } 
	    	  } 
	      } 
	      else
	      {
	    	  s.stats(p);
	      } 
	   } 
	    if (p.hasPermission("winner.set"))
	    {
	    	if (label.equalsIgnoreCase(this.winner)) 
	    	{
		        Wins win = new Wins();
		        
		        if(args.length == 1) 
		        {
		          Player win1 = Bukkit.getPlayer(args[0]);
		          
		          if(win1 != null)
		          {
		        	  win.getFFAWinner(win1); 
		          }
		        } 
		        else if (args.length == 2)
		        {
		        	Player win1 = Bukkit.getPlayer(args[0]);
		        	Player win2 = Bukkit.getPlayer(args[1]);
		        	
		        	if(win1 != null && win2 != null)
		        	{
		        		win.getTo2Winner(win1, win2); 
		        	}
		        }
		        else if (args.length == 3) 
		        {
		        	Player win1 = Bukkit.getPlayer(args[0]);
		        	Player win2 = Bukkit.getPlayer(args[1]);
		        	Player win3 = Bukkit.getPlayer(args[2]);
		        	
		        	if(win1 != null && win2 != null && win3 != null)
		        	{
		        		win.getTo3Winner(win1, win2, win3); 
		        	}
		        } 
		        else if (args.length == 4) 
		        {
		        	Player win1 = Bukkit.getPlayer(args[0]);
		        	Player win2 = Bukkit.getPlayer(args[1]);
		        	Player win3 = Bukkit.getPlayer(args[2]);
		        	Player win4 = Bukkit.getPlayer(args[3]);
		        	
		        	if (win1 != null && win2 != null && win3 != null && win4 != null)
		        	{
		        		win.getTo4Winner(win1, win2, win3, win4); 
		        	}
		        } 
		        else if (args.length == 5) 
		        {
		        	Player win1 = Bukkit.getPlayer(args[0]);
		        	Player win2 = Bukkit.getPlayer(args[1]);
		        	Player win3 = Bukkit.getPlayer(args[2]);
		        	Player win4 = Bukkit.getPlayer(args[3]);
		        	Player win5 = Bukkit.getPlayer(args[4]);
		        	
		        	if(win1 != null && win2 != null && win3 != null && win4 != null && win5 != null)
		        	{
		        		 win.getTo5Winner(win1, win2, win3, win4, win5); 
		        	}
		        } 
		        else if (args.length == 6) 
		        {
		        	Player win1 = Bukkit.getPlayer(args[0]);
		        	Player win2 = Bukkit.getPlayer(args[1]);
		        	Player win3 = Bukkit.getPlayer(args[2]);
		        	Player win4 = Bukkit.getPlayer(args[3]);
		        	Player win5 = Bukkit.getPlayer(args[4]);
		        	Player win6 = Bukkit.getPlayer(args[5]);
		        	
		        	if(win1 != null && win2 != null && win3 != null && win4 != null && win5 != null && win6 != null)
		        	{
		        		win.getTo6Winner(win1, win2, win3, win4, win5, win6);
		        	}
		        } 
		        else 
		        {
		        	p.sendMessage(ChatColor.RED + "Usage: /statswinner (player1) (player2) (player3) (player4) (player5) (player6)");
		        } 
		    }
	    	
		    return false;
		}
	    
		return false;
	}
}
