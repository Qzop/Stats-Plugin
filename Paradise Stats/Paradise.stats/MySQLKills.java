package Paradise.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import Paradise.main.Main;

public class MySQLKills implements Listener
{
	 Main plugin = Main.getPlugin(Main.class);
	  
	public void createTableKills() 
	{
		try 
		{
			PreparedStatement ps = this.plugin.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS kills (NAME VARCHAR(100),UUID VARCHAR(100), KILLS INT(100),PRIMARY KEY (NAME))");
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
			
			if(!exists(uuid)) 
			{
				PreparedStatement ps1 = this.plugin.getConnection().prepareStatement("INSERT IGNORE INTO kills (NAME,UUID) VALUES (?,?)");
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
			PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT * FROM kills WHERE UUID=?");
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
	  
	public void addKills(UUID uuid, int kills) 
	{
		try 
		{
			PreparedStatement ps = this.plugin.getConnection().prepareStatement("UPDATE kills SET KILLS=? WHERE UUID=?");
			ps.setInt(1, getKills(uuid) + kills);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
	    } 
		catch(SQLException e) 
		{
			e.printStackTrace();
	    } 
	}
	  
	public int getKills(UUID uuid) 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT KILLS FROM kills WHERE UUID=?");
	    	ps.setString(1, uuid.toString());
	    	ResultSet rs = ps.executeQuery();
	    	int kills = 0;
	    	
	    	if(rs.next()) 
	    	{
	    		kills = rs.getInt("KILLS");
	    		return kills;
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
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("TRUNCATE kills");
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
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("DELETE FROM kills WHERE UUID=?");
	    	ps.setString(1, uuid.toString());
	    	ps.executeUpdate();
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	}
}
