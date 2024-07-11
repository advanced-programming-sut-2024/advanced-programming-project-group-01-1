import controller.MainMenuController;
import controller.sign.LoginMenusController;
import controller.sign.RegisterMenusController;
import model.Result;
import model.user.Question;
import model.user.User;
import org.junit.After;
import org.junit.Test;
import view.Appview;
import view.sign.login.LoginMenu;

public class TestSign {

	@Test
	public void testRegister() {
		Result result;
		Appview.setMenu(new LoginMenu());
		result = LoginMenusController.goToRegisterMenu();
		assert (result.isSuccessful());
		result = RegisterMenusController.register("test", "test", "test", "test", "test");
		assert (result.getMessage().equals("Invalid email"));
		result = RegisterMenusController.register("test", "te st", "t est", "test", "sepehr.s2speed@gmail.com");
		assert (result.getMessage().equals("Invalid password"));
		result = RegisterMenusController.register("test", "test", "test", "test", "sepehr.s2speed@gmail.com");
		assert (result.getMessage().equals("Weak password"));
		result = RegisterMenusController.register("test", "@Ali1234", "test", "test", "sepehr.s2speed@gmail.com");
		assert (result.getMessage().equals("Passwords don't match"));
		result = RegisterMenusController.register("test", "@Ali1234", "@Ali1234", "test", "sepehr.s2speed@gmail.com");
		assert (result.isSuccessful());
		result = RegisterMenusController.pickQuestion(0, "test", "test");
		assert (result.isSuccessful());
		LoginMenusController.goToRegisterMenu();
		result = RegisterMenusController.register("test", "@Ali1234", "@Ali1234", "test", "sepehr.s2speed@gmail.com");
		assert (result.getMessage().equals("Username is already taken"));
		RegisterMenusController.exit();
		assert (Appview.getMenu() instanceof LoginMenu);
	}

	@Test
	public void testLogin() {
		Appview.setMenu(new LoginMenu());
		Result result = LoginMenusController.login("test", "test", false);
		assert (result.getMessage().equals("Username doesn't exist"));
		User.getUsers().add(new User("test", "test", "@Ali1234", "test", new Question("test", "test")));
		result = LoginMenusController.login("test", "test", false);
		assert (result.getMessage().equals("Password is incorrect"));
		result = LoginMenusController.login("test", "@Ali1234", false);
		assert (result.isSuccessful());
		result = MainMenuController.logout();
		assert (result.isSuccessful());
		result = LoginMenusController.forgotPassword("toast");
		assert (result.getMessage().equals("Username doesn't exist"));
		result = LoginMenusController.forgotPassword("test");
		assert (result.isSuccessful());
		result = LoginMenusController.answerQuestion("toast");
		assert (result.getMessage().equals("Answer is incorrect"));
		result = LoginMenusController.answerQuestion("test");
		assert (result.isSuccessful());
		result = LoginMenusController.setPassword("test");
		assert (result.getMessage().equals("Password is weak"));
		result = LoginMenusController.setPassword("@Test1234");
		assert (result.isSuccessful());
		assert (Appview.getMenu() instanceof LoginMenu);
	}

	@After
	public void after() {
		User.getUsers().clear();
	}

}
