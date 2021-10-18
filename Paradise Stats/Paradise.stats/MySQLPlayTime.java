package Paradise.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import Paradise.main.Main;

public class MySQLPlayTime implements Listener
{
	Main plugin = Main.getPlugin(Main.class);
	  
	public void createTableTime() 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS time (NAME VARCHAR(100),UUID VARCHAR(100), TIME INT(255),PRIMARY KEY (NAME))");
	    	ps.executeUpdate();
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	}
	  
	public void createPlayer(Player p) 
	{
	    try 
	    {
	    	UUID uuid = p.getUniqueId();
	    	
	    	if (!exists(uuid))
	    	{
	    		PreparedStatement ps1 = this.plugin.getConnection().prepareStatement("INSERT IGNORE INTO time (NAME,UUID) VALUES (?,?)");
	    		ps1.setString(1, p.getDisplayName());
	    		ps1.setString(2, uuid.toString());
	    		ps1.executeUpdate();
	    		return;
	    	} 
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	}
	  
	public boolean exists(UUID uuid) 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT * FROM time WHERE UUID=?");
	    	ps.setString(1, uuid.toString());
	    	ResultSet results = ps.executeQuery();
	    	
	    	if (results.next())
	    	{
	    		return true; 
	    	}
	    	
	    	return false;
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    	return false;
	    } 
	}
	  
	public void addTime(UUID uuid, int time) 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("UPDATE time SET TIME=? WHERE UUID=?");
	    	ps.setInt(1, getTime(uuid) + time);
	    	ps.setString(2, uuid.toString());
	    	ps.executeUpdate();
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	}
	  
    public int getTime(UUID uuid) 
    {
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT TIME FROM time WHERE UUID=?");
	    	ps.setString(1, uuid.toString());
	    	ResultSet rs = ps.executeQuery();
	    	int time = 0;
	    	
	    	if (rs.next()) 
	    	{
	    		time = rs.getInt("TIME");
	    		return time;
	    	} 
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    }

	    return 0;
	}
	  
	public void emptytable() 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("TRUNCATE time");
	    	ps.executeUpdate();
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	}
	  
	public void remove(UUID uuid) 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("DELETE FROM time WHERE UUID=?");
	    	ps.setString(1, uuid.toString());
	    	ps.executeUpdate();
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	}
}
