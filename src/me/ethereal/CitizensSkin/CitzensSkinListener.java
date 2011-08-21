package me.ethereal.CitizensSkin;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.packet.PacketType;
import org.getspout.spoutapi.packet.listener.PacketListener;
import org.getspout.spoutapi.packet.standard.MCPacket;
import org.getspout.spoutapi.player.AppearanceManager;

import com.citizens.events.NPCListener;
import com.citizens.events.NPCSpawnEvent;
import com.citizens.resources.npclib.HumanNPC;

public class CitzensSkinListener extends NPCListener implements PacketListener{
	CitzensSkin plugin;
	Server server;
	public CitzensSkinListener(CitzensSkin instance) {
		this.plugin = instance;
		this.server = Bukkit.getServer();
	}
	public void onNPCSpawn(NPCSpawnEvent event)
	{
		//plugin.log("Spawn!");
		HumanNPC npc = event.getNPC();
		String URL = plugin.CheckForNpcSkin(npc.getUID());
		//plugin.log("ID: " + npc.getUID() + " URL: " + URL);
		if(URL != "false")
		{
			AppearanceManager mgr = SpoutManager.getAppearanceManager();
			//plugin.log("HAS SKIN!");
			HumanEntity HNpc = (HumanEntity)npc.getPlayer();
			//HumanEntity entity = (HumanEntity)((Entity)NPCManager.get(UID).getHandle()).getBukkitEntity();
			mgr.setGlobalSkin(HNpc, URL);
			
		}
	}
	@Override
	public boolean checkPacket(Player player, MCPacket packet) {
		plugin.log("Player: " + player.toString() + "Packet: " + packet.getId());
		plugin.log("gfgd" + packet.hashCode());
		return true;
	}
}
