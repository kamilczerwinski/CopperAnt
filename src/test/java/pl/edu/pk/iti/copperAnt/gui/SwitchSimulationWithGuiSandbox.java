package pl.edu.pk.iti.copperAnt.gui;

import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import pl.edu.pk.iti.copperAnt.network.Cable;
import pl.edu.pk.iti.copperAnt.network.Computer;
import pl.edu.pk.iti.copperAnt.network.IPAddress;
import pl.edu.pk.iti.copperAnt.network.Switch;
import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.MaxTimeFinishCondition;

public class SwitchSimulationWithGuiSandbox extends AbstractControlSandbox {

	@Override
	protected void addElements(Pane root) {
		SimulationCanvas simulationCanvas = new SimulationCanvas();
		root.getChildren().add(simulationCanvas);

		Clock clock = new Clock()
				.withFinishCondition(new MaxTimeFinishCondition(100));
		clock.setRealTime(true);
		clock.setTimeScale(100);
		Computer computer1 = new Computer(new IPAddress("192.168.1.1"), true);
		Computer computer2 = new Computer(new IPAddress("192.168.1.2"), true);
		Computer computer3 = new Computer(new IPAddress("192.168.1.3"), true);
		Switch switch_ = new Switch(3, clock, true);
		Cable cable = new Cable();
		cable.insertInto(computer1.getPort());
		cable.insertInto(switch_.getPort(0));
		Cable cable2 = new Cable();
		cable2.insertInto(computer2.getPort());
		cable2.insertInto(switch_.getPort(1));
		Cable cable3 = new Cable();
		cable3.insertInto(computer3.getPort());
		cable3.insertInto(switch_.getPort(2));
		simulationCanvas.addControl(switch_.getControl(), 100, 0);
		simulationCanvas.addControl(computer1.getControl(), 0, 200);
		simulationCanvas.addControl(computer2.getControl(), 100, 200);
		simulationCanvas.addControl(computer3.getControl(), 200, 200);

		computer1.initTrafic(clock);
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				clock.run();
				return null;
			}
		};
		new Thread(task).start();

	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}