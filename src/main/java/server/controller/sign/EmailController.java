package server.controller.sign;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import message.Result;
import server.model.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class EmailController {

	public static boolean sendEmail(String to, String subject, String body, boolean isHtml) {
		final String username = "gopasttenseofgo@gmail.com";
		final String password = "icwx uork uhlz ltfj ";
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("gopasttenseofgo@gmail.com"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			if (isHtml) message.setContent(body, "text/html");
			else message.setText(body);
			Transport.send(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void runVerifier() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.createContext("/verify", new VerifyHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("Server started on port 8080");
	}

	static class VerifyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if ("GET".equals(exchange.getRequestMethod())) {
				Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
				String token = queryParams.get("token");
				System.out.println("kir to in :" + token);
				String response;
				if (verifyToken(token)) {
					response = "Email verified successfully!";
				} else {
					response = "Invalid or expired token.";
				}

				exchange.sendResponseHeaders(200, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}
		}

		private Map<String, String> parseQueryParams(String query) {
			Map<String, String> queryParams = new HashMap<>();
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				int idx = pair.indexOf("=");
				queryParams.put(pair.substring(0, idx), pair.substring(idx + 1));
			}
			return queryParams;
		}

		private boolean verifyToken(String token) {
			Client client = Client.getClient(token);
			if (client == null) return false;
			return RegisterMenusController.verify(token);
		}
	}
}
