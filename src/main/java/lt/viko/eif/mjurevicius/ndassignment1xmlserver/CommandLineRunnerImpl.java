package lt.viko.eif.mjurevicius.ndassignment1xmlserver;

import lt.viko.eif.mjurevicius.ndassignment1xmlserver.service.ServerConnectorUnconnector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
                ServerConnectorUnconnector server = new ServerConnectorUnconnector();
                server.start(14242);
        }
}
