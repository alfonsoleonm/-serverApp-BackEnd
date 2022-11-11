package alfonsoleon.course.server.backendserver.repo;

import alfonsoleon.course.server.backendserver.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);
}
