package pl.edu.pk.iti.copperAnt.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pk.iti.copperAnt.simulation.events.Event;

public class Clock {
	private static final Logger log = LoggerFactory.getLogger(Clock.class);
	private FinishCondition finishCondition = new EmptyListFinishCondition();

	long currentTime;

	List<Event> events;

	public Clock() {
		this.currentTime = -1;
		events = new ArrayList<Event>();
	}

	public Event getEventFromList(int index) {
		return this.events.get(index);
	}

	public void addEvent(Event eventToAdd) {
		log.debug("Added event: " + eventToAdd);
		if (eventToAdd.getTime() >= currentTime) {
			events.add(getCorrectIndex(eventToAdd), eventToAdd);
		}
	}

	public int getNumberOfWaitingEvent() {
		return this.events.size();
	}

	public void run() {
		while (!finishCondition.isSatisfied(this)) {
			tick();
		}

	}

	public void tick() {
		if (!finishCondition.isSatisfied(this)) {
			this.currentTime = events.get(0).getTime();
			while (!events.isEmpty() && events.get(0).getTime() == currentTime) {
				Event eventToRun = events.get(0);
				events.remove(eventToRun);
				eventToRun.run(this);
			}

		}
		log.debug("Tick. Size of list:" + events.size());
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

	public void setFinishCondition(FinishCondition finishCondition) {
		this.finishCondition = finishCondition;
	}

	public Clock withFinishCondition(FinishCondition finishCondition) {
		setFinishCondition(finishCondition);
		return this;
	}

	public long getCurrentTime() {
		return currentTime;
	}
}
