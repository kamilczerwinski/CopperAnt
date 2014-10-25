package pl.edu.pk.iti.copperAnt.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clock {
	long currentTime;
	List<Event> events;

	public Clock() {
		this.currentTime = 0;
		events = new ArrayList<Event>();
	}

	public Event getEventFromList(int index) {
		return this.events.get(index);
	}

	public void addEvent(Event eventToAdd) {
		if (eventToAdd.getTime() > currentTime) {
			events.add(getCorrectIndex(eventToAdd), eventToAdd);
		}
	}

	public int getNumberOfWaitingEvent() {
		return this.events.size();
	}

	private int getCorrectIndex(Event eventToAdd) {
		int i = 0;
		Iterator<Event> iterator = events.iterator();
		while (iterator.hasNext()) {
			Event nextEvent = iterator.next();
			if (nextEvent.getTime() >= eventToAdd.getTime()) {
				break;
			}
			i++;
		}
		return i;
	}

}
