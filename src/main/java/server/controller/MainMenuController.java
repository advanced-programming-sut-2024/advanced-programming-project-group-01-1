package server.controller;

import message.Result;
import server.model.Client;
import server.model.user.User;
import server.view.game.prematch.MatchFinderMenu;
import server.view.sign.login.LoginMenu;
import server.view.user.ProfileMenu;

public class MainMenuController {

	public static Result logout(Client client) {
		client.setIdentity(null);
		client.setMenu(new LoginMenu());
		return new Result("Logged out successfully", true);
	}

	public static Result goToProfileMenu(Client client) {
		client.setMenu(new ProfileMenu());
		return new Result("Entered Profile Menu", true);
	}

	public static Result goToMatchFinderMenu(Client client) {
		client.setMenu(new MatchFinderMenu());
		return new Result("Entered Match Finder Menu", true);
	}

	public static Result showReceivedFriendRequests(Client client) {
		synchronized (User.getUsers()) {
			StringBuilder friendRequests = new StringBuilder();
			for (User user : client.getIdentity().getReceivedRequests()) {
				friendRequests.append(user.getUsername()).append("\n");
			}
			return new Result(friendRequests.toString(), true);
		}
	}

	public static Result showSentFriendRequests(Client client) {
		synchronized (User.getUsers()) {
			StringBuilder sentFriendRequests = new StringBuilder();
			for (User user : client.getIdentity().getSentRequests()) {
				sentFriendRequests.append(user.getUsername()).append("\n");
			}
			return new Result(sentFriendRequests.toString(), true);
		}
	}

	public static Result showFriends(Client client) {
		synchronized (User.getUsers()) {
			StringBuilder friends = new StringBuilder();
			for (User user : client.getIdentity().getFriends()) {
				friends.append(user.getUsername()).append("\n");
			}
			return new Result(friends.toString(), true);
		}
	}

	public static Result sendFriendRequest(Client client, String username) {
		synchronized (User.getUsers()) {
			User user = User.getUserByUsername(username);
			if (user == null) {
				return new Result("User not found", false);
			}
			if (client.getIdentity().getSentRequests().contains(user)) {
				return new Result("You have already sent a friend request to this user", false);
			}
			if (client.getIdentity().getReceivedRequests().contains(user)) {
				return new Result("You have already received a friend request from this user", false);
			}
			if (client.getIdentity().getFriends().contains(user)) {
				return new Result("You are already friends with this user", false);
			}
			client.getIdentity().addSentRequest(user);
			user.addReceivedRequest(client.getIdentity());
			return new Result("Friend request sent successfully", true);
		}
	}

	public static Result unsendFriendRequest(Client client, String username) {
		synchronized (User.getUsers()) {
			User user = User.getUserByUsername(username);
			if (user == null) {
				return new Result("User not found", false);
			}
			if (!client.getIdentity().getSentRequests().contains(user)) {
				return new Result("You have not sent a friend request to this user", false);
			}
			client.getIdentity().removeSentRequest(user);
			user.removeReceivedRequest(client.getIdentity());
			return new Result("Friend request unsent successfully", true);
		}
	}

	public static Result declineFriendRequest(Client client, String username) {
		synchronized (User.getUsers()) {
			User user = User.getUserByUsername(username);
			if (user == null) {
				return new Result("User not found", false);
			}
			if (!client.getIdentity().getReceivedRequests().contains(user)) {
				return new Result("You have not received a friend request from this user", false);
			}
			client.getIdentity().removeReceivedRequest(user);
			user.removeSentRequest(client.getIdentity());
			return new Result("Friend request declined successfully", true);
		}
	}

	public static Result acceptFriendRequest(Client client, String username) {
		synchronized (User.getUsers()) {
			User user = User.getUserByUsername(username);
			if (user == null) {
				return new Result("User not found", false);
			}
			if (!client.getIdentity().getReceivedRequests().contains(user)) {
				return new Result("You have not received a friend request from this user", false);
			}
			client.getIdentity().addSentRequest(user);
			user.addReceivedRequest(client.getIdentity());
			return new Result("Friend request accepted successfully", true);
		}
	}

	public static Result removeFriend(Client client, String username) {
		synchronized (User.getUsers()) {
			User user = User.getUserByUsername(username);
			if (user == null) {
				return new Result("User not found", false);
			}
			if (!client.getIdentity().getFriends().contains(user)) {
				return new Result("You are not friends with this user", false);
			}
			client.getIdentity().removeSentRequest(user);
			user.removeReceivedRequest(client.getIdentity());
			client.getIdentity().removeReceivedRequest(user);
			user.removeSentRequest(client.getIdentity());
			return new Result("Friend removed successfully", true);
		}
	}

	public static Result showCurrentMenu() {
		return new Result("Main Menu", true);
	}

	public static Result getLoggedInUsername(Client client) {
		synchronized (User.getUsers()) {
			return new Result(client.getIdentity().getUsername(), true);
		}
	}
}
