package Paradise.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import Paradise.Events.Kills;
import Paradise.Events.ResetStatsEvent;
import Paradise.Events.StatsEvent;
import Paradise.Events.Time;
import Paradise.stats.MySQLDeaths;
import Paradise.stats.MySQLKills;
import Paradise.stats.MySQLPlayTime;
import Paradise.stats.MySQLWins;
import Paradise.stats.MySQLkdr;

public class Main extends JavaPlugin implements Listener 
{
	private Connection connection;
	public String host;
	public String database;
	public String username;
	public String password;
	public int port;
	public static Main plugin;
	public MySQLKills kills;
	public MySQLDeaths deaths;
	public MySQLkdr kdr;
	public MySQLPlayTime time;
	public MySQLWins win;
	public Time t;
	  
	public void onEnable() 
	{
	    Commands command = new Commands(this);
	    this.kills = new MySQLKills();
	    this.deaths = new MySQLDeaths();
	    this.kdr = new MySQLkdr();
	    this.time = new MySQLPlayTime();
	    this.t = new Time();
	    this.win = new MySQLWins();
	    plugin = this;
	    getServer().getPluginManager().registerEvents(this, this);
	    getServer().getPluginManager().registerEvents(new Kills(), this);
	    getServer().getPluginManager().registerEvents(new StatsEvent(), this);
	    getServer().getPluginManager().registerEvents(new ResetStatsEvent(), this);
	    getCommand(command.reloadconfig).setExecutor(command);
	    getCommand(command.stats).setExecutor(command);
	    getCommand(command.winner).setExecutor(command);
	    getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nParadiseUHC Stats Enabled.\n\n");
	    loadConfig();
	    SQLSetup();
	}
	  
	public void onDisable() 
	{
	    getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nParadiseUHC Stats Disabled.\n\n");
	}
	  
	public void loadConfig() 
	{
	    getConfig().options().copyDefaults(true);
	    saveConfig();
	}
	  
	public void SQLSetup() 
	{
	    this.host = getConfig().getString("Host: ");
	    this.database = getConfig().getString("Database: ");
	    this.username = getConfig().getString("Username: ");
	    this.password = getConfig().getString("Password: ");
	    this.port = Integer.parseInt(getConfig().getString("Port: "));
	    
	    try 
	    {
	    	synchronized (this) 
	    	{
	    		if (getConnection() != null && !getConnection().isClosed())
	    		{
	    			return; 
	    		}
	        
	    		Class.forName("com.mysql.jdbc.Driver");
	    		setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
	    		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nMySQL Database connected.\n\n");
	    		kills.createTableKills();
	    		deaths.createTableDeaths();
	    		kdr.createTableKDR();
	    		time.createTableTime();
	    	  	win.createTableWins();
	    	} 
	    } 
	    catch(SQLException e) 
	    {
	    	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "\n\nMySQL Database not connected.\n\n");
	    	e.printStackTrace();
	    } 
	    catch(ClassNotFoundException e) 
	    {
	    	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "\n\nMySQL Database not connected.\n\n");
	    	e.printStackTrace();
	    } 
	    
	    new BukkitRunnable()
	    {
	    	public void run()
	    	{
	    		if(getConnection() == null)
	    	    {
	    	    	SQLSetup();
	    	    	cancel();
	    	    	return;
	    	    }
	    	}
	    	
	    }.runTaskTimer(plugin, 0, 1200);
	}
	  
	public Connection getConnection() 
	{
	    return this.connection;
	}
	  
	public void setConnection(Connection connection) 
	{
	    this.connection = connection;
	}
	  
	@EventHandler
	public void onJoin(PlayerJoinEvent e) 
	{
	    Player p = e.getPlayer();
	    
	    kills.createPlayer(p);
	    deaths.createPlayer(p);
	    kdr.createPlayer(p);
	    time.createPlayer(p);
	    win.createPlayer(p);
	    t.Timer(p);
	}
}
