package alfonsoleon.course.server.backendserver.service;

import alfonsoleon.course.server.backendserver.enumeration.Status;
import alfonsoleon.course.server.backendserver.model.Server;
import alfonsoleon.course.server.backendserver.repo.ServerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Random;

@Transactional
@Service
@Slf4j
public class ServerServiceImpl implements ServerService{

    @Autowired
    private ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }


    @Override
    public List<Server> getServers(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("pinging server IP: {}",ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable
                (10000)? Status.SERVER_UP: Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Server getServer(Long id) {
        log.info("Fetching server with id {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server with id {}", id);
        serverRepo.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png"
        ,"server2.png"
        ,"server3.png"
        ,"server4.png"};

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/server/image/" + imageNames[new Random().nextInt(4)])
                .toUriString();
    }
}
