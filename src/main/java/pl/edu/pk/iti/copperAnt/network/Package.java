package pl.edu.pk.iti.copperAnt.network;

public class Package {
    private String sourceMAC;
    private String destinationMAC;
    private String sourceIP;
    private String destinationIP;
    private int ttl = 10;
    private String content;
    private String header;
    private PackageType type = PackageType.ECHO_REQUEST;
    
    public Package(PackageType type, String content){
    	this.type = type;
    	this.content = content;
    	this.header = null;
    	
    }
    public Package(PackageType type, String header, String content){
    	this.type = type;
    	this.content = content;
    	this.header = header;
    	
    }
    public String getContent() {
    	return content;
    }
    public String getHeader() {
    	return header;
    }
    public PackageType getType() {
    	return type;
    }
    public void setType(PackageType type) {
    	this.type = type;
    }
    public Package() {
    	
    }
   
    public boolean validTTL() {
    	return --ttl > 0;
    }
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
    	// + 1 Because it always has 'header'
    	return content.length() + 1;
    	
    }
}
