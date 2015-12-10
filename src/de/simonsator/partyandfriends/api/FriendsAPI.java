package de.simonsator.partyandfriends.api;

import java.util.List;

import de.simonsator.partyandfriends.main.Main;

/**
 * The API for the friends system
 * 
 * @author Simonsator
 * @version 1.0.1
 */
public class FriendsAPI {
	/**
	 * Returns if someone is a friend of an other one.
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 * @param pPlayerOneUUID
	 *            The first UUID of the first person
	 * @param pPlayerTwoUUID
	 *            The second UUID of the second person
	 * @return Returns true if person one and person two are friends. Returns
	 *         false if they are not friends.
	 */
	public static boolean isAFriendOf(String pPlayerOneUUID, String pPlayerTwoUUID) {
		return Main.getInstance().getConnection().isAFriendOf(pPlayerOneUUID, pPlayerTwoUUID);
	}

	/**
	 * Returns the UUIDs of the friends of the player.
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 * @param pPlayerUUID
	 *            The UUID of the player who you want to find the friends of.
	 * @return Returns the UUIDs of the friends of the player.
	 */
	public static List<String> getFriends(String pPlayerUUID) {
		return Main.getInstance().getConnection().getFriends(pPlayerUUID);
	}

	/**
	 * Returns the UUID of the player.
	 * 
	 * @param pPlayerName
	 *            The name of the player.
	 * @return Returns the UUID of the player.
	 */
	public static String getUUIDByPlayerName(String pPlayerName) {
		return Main.getInstance().getConnection().getUUIDByPlayerName(pPlayerName);
	}

	/**
	 * Returns the name of the player.
	 * 
	 * @param pPlayerUUID
	 *            The UUID of the player
	 * @return Returns the name of the player.
	 */
	public static String getNameByPlayerUUID(String pPlayerUUID) {
		return Main.getInstance().getConnection().getPlayerNameByUUID(pPlayerUUID);
	}
}
