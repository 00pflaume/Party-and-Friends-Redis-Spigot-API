package de.simonsator.partyandfriends.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.simonsator.partyandfriends.main.Main;
import redis.clients.jedis.Jedis;

public class PartyAPI {
	/**
	 * Returns the ID of the party, where the player is in. If the player is not
	 * in a party it returns -1.
	 * 
	 * @param pPlayer
	 *            The UUID of the player
	 * @return Returns the pPartyID of the party, where the player is in
	 */
	public int getPartyID(UUID pPlayer) {
		String i;
		Jedis jedis = Main.getInstance().connect();
		try {
			i = jedis.get("PAF:Partys:Player:" + pPlayer);
		} finally {
			jedis.close();
		}
		if (i == null) {
			return -1;
		} else {
			return Integer.parseInt(i);
		}
	}

	/**
	 * Returns the leader of the party. If the party does not exist it returns
	 * null.
	 * 
	 * @param pPartyID
	 *            The ID of the party
	 * @return Returns the leader of the party
	 */
	public UUID getLeader(int pPartyID) {
		Jedis jedis = Main.getInstance().connect();
		String partyLeader = jedis.get("PAF:Partys:Leader:" + pPartyID);
		try {
			if (partyLeader != null)
				return UUID.fromString(partyLeader);
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * Returns the players in the party (exclusive the leader). If the party
	 * does not exist it returns an empty list.
	 * 
	 * @param pPartyID
	 *            The ID of the party
	 * @return Returns the players in the party (exclusive the leader). If the
	 *         party does not exist it returns an empty list.
	 */
	public ArrayList<UUID> getPlayersInParty(int pPartyID) {
		Jedis jedis = Main.getInstance().connect();
		try {
			List<String> playersString = jedis.lrange("PAF:Partys:PartyMembers:" + pPartyID, 0, 1000);
			ArrayList<UUID> players = new ArrayList<>();
			for (String playerUUID : playersString) {
				players.add(UUID.fromString(playerUUID));
			}
			return players;
		} finally {
			jedis.close();
		}
	}

	/**
	 * Returns the players in the party (inclusive the leader). If the party
	 * does not exist it returns an empty list.
	 * 
	 * @param pPartyID
	 *            The ID of the party
	 * @return Returns the players in the party (inclusive the leader). If the
	 *         party does not exist it returns an empty list.
	 */
	public ArrayList<UUID> getAllPlayersInParty(int pPartyID) {
		ArrayList<UUID> list = getAllPlayersInParty(pPartyID);
		UUID leaderUUID = getLeader(pPartyID);
		if (leaderUUID != null)
			list.add(leaderUUID);
		return list;
	}

	/**
	 * Returns if the player is the leader of the party or not.
	 * 
	 * @param pPlayer
	 *            The UUID of the player
	 * @param pPartyID
	 *            The ID of the party
	 * @return Returns if the player is the leader of the party or not.
	 */
	public boolean isPartyLeader(UUID pPlayer, int pPartyID) {
		UUID partyLeader = getLeader(pPartyID);
		if (partyLeader == null)
			return false;
		else {
			if ((partyLeader + "").equals(pPlayer + ""))
				return true;
			return false;
		}
	}
}
