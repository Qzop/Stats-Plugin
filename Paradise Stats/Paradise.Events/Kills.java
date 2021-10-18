package Paradise.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import Paradise.stats.MySQLDeaths;
import Paradise.stats.MySQLKills;

public class Kills implements Listener
{
	MySQLKills kills = new MySQLKills();
	  
	MySQLDeaths deaths = new MySQLDeaths();
	  
	KDR kdr = new KDR();
	  
	@EventHandler
	public void onDeath(PlayerDeathEvent e) 
	{
	    
	    if(e.getEntity() instanceof Player && e.getEntity().getWorld().getName().equals("uhc")) 
	    {
	    	Player killed = e.getEntity();
	    	
	    	this.deaths.addDeaths(killed.getUniqueId(), 1);
	    	this.kdr.updateKDR(killed);
	    	
	      if(killed.getKiller() instanceof Player && killed.getKiller().getWorld().getName().equals("uhc")) 
	      {
	    	  Player killer = killed.getKiller();
	          this.kills.addKills(killer.getUniqueId(), 1);
	          this.kdr.updateKDR(killer);
	      } 
	      else if(killed.getKiller() == null) 
	      {
	    	  if(killed.getLastDamageCause() instanceof EntityDamageByEntityEvent) 
	    	  {
	    		  EntityDamageByEntityEvent dmgEvent = (EntityDamageByEntityEvent)
	    		  killed.getLastDamageCause();
	    		  
	    		  if (dmgEvent.getDamager() instanceof Player) 
	    		  {
	    			  Player killer = (Player)dmgEvent.getDamager();
	    			  this.kills.addKills(killer.getUniqueId(), 1);
	    		  } 
	    	  } 
	      } 
	   } 
	}
}
