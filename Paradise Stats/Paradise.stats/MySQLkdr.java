package Paradise.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import Paradise.main.Main;

public class MySQLkdr implements Listener
{
	 Main plugin = Main.getPlugin(Main.class);
	  
	public void createTableKDR() 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS kdr (NAME VARCHAR(100),UUID VARCHAR(100), KDR DOUBLE(10,2),PRIMARY KEY (NAME))");
	    	ps.executeUpdate();
	    } 
	    catch (SQLException e) 
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
	    		PreparedStatement ps1 = this.plugin.getConnection().prepareStatement("INSERT IGNORE INTO kdr (NAME,UUID) VALUES (?,?)");
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
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT * FROM kdr WHERE UUID=?");
	      	ps.setString(1, uuid.toString());
	      	ResultSet results = ps.executeQuery();
	      	
	      	if(results.next())
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
	  
	public void addKdr(UUID uuid, double kdr) 
	{
		try 
		{
			PreparedStatement ps = this.plugin.getConnection().prepareStatement("UPDATE kdr SET KDR=? WHERE UUID=?");
			ps.setDouble(1, kdr);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
	    } 
		catch(SQLException e) 
		{
			e.printStackTrace();
	    } 
	}
	  
	public double getKdr(UUID uuid) 
	{
		double kdr = 0.0D;
		  
		try 
		{
			PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT KDR FROM kdr WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			  
			if(rs.next()) 
			{
				kdr = rs.getDouble("KDR");
				return kdr;
			} 
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		} 
		  
	    return 0.0D;
	}
	  
	public void emptytable() 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("TRUNCATE kdr");
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
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("DELETE FROM kdr WHERE UUID=?");
	    	ps.setString(1, uuid.toString());
	    	ps.executeUpdate();
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	}
}
