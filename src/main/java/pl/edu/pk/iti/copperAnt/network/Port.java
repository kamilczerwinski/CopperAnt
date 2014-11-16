package pl.edu.pk.iti.copperAnt.network;

import java.util.Random;

public class Port {

    Cable cable;
    final Device device;
    final String MAC;

    public Device getDevice() {
        return device;
    }

    public Port(Device device) {
        super();
        this.device = device;
        this.MAC = setMAC();
    }

    public Cable getCable() {
        return cable;
    }

    public String getMAC() {
        return MAC;
    }

    private String setMAC() {
        Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);

        // zero last 2 bytes to make it unicast and locally adminstrated
        macAddr[0] = (byte) (macAddr[0] & (byte) 254);

        StringBuilder sb = new StringBuilder(18);
        for (byte b : macAddr) {

            if (sb.length() > 0) {
                sb.append(":");
            }

            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public void conntectCalble(Cable cable) {
        if (this.cable == null) {
            this.cable = cable;
            cable.insertInto(this);
        }

    }

    public void disconnectCable() {
        if (this.cable != null) {
            Cable cableToDisconnect = this.cable;
            this.cable = null;
            cableToDisconnect.ejectFromPort(this);
        }
    }

    @Override
    public String toString() {
        return "[" + Integer.toHexString(hashCode()) + "]";
    }

}
