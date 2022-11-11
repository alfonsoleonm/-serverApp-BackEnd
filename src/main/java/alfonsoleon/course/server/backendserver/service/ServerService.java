package alfonsoleon.course.server.backendserver.service;

import alfonsoleon.course.server.backendserver.model.Server;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public interface ServerService {
    Server create(Server server);
    List<Server> getServers(int limit);
    Server ping(String ipAddress) throws IOException;
    Server getServer(Long id);
    Server update(Server server);
    Boolean delete(Long id);
}
