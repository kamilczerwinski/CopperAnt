package pl.edu.pk.iti.copperAnt.simulation;

import pl.edu.pk.iti.copperAnt.network.Package;


public class StatisticData {
	private long time;
	private Package triggeredPackage;
	
	public StatisticData(long time, Package triggeredPackage){
		super();
		this.time = time;
		this.triggeredPackage = triggeredPackage;		
	}


	public long getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Package getTriggeredPackage() {
		return triggeredPackage;
	}

	public void setTriggeredPackage(Package triggeredPackage) {
		this.triggeredPackage = triggeredPackage;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatisticData other = (StatisticData) obj;
		if (time != other.time)
			return false;
		if (triggeredPackage == null) {
			if (other.triggeredPackage != null)
				return false;
		} else if (!triggeredPackage.equals(other.triggeredPackage))
			return false;
		return true;
	}

	
	
	

}
