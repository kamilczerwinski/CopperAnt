package pl.edu.pk.iti.copperAnt.network;

import java.util.StringTokenizer;

public class IPAddress {
	private int[] ipParts = { 192, 168, 0, 1 };
	private int[] netmaskParts = { 255, 255, 255, 0 };

	public IPAddress(String ip, String netmask) {
		String[] ipParts = ip.split("\\.");
		for (int i = 0; i < 4; ++i) {
			this.ipParts[i] = Integer.parseInt(ipParts[i]);
		}
		String[] netmaskParts = netmask.split("\\.");
		for (int i = 0; i < 4; ++i) {
			this.netmaskParts[i] = Integer.parseInt(netmaskParts[i]);
		}

	}

	public IPAddress(String ip) {
		String[] ipParts = ip.split("\\.");
		for (int i = 0; i < 4; ++i) {
			this.ipParts[i] = Integer.parseInt(ipParts[i]);
		}
	}
	public IPAddress(IPAddress obj) {
		for (int i = 0; i< 4; i++) {
			ipParts[i] = obj.ipParts[i];
			netmaskParts[i] = obj.netmaskParts[i];
		}
	}

	public String toString() {
		StringBuilder ip = new StringBuilder();
		for (int i = 0, len = 4; i < len; ++i) {
			ip.append(ipParts[i] + ".");
		}
		return new String(ip.deleteCharAt(ip.length() - 1));
	}

	public String increment(int index) {
		ipParts[index - 1]++;
		return this.toString();
	}

	public String increment() {
		ipParts[3]++;
		return this.toString();
	}
	public String decrement() {
		ipParts[3]--;
		return this.toString();
	}

	public void set(int index, int value) {
		ipParts[index -1 ] = value;
	}
	
	public String getNetwork() {
		IPAddress tmp = new IPAddress(this);
		return tmp.decrement();
	}

	/**
	 * TCP/IP Address Utility Class
	 *
	 * @author gkspencer
	 */
	/**
	 * Check if the specified address is within the required subnet
	 * 
	 * @param ipaddr
	 *            String
	 * @param subnet
	 *            String
	 * @param mask
	 *            String
	 * @return boolean
	 */
	public final static boolean isInSubnet(String ipaddr, String subnet,
			String mask) {

		// Convert the addresses to integer values

		int ipaddrInt = parseNumericAddress(ipaddr);
		if (ipaddrInt == 0)
			return false;

		int subnetInt = parseNumericAddress(subnet);
		if (subnetInt == 0)
			return false;

		int maskInt = parseNumericAddress(mask);
		if (maskInt == 0)
			return false;

		// Check if the address is part of the subnet

		if ((ipaddrInt & maskInt) == subnetInt)
			return true;
		return false;
	}

	/**
	 * Check if the specified address is a valid numeric TCP/IP address and
	 * return as an integer value
	 * 
	 * @param ipaddr
	 *            String
	 * @return int
	 */
	public final static int parseNumericAddress(String ipaddr) {

		// Check if the string is valid

		if (ipaddr == null || ipaddr.length() < 7 || ipaddr.length() > 15)
			return 0;

		// Check the address string, should be n.n.n.n format

		StringTokenizer token = new StringTokenizer(ipaddr, ".");
		if (token.countTokens() != 4)
			return 0;

		int ipInt = 0;

		while (token.hasMoreTokens()) {

			// Get the current token and convert to an integer value

			String ipNum = token.nextToken();

			try {

				// Validate the current address part

				int ipVal = Integer.valueOf(ipNum).intValue();
				if (ipVal < 0 || ipVal > 255)
					return 0;

				// Add to the integer address

				ipInt = (ipInt << 8) + ipVal;
			} catch (NumberFormatException ex) {
				return 0;
			}
		}

		// Return the integer address

		return ipInt;
	}

}
