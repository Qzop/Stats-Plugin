package Paradise.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import Paradise.main.Main;

public class MySQLWins implements Listener
{
	Main plugin = Main.getPlugin(Main.class);
	  
	public void createTableWins() 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS wins (NAME VARCHAR(100),UUID VARCHAR(100), WINS INT(100),PRIMARY KEY (NAME))");
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
	    		PreparedStatement ps1 = this.plugin.getConnection().prepareStatement("INSERT IGNORE INTO wins (NAME,UUID) VALUES (?,?)");
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
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT * FROM wins WHERE UUID=?");
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
	  
	public void addwins(UUID uuid, int kills) 
	{
	    try 	
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("UPDATE wins SET WINS=? WHERE UUID=?");
	      	ps.setInt(1, getwins(uuid) + kills);
	      	ps.setString(2, uuid.toString());
	      	ps.executeUpdate();
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	}
	  
	public int getwins(UUID uuid) 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("SELECT WINS FROM wins WHERE UUID=?");
	    	ps.setString(1, uuid.toString());
	    	ResultSet rs = ps.executeQuery();
	    	int wins = 0;
	    	
	    	if (rs.next()) 
	    	{
	    		wins = rs.getInt("WINS");
	    		return wins;
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
	    try {
	      PreparedStatement ps = this.plugin.getConnection().prepareStatement("TRUNCATE wins");
	      ps.executeUpdate();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } 
	}
	  
	public void remove(UUID uuid) 
	{
	    try 
	    {
	    	PreparedStatement ps = this.plugin.getConnection().prepareStatement("DELETE FROM wins WHERE UUID=?");
	    	ps.setString(1, uuid.toString());
	    	ps.executeUpdate();
	    } 
	    catch(SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	}
}
