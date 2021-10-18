package Paradise.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import Paradise.stats.MySQLWins;

public class Wins implements Listener
{
	MySQLWins w = new MySQLWins();
	  
	public void getFFAWinner(Player win) 
	{
	    w.addwins(win.getUniqueId(), 1);
	}
	  
	public void getTo2Winner(Player win1, Player win2) 
	{
		w.addwins(win1.getUniqueId(), 1);
	    w.addwins(win2.getUniqueId(), 1);
	}
	  
	public void getTo3Winner(Player win1, Player win2, Player win3) 
	{
	    w.addwins(win1.getUniqueId(), 1);
	    w.addwins(win2.getUniqueId(), 1);
	    w.addwins(win3.getUniqueId(), 1);
	}
	  
	public void getTo4Winner(Player win1, Player win2, Player win3, Player win4) 
	{
		w.addwins(win1.getUniqueId(), 1);
	    w.addwins(win2.getUniqueId(), 1);
	    w.addwins(win3.getUniqueId(), 1);
	    w.addwins(win4.getUniqueId(), 1);
	}
	  
	public void getTo5Winner(Player win1, Player win2, Player win3, Player win4, Player win5) 
	{
	    w.addwins(win1.getUniqueId(), 1);
	    w.addwins(win2.getUniqueId(), 1);
	    w.addwins(win3.getUniqueId(), 1);
	    w.addwins(win4.getUniqueId(), 1);
	    w.addwins(win5.getUniqueId(), 1);
	}
	  
	public void getTo6Winner(Player win1, Player win2, Player win3, Player win4, Player win5, Player win6) 
	{
	    w.addwins(win1.getUniqueId(), 1);
	    w.addwins(win2.getUniqueId(), 1);
	    w.addwins(win3.getUniqueId(), 1);
	    w.addwins(win4.getUniqueId(), 1);
	    w.addwins(win5.getUniqueId(), 1);
	    w.addwins(win6.getUniqueId(), 1);
	}
}
