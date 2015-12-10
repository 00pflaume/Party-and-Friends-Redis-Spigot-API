package de.simonsator.partyandfriends.main;

import org.bukkit.plugin.java.JavaPlugin;

import de.simonsator.partyandfriends.connection.Redis;

public class Main extends JavaPlugin {
	private static Main main;
	private Redis connection;

	public void onEnable() {
		main = this;
		Main.main.getConfig().options().copyDefaults(true);
		Main.main.saveConfig();
		connection = (new Redis(getConfig().getString("Redis.Host"), getConfig().getString("Redis.Password")));
	}

	public void onDisable() {
	}

	public Redis getConnection() {
		return connection;
	}

	public static Main getInstance() {
		return main;
	}
}
