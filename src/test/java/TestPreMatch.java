import controller.MainMenuController;
import controller.game.PreMatchMenusController;
import controller.sign.LoginMenusController;
import model.Result;
import model.user.Question;
import model.user.User;
import org.junit.Before;
import org.junit.Test;
import view.Appview;
import view.MainMenu;
import view.game.MatchMenu;
import view.game.prematch.LobbyMenu;
import view.game.prematch.MatchFinderMenu;
import view.sign.login.LoginMenu;

public class TestPreMatch {

	@Before
	public void setUp() {
		Appview.setMenu(new LoginMenu());
		new User("a", "test", "@Test123", "test", new Question("test", "test"));
		new User("b", "test", "@Test123", "test", new Question("test", "test"));
		LoginMenusController.login("a", "@Test123", false);
		MainMenuController.goToMatchFinderMenu();
		assert (Appview.getMenu() instanceof MatchFinderMenu);
	}

	@Test
	public void testMatchFinder() {
		Result result;
		result = PreMatchMenusController.createGame("c");
		assert (result.getMessage().equals("User Not Found"));
		result = PreMatchMenusController.createGame("a");
		assert (result.getMessage().equals("You Cannot Play With Yourself"));
		result = PreMatchMenusController.createGame("b");
		assert (result.isSuccessful());
		assert (Appview.getMenu() instanceof LobbyMenu);
		PreMatchMenusController.showFactions();
		result = PreMatchMenusController.selectFaction("Invalid Faction Name");
		assert (result.getMessage().equals("Invalid Faction Name"));
		result = PreMatchMenusController.selectFaction("Nilfgaardian Empire");
		assert (result.isSuccessful());
		PreMatchMenusController.showNowFactionToGraphics();
		PreMatchMenusController.showCards();
		PreMatchMenusController.showCardsForGraphic();
		PreMatchMenusController.showDeck();
		PreMatchMenusController.showDeckForGraphic();
		PreMatchMenusController.showLeaders();
		PreMatchMenusController.showNowLeaderForGraphic();
		PreMatchMenusController.showInfo();
		result = PreMatchMenusController.selectLeader(5);
		assert (result.getMessage().equals("Invalid Leader Number"));
		result = PreMatchMenusController.selectLeader(1);
		assert (result.isSuccessful());
		testDeck();
	}

	private void testDeck() {
		Result result;
		result = PreMatchMenusController.addToDeck("", 0);
		assert (result.getMessage().equals("Invalid Count"));
		result = PreMatchMenusController.addToDeck("Decoy", 4);
		assert (result.getMessage().equals("Not Enough Cards Available"));
		result = PreMatchMenusController.addToDeck("Decoy", 1);
		assert (result.isSuccessful());
		result = PreMatchMenusController.deleteFromDeck(1, 1);
		assert (result.getMessage().equals("Invalid Card Number"));
		result = PreMatchMenusController.deleteFromDeck(0, 0);
		assert (result.getMessage().equals("Invalid Count"));
		result = PreMatchMenusController.deleteFromDeck(0, 2);
		assert (result.getMessage().equals("Not Enough Cards in Deck"));
		result = PreMatchMenusController.deleteFromDeck(0, 1);
		assert (result.isSuccessful());
		result = PreMatchMenusController.setPreferFirst(false);
		assert (result.isSuccessful());
		result = PreMatchMenusController.changeTurn();
		assert (result.getMessage().equals("Your Deck Is Invalid"));
		PreMatchMenusController.getUsernames();
		PreMatchMenusController.addToDeck("Cirilla Fiona Elen Riannon", 1);
		result = PreMatchMenusController.saveDeckByName("test");
		assert (result.isSuccessful());
		result = PreMatchMenusController.saveDeckByAddress("D:/ap/vojood_nadarad/test.json");
		assert (result.getMessage().equals("Invalid Address"));
		result = PreMatchMenusController.loadDeckByAddress("D:/ap/vojood_nadarad/test.json");
		assert (result.getMessage().equals("Invalid Address"));
		PreMatchMenusController.addToDeck("Geralt of Rivia", 1);
		result = PreMatchMenusController.loadDeckByName("test");
		assert (result.isSuccessful());
		result = PreMatchMenusController.showDeck();
		assert (result.getMessage().split("\n------------------\n").length == 1);
		result = PreMatchMenusController.loadDeckByName("valid");
		assert (result.isSuccessful());
		result = PreMatchMenusController.changeTurn();
		assert (result.isSuccessful());
		result = PreMatchMenusController.loadDeckByName("valid");
		assert (result.isSuccessful());
	}

}
