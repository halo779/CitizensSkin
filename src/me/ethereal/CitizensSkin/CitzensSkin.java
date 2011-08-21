package me.ethereal.CitizensSkin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.AppearanceManager;

import com.citizens.events.NPCListener;
import com.citizens.npcs.NPCManager;
import com.citizens.resources.npclib.HumanNPC;

public class CitzensSkin extends JavaPlugin {
	private final CitzensSkinListener listener = new CitzensSkinListener(this);
	String pluginname = "CitzensSkin";
	NPCListener NpcListener = new NPCListener();
	Logger logg = Logger.getLogger("Minecraft");
	private File file;
	@Override
	public void onDisable() {
		log("[" + pluginname + "] " + pluginname + " has been disabled.");
	}

	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		file = new File(getDataFolder() + "/config.yml");
		
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Runnable run = new Runnable(){
			
			@Override
			public void run(){
				
				loadNPCSkins();
				log("loaded npcs");
				
			}
			
		};
		
		getServer().getScheduler().scheduleSyncDelayedTask(this, run, 2);
		log("[" + pluginname + "] " + pluginname + " has been enabled.");
		log("[" + pluginname + "] Created by Ethereal");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.CUSTOM_EVENT, listener, Event.Priority.Normal, this);
		SpoutManager.getPacketManager().addListener(20, listener);
	}
	
	public void log(String log)
	{
		logg.info("[" + pluginname + "] " + log);
	}
	
public void loadNPCSkins(){
		
		Configuration config = new Configuration(file);
		config.load();
		
		int successfulSkins = 0;
		
		//NPCManager npcmanger = new NPCManager();
		
		AppearanceManager mgr = SpoutManager.getAppearanceManager();
		
		List<String> keys = config.getKeys();
		for(String key : keys){
			
			if(key == null)
				continue;
			
			Integer UID = null;
			
			try {
				
				UID = Integer.parseInt(key);
				
			} catch(Exception e){
				
				log(key + " is not an integer!");
				continue;
				
			}
			log(key + ".url");
			String URL = config.getString(key + ".url");
			if(URL == null || URL.isEmpty()){
			
				log(key + " does not have a URL!");
				continue;
				
			}
			HumanNPC entity = (HumanNPC)NPCManager.get(UID);
			HumanEntity entityP = (HumanEntity)entity.getPlayer();
			//HumanEntity entity = (HumanEntity)((Entity)NPCManager.get(UID).getHandle()).getBukkitEntity();
			mgr.setGlobalSkin(entityP, URL);
			
			successfulSkins++;
			
		}
		
		log("Skinned " + successfulSkins + " NPCs successfully.");
		
	}

public String CheckForNpcSkin(Integer UID){
	
	Configuration config = new Configuration(file);
	config.load();
	String URL = "false";
	List<String> keys = config.getKeys();
	
	if(keys.contains(UID.toString()))
	{
		URL = config.getString(UID.toString() + ".url");
		log(UID.toString() + ".url");
	}
	
	return URL;
}

}
