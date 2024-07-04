package server.view;

import message.Result;
import server.model.Client;

public interface Menuable {

	Result run(Client client, String command);
}
