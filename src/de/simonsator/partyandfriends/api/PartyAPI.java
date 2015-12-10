package de.simonsator.partyandfriends.api;

import java.util.ArrayList;
import java.util.UUID;

import de.simonsator.partyandfriends.main.Main;

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
		String i = Main.getInstance().getConnection().getPartyID(pPlayer);
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
		String leader = Main.getInstance().getConnection().getPartyLeader(pPartyID);
		if (leader == null) {
			return null;
		} else {
			return UUID.fromString(leader);
		}
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
		return Main.getInstance().getConnection().getPlayersInParty(pPartyID);
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
		list.add(getLeader(pPartyID));
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
