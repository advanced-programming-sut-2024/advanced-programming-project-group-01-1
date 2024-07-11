import controller.MainMenuController;
import controller.UserMenusController;
import controller.enums.RegisterMenusResponses;
import controller.game.PreMatchMenusController;
import controller.sign.LoginMenusController;
import model.Result;
import model.user.Question;
import model.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.Appview;
import view.MainMenu;
import view.game.prematch.MatchFinderMenu;
import view.sign.login.LoginMenu;
import view.sign.register.RegisterMenu;
import view.user.HistoryMenu;
import view.user.InfoMenu;
import view.user.ProfileMenu;
import view.user.RankingMenu;

public class TestMain {

	@Before
	public void setUp() {
		Appview.setMenu(new LoginMenu());
		new User("test", "test", "@Test123", "test", new Question("test", "test"));
		LoginMenusController.login("test", "@Test123", false);
	}

	@Test
	public void testMain() {
		MainMenuController.goToProfileMenu();
		assert (Appview.getMenu() instanceof ProfileMenu);
		UserMenusController.exit();
		assert (Appview.getMenu() instanceof MainMenu);
		MainMenuController.goToMatchFinderMenu();
		assert (Appview.getMenu() instanceof MatchFinderMenu);
		PreMatchMenusController.exit();
		assert (Appview.getMenu() instanceof MainMenu);
		MainMenuController.logout();
		assert (Appview.getMenu() instanceof LoginMenu);
	}

	@Test
	public void testProfileMenu() {
		MainMenuController.goToProfileMenu();
		assert (Appview.getMenu() instanceof ProfileMenu);
		Result result;
		result = UserMenusController.changeUsername("test");
		assert (result == RegisterMenusResponses.DUPLICATE_USERNAME.getResult());
		result = UserMenusController.changeUsername("test 2");
		assert (result == RegisterMenusResponses.INVALID_USERNAME.getResult());
		result = UserMenusController.changeUsername("test2");
		assert (result.isSuccessful());
		result = UserMenusController.changeNickname("test 2");
		assert (result.isSuccessful());
		result = UserMenusController.changePassword("test2", "test");
		assert (result.getMessage().equals("Incorrect password"));
		result = UserMenusController.changePassword("test 2", "@Test123");
		assert (result == RegisterMenusResponses.INVALID_PASSWORD.getResult());
		result = UserMenusController.changePassword("test2", "@Test123");
		assert (result == RegisterMenusResponses.WEAK_PASSWORD.getResult());
		result = UserMenusController.changePassword("@Test123", "@Test123");
		assert (result.isSuccessful());
		result = UserMenusController.changeEmail("test");
		assert (result == RegisterMenusResponses.INVALID_EMAIL.getResult());
		result = UserMenusController.changeEmail("test@test.com");
		assert (result.isSuccessful());
		testSaveChanges();
		testMoving();
	}

	private void testSaveChanges() {
		Result result;
		result = UserMenusController.saveChanges("test", "t", "t@test.co", "@Test123", "", "");
		assert (result.isSuccessful());
		// test save changes like How I tested change methods
		result = UserMenusController.saveChanges("test", "t", "t@test.com", "test", "", "");
		assert (result.getMessage().equals("Wrong Password"));
		new User("testtest", "test", "test", "test", new Question("test", "test"));
		result = UserMenusController.saveChanges("testtest", "t", "t@test.com", "@Test123", "", "");
		assert (result == RegisterMenusResponses.DUPLICATE_USERNAME.getResult());
		result = UserMenusController.saveChanges("test test", "t", "t@test.com", "@Test123", "", "");
		assert (result == RegisterMenusResponses.INVALID_USERNAME.getResult());
		result = UserMenusController.saveChanges("test", "t", "t", "@Test123", "", "");
		assert (result == RegisterMenusResponses.INVALID_EMAIL.getResult());
		result = UserMenusController.saveChanges("test", "t", "t@test.com", "@Test123", "", "t");
		assert (result == RegisterMenusResponses.PASSWORDS_DONT_MATCH.getResult());
		result = UserMenusController.saveChanges("test", "t", "t@test.com", "@Test123", "te st", "te st");
		assert (result == RegisterMenusResponses.INVALID_PASSWORD.getResult());
		result = UserMenusController.saveChanges("test", "t", "t@test.com", "@Test123", "test", "test");
		assert (result == RegisterMenusResponses.WEAK_PASSWORD.getResult());
		result = UserMenusController.saveChanges("test", "t", "t@test.com", "@Test123", "@123Test", "@123Test");
		assert (result.isSuccessful());
	}

	private void testMoving() {
		UserMenusController.exit();
		assert (Appview.getMenu() instanceof MainMenu);
		MainMenuController.goToProfileMenu();
		UserMenusController.goToInfoMenu();
		assert (Appview.getMenu() instanceof InfoMenu);
		UserMenusController.goToHistoryMenu();
		assert (Appview.getMenu() instanceof HistoryMenu);
		UserMenusController.exit();
		assert (Appview.getMenu() instanceof InfoMenu);
		UserMenusController.exit();
		assert (Appview.getMenu() instanceof ProfileMenu);
	}

	@Test
	public void testRankingMenu() {
		MainMenuController.goToRankingMenu();
		assert (Appview.getMenu() instanceof RankingMenu);
		UserMenusController.exit();
		assert (Appview.getMenu() instanceof MainMenu);
		MainMenuController.goToRankingMenu();
		Result result;
		result = UserMenusController.getPage(1);
		assert (result.getMessage().equals("1 test 1000\n"));
		result = UserMenusController.getPageCount();
		assert (result.getMessage().equals("1"));
	}

	@After
	public void tearDown() {
		User.getUsers().clear();
	}

}
