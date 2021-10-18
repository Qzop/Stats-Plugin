package Paradise.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Paradise.stats.MySQLClearDatabase;

public class ResetStatsEvent implements Listener
{	
	@EventHandler
	public void inventoryClick(InventoryClickEvent event) 
	{
		Player player = (Player)event.getWhoClicked();
	    Inventory open = event.getClickedInventory();
	    ItemStack item = event.getCurrentItem();
	    String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "ParadiseUHC" + ChatColor.GRAY + "]";
	    MySQLClearDatabase clear = new MySQLClearDatabase();
	    
	    if(open.getName().equals(ChatColor.RED + "Stats Reset")) 
	    {
	    	event.setCancelled(true);
	    	
	    	if(item == null || !item.hasItemMeta())
	    	{
	    		return; 
	    	}
	    	
	    	if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Confirm")) 
	    	{
	    		player.closeInventory();
	    		clear.clearAll();
	    		player.sendMessage(String.valueOf(prefix) + ChatColor.GREEN + " All stats have been cleared successfully.");
	    	} 
	    	else if(item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Cancel")) 
	    	{
	    		player.closeInventory();
	    		player.sendMessage(String.valueOf(prefix) + ChatColor.RED + " Stats reset cancelled. No stats have been cleared.");
	        } 
	    } 
	}
}