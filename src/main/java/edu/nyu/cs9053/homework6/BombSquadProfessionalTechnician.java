package edu.nyu.cs9053.homework6;

import java.util.Map;
import java.util.List;
import java.lang.reflect.Field;

/**
 * User: Hongjian
 * Date: 10/19/19
 * Time: 9:35 PM
 */
public class BombSquadProfessionalTechnician implements BombSquadTechnician {

    private final Toolbox toolbox = new Toolbox();

    @Override public WireDiagram assess(Bomb bomb) {
        return new WireDiagram() {
            @Override public TripWire getTripWires() {
                Wire[] wires= bomb.getWires();
                Wire blueWire = null;
                Wire redWire = null;
                try {
                    for (Wire wire: wires) {
                        if (toolbox.getColor(wire) == WireColor.Blue) blueWire = wire;
                        if (toolbox.getColor(wire) == WireColor.Red) redWire = wire;
                    }
                } catch (ToolMisuseException tme) {
                    tme.printStackTrace();
                }
                return new TripWire(blueWire, redWire);
            }
        };
    }

    @Override public void defuse(Bomb bomb, WireDiagram diagram) {
        Wire[] wires= bomb.getWires();
        Wire blueWire = diagram.getTripWires().getBlueWire();
        Wire redWire = diagram.getTripWires().getRedWire();
        EvilVillain villain = null;

//        This is another tricky way to complete this homework quickly : Map<Bomb, List<WireColor>> orderCuts = new HashMap<>(); toolbox.setField(villain, "orderCuts", orderCuts);

        try {
            for (Wire wire: wires) {
                if (toolbox.invokeMethod(wire, "callingCard") instanceof EvilVillain) {
                    villain = (EvilVillain) toolbox.invokeMethod(wire, "callingCard");
                    break;
                }
            }

            if (villain == null) {
                throw new NullPointerException();
            }

            toolbox.setField(villain, "free", false);

            Field privateOrderCuts = EvilVillain.class.getDeclaredField("orderCuts");
            privateOrderCuts.setAccessible(true);
            Map<Bomb, List<WireColor>> orderCutsValue = (Map<Bomb, List<WireColor>>) privateOrderCuts.get(villain);
            List<WireColor> orderCuts = orderCutsValue.get(bomb);

            while (orderCuts.size() > 0) {
                if (orderCuts.get(0) == WireColor.Blue) {
                    blueWire.cut();
                    continue;
                }
                if (orderCuts.get(0) == WireColor.Red) {
                    redWire.cut();
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException | ClassCastException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

}