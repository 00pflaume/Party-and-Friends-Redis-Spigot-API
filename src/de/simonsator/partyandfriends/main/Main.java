package de.simonsator.partyandfriends.main;

import org.bukkit.plugin.java.JavaPlugin;

import redis.clients.jedis.Jedis;

public class Main extends JavaPlugin {
	private static Main main;
	private String host;
	private String password;

	public void onEnable() {
		main = this;
		Main.main.getConfig().options().copyDefaults(true);
		Main.main.saveConfig();
		host = (getConfig().getString("Redis.Host"));
		password = getConfig().getString("Redis.Password");

	}

	public Jedis connect() {
		Jedis jedis = new Jedis(host);
		if (!password.equals("")) {
			jedis.auth(password);
		}
		return jedis;
	}

	public static Main getInstance() {
		return main;
	}
}
