package Paradise.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import Paradise.stats.MySQLDeaths;
import Paradise.stats.MySQLKills;
import Paradise.stats.MySQLkdr;

public class KDR implements Listener
{
	MySQLDeaths death = new MySQLDeaths();
	  
	MySQLKills kill = new MySQLKills();
	  
	MySQLkdr ratio = new MySQLkdr();
	  
	public void updateKDR(Player p) 
	{
	    double deaths = this.death.getDeaths(p.getUniqueId());
	    double kills = this.kill.getKills(p.getUniqueId());
	    
	    if (deaths > 0.0D) 
	    {
	        double kdr = kills / deaths;
	        this.ratio.addKdr(p.getUniqueId(), kdr);
	    } 
	    else 
	    {
	    	double kdr = kills / 1.0D;
	        this.ratio.addKdr(p.getUniqueId(), kdr);
	    } 
	}
}
