package pl.edu.pk.iti.copperAnt.network;

public class Package {
    private String sourceMAC;
    private String destinationMAC;
    private String sourceIP;
    private String destinationIP;
    private int size = 0; // temporary value
    
    public String getSourceMAC() {
        return sourceMAC;
    }
    
    public String getDestinationMAC() {
        return destinationMAC;
    }
    
    public String getSourceIP() {
        return sourceIP;
    }
    
    public String getDestinationIP() {
        return destinationIP;
    }
    
    public void setSourceMAC(String sMAC) {
        this.sourceMAC = sMAC;
    }

    public void setDestinationMAC(String dMAC) {
        this.destinationMAC = dMAC;
    }
    
    public void setSourceIP(String sIP) {
        this.sourceIP = sIP;
    }
    
    public void setDestinationIP(String dIP) {
        this.destinationIP = dIP;
    }
    
    public int getSize() {
        // TODO wykorzystać do obliczania opóźnień
        return size;
    }
}
