package pl.edu.pk.iti.copperAnt.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;

public class Switch implements Device {

    private static final long DELAY = 1;
    private final List<Port> ports;
    private HashMap<String, Port> macTable;   // <MAC, Port>
    private Clock clock;
    private String managementIP;    // management IP

    public Switch(int numberOfPorts, Clock clock) {
        this.clock = clock;
        ports = new ArrayList<>(numberOfPorts);
        for (int i = 0; i < numberOfPorts; i++) {
            ports.add(new Port(this));
        }
        macTable = new HashMap<>();
    }

    public Port getPort(int portNumber) {
        return ports.get(portNumber);
    }

     /**
     * Add MAC & port to macTable
     *
     * @param MAC
     * @param port
     */
    private void addMACtoTable(String MAC, Port port) {
        macTable.put(MAC, port);
    }

    /**
     * Search for MAC & port in macTable
     *
     * @param MAC
     * @param Port
     * @return true or false
     */
    private boolean macLookup(String MAC, Port port) {
        if (macTable.containsKey(MAC)) {
            port = macTable.get(MAC);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Process incoming Package on receiving port and forward it
     *
     * @param pack - package
     * @param inPort - receiving port
    */
    @Override
    public void acceptPackage(Package pack, Port inPort) {
        String destinationMAC = pack.getDestinationMAC();
        String sourceMAC = pack.getSourceMAC();
        Port outPort = null;

        // Save source MAC & port to macTable, if it doesn't exist
        if (!macLookup(sourceMAC, inPort)) {
            addMACtoTable(sourceMAC, inPort);
        }

        // Search for MAC & port in macTable
        if (macLookup(destinationMAC, outPort)) {
            // Send through desired port
            pack.setSourceMAC(outPort.getMAC());
            clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + getDelay(),
                    outPort, pack));
        } else {
            // Send through all ports
            // TODO: add exception for source port
            for (Port port : ports) {
                pack.setSourceMAC(outPort.getMAC());
                clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + getDelay(),
                        port, pack));
            }
            // TODO: maybe some ACK that package was/wasn't delivered ?
        }
    }

    @Override
    public int getDelay() {
        // TODO Auto-generated method stub
        return 0;
    }

}
