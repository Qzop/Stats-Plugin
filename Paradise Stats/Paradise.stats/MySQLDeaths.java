package Paradise.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import Paradise.main.Main;

public class MySQLDeaths implements Listener
{
	Main plugin = Main.getPlugin(Main.class);
	  
	public void createTableDeaths() 
	{
		try 
		{
			PreparedStatement ps = plugin.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS deaths (NAME VARCHAR(100),UUID VARCHAR(100), DEATHS INT(100),PRIMARY KEY (NAME))");
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
	    	  PreparedStatement ps1 = this.plugin.getConnection().prepareStatement("INSERT IGNORE INTO deaths (NAME,UUID) VALUES (?,?)");
	    	  ps1.setString(1, p.getDisplayName());
	    	  ps1.setString(2, uuid.toString());
	    	  ps1.executeUpdate();
	    	  return;
			} 
	    } 
		catch (SQLException e) 
		{
			e.printStackTrace();
	    } 
	}
	  
	public boolean exists(UUID uuid) 
	{
		try 
		{
			PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT * FROM deaths WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet results = ps.executeQuery();
			
			if (results.next())
			{
				return true; 
			}
	        
	      return false;
	    } 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
	    } 
	}
	  
	public void addDeaths(UUID uuid, int deaths) 
	{
		try 
		{
			PreparedStatement ps = this.plugin.getConnection().prepareStatement("UPDATE deaths SET DEATHS=? WHERE UUID=?");
			ps.setInt(1, getDeaths(uuid) + deaths);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
	    } 
		catch(SQLException e) 
		{
			e.printStackTrace();
	    } 
	}
	  
	public int getDeaths(UUID uuid) 
	{
		try 
		{
			PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT DEATHS FROM deaths WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			int deaths = 0;
			
			if (rs.next()) 
			{
				deaths = rs.getInt("DEATHS");
				return deaths;
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
			PreparedStatement ps = this.plugin.getConnection().prepareStatement("TRUNCATE deaths");
			ps.executeUpdate();
	    } 
		catch (SQLException e) 
		{
			e.printStackTrace();
	    } 
	}
	  
	public void remove(UUID uuid) 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("DELETE FROM deaths WHERE UUID=?");
	    	ps.setString(1, uuid.toString());
	    	ps.executeUpdate();
	    } 
	    catch(SQLException e) 	
	    {
	    	e.printStackTrace();
	    } 
	}
}