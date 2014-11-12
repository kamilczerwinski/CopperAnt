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
    private String m_IP;    // management IP
    private String m_MAC;   // management MAC (?)

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
     * Process incoming Package on Port and forward it
     *
     * @param pack
     * @param port
     */
    public void process(Package pack, Port sPort) {
        String destinationMAC = pack.getDestinationMAC();
        String sourceMAC = pack.getSourceMAC();
        Port destinationPort = null;

        // Save source MAC & port to macTable, if it doesn't exist
        if (!macLookup(sourceMAC, sPort)) {
            addMACtoTable(sourceMAC, sPort);
        }

        // Search for MAC & port in macTable
        if (macLookup(destinationMAC, destinationPort)) {
            // Send through desired port
            pack.setSourceMAC(destinationPort.device.getMac());
            clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + DELAY,
                    destinationPort, pack));
        } else {
            // Send through all ports
            // TODO: add exception for source port
            for (Port port : ports) {
                pack.setSourceMAC(port.device.getMac());
                clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + DELAY,
                        port, pack));
            }
            // TODO: maybe some ACK that package was/wasn't delivered ?
        }
    }
    /**
     * Add MAC & port to macTable
     * @param MAC
     * @param port 
     */
    private void addMACtoTable(String MAC, Port port) {
        macTable.put(MAC, port);
    }
    
    /**
     * Search for MAC & port in macTable
     * @param MAC
     * @param dPort
     * @return true or false
     */
    private boolean macLookup(String MAC, Port dPort) {
        if (macTable.containsKey(MAC)) {
            dPort = macTable.get(MAC);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void acceptPackage(Package pack) {
        // TODO: Merge process method
    }

    /**
     * Returns switch's management IP
     *
     * @return m_IP
     */
    @Override
    public String getIp() {
        return m_IP;
    }

    /**
     * Returns switch's management MAC
     *
     * @return m_IP
     */
    @Override
    public String getMac() {
        return m_MAC;
    }

}
