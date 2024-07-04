package server.view;

import message.Command;
import message.Result;

public interface Menuable {

	Result run(Command command);
}
