package de.simonsator.partyandfriends.connection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;

public class Redis {
	String host;
	String password;

	public Redis(String pHost, String pPassword) {
		host = pHost;
		password = pPassword;
	}

	private Jedis connect() {
		Jedis jedis = new Jedis(host);
		if (!password.equals("")) {
			jedis.auth(password);
		}
		return jedis;
	}

	public String getUUIDByPlayerName(String pPlayerName) {
		Jedis jedis = connect();
		try {
			pPlayerName = pPlayerName.toLowerCase();
			return jedis.get("PAF:Players:" + pPlayerName + ":UUID");
		} finally {
			jedis.close();
		}
	}

	public List<String> getFriends(String pPlayerUUID) {
		Jedis jedis = connect();
		try {
			return jedis.lrange("PAF:Friends:" + pPlayerUUID + ":Friends", 0, 5000);
		} finally {
			jedis.close();
		}
	}

	public boolean isAFriendOf(String pPlayerOneUUID, String pPlayerTwoUUID) {
		Jedis jedis = connect();
		try {

			return getFriends(pPlayerOneUUID).contains(pPlayerTwoUUID);
		} finally {
			jedis.close();
		}
	}

	public String getPlayerNameByUUID(String pPlayerUUID) {
		Jedis jedis = connect();
		try {
			return jedis.get("PAF:Players:" + pPlayerUUID + ":PlayerName");
		} finally {
			jedis.close();
		}
	}

	public String getPartyID(UUID pUUID) {
		Jedis jedis = connect();
		try {
			String party = jedis.get("PAF:Partys:Player:" + pUUID);
			if (party == null) {
				return null;
			} else {
				return party;
			}
		} finally {
			jedis.close();
		}
	}

	public String getPartyLeader(int partyID) {
		Jedis jedis = connect();
		try {
			return jedis.get("PAF:Partys:Leader:" + partyID);
		} finally {
			jedis.close();
		}
	}

	public ArrayList<UUID> getPlayersInParty(int partyID) {
		Jedis jedis = connect();
		try {
			List<String> playersString = jedis.lrange("PAF:Partys:PartyMembers:" + partyID, 0, 1000);
			ArrayList<UUID> players = new ArrayList<>();
			for (String playerUUID : playersString) {
				players.add(UUID.fromString(playerUUID));
			}
			return players;
		} finally {
			jedis.close();
		}
	}

}
