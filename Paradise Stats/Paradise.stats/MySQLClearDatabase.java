package Paradise.stats;

import org.bukkit.event.Listener;

import Paradise.main.Main;

public class MySQLClearDatabase implements Listener
{
	public MySQLKills kills = new MySQLKills();
	public MySQLDeaths deaths = new MySQLDeaths();
	public MySQLkdr kdr = new MySQLkdr();
	public MySQLPlayTime time = new MySQLPlayTime();
	public MySQLWins win = new MySQLWins();
	public Main plugin;
	  
	public void clearAll() 
	{
		kills.emptytable();
	    deaths.emptytable();
	    kdr.emptytable();
	    time.emptytable();
	    win.emptytable();
	}
}
