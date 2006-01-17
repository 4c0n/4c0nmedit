package org.nomad.patch;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.nomad.patch.Cables.CableType;


public class Cable extends JLabel {
    /**
     * 0 6 0 0 4 0 1 = colour, module, connector, in (0), module, connector, in
     * (0) or uit (1). v3.0: first connector must be an input
     */

    // .pch info
    private int colour;

    private int beginArray[], endArray[];

    // .pch info

    private double d2, d3 = 0;

    Cable(String params) {
        super();
        // [0] = module index, [1] = connector index, [2] = input or output

        beginArray = new int[3];
        endArray = new int[3];

        String[] paramArray = new String[7];
        paramArray = params.split(" ");

        colour = Integer.parseInt(paramArray[0]);

        // The 4th parameter must be a 0(in), the 7th can be an in(0) or an
        // out(1)
        beginArray[0] = Integer.parseInt(paramArray[1]);
        beginArray[1] = Integer.parseInt(paramArray[2]);
        beginArray[2] = Integer.parseInt(paramArray[3]);
        if (beginArray[2] != 0)
            System.out
                    .println("IN CONNECTOR EXPECTED!!! PATCH NON 3.0 complient!");

        endArray[0] = Integer.parseInt(paramArray[4]);
        endArray[1] = Integer.parseInt(paramArray[5]);
        endArray[2] = Integer.parseInt(paramArray[6]);

        init();
    }

    Cable(int newBeginMod, int newBeginConnector, int newBeginConnectorType,
            int newEndMod, int newEndConnector, int newEndConnectorType) {
        beginArray = new int[3];
        endArray = new int[3];

        setCableData(newBeginMod, newBeginConnector, newBeginConnectorType,
                newEndMod, newEndConnector, newEndConnectorType,
                CableType.LOOSE);

        init();
    }

    Cable(int newType) {
        beginArray = new int[3];
        endArray = new int[3];

        colour = newType;

        init();
    }

    public void init() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setOpaque(false);

        double r = Math.random();
        if (r < 1)
            d2 = 1.2;
        if (r < 0.8)
            d2 = 1.4;
        if (r < 0.6)
            d2 = 1.6;
        if (r < 0.4)
            d2 = 2;
        if (r < 0.2)
            d2 = 3;

        r = Math.random();
        if (r < 1)
            d3 = 1.2;
        if (r < 0.8)
            d3 = 1.4;
        if (r < 0.6)
            d3 = 1.6;
        if (r < 0.4)
            d3 = 2;
        if (r < 0.2)
            d3 = 3;
    }

    public void setCableData(int newInMod, int newInConnector, int newInInput,
            int newOutMod, int newOutConnector, int newOutInput, int newType) {

        if (newInMod > -1) { // if -1, keep the old connector values
            beginArray[0] = newInMod;
            beginArray[1] = newInConnector;
            beginArray[2] = newInInput;
        }
        if (newOutMod > -1) { // if -1, keep the old connector values
            endArray[0] = newOutMod;
            endArray[1] = newOutConnector;
            endArray[2] = newOutInput; // In of Uit
        }

        if (newInInput == 1) {// swap when there is an out connector in the
                                // first connection (v3.0)
            int newTempMod, newTempConnector, newTempInput = -1;

            newTempMod = beginArray[0];
            newTempConnector = beginArray[1];
            newTempInput = beginArray[2];

            beginArray[0] = endArray[0];
            beginArray[1] = endArray[1];
            beginArray[2] = endArray[2];

            endArray[0] = newTempMod;
            endArray[1] = newTempConnector;
            endArray[2] = newTempInput;
        }

        colour = newType;
    }

    // Getters

    public int getBeginModule() {
        return beginArray[0];
    }

    public int getBeginConnector() {
        return beginArray[1];
    }

    public int getBeginConnectorType() {
        return beginArray[2];
    }

    public int getEndModule() {
        return endArray[0];
    }

    public int getEndConnector() {
        return endArray[1];
    }

    public int getEndConnectorType() {
        return endArray[2];
    }

    public int getColor() {
        return colour;
    }

    public String getName() {
        switch (colour) {
        case CableType.AUDIO:
            return "Audio"; // 24bit, min = -64, max = +64 - 96kHz.
        case CableType.CNTRL:
            return "Control"; // 24bit, min = -64, max = +64 - 24kHz.
        case CableType.LOGIC:
            return "Logic"; // 1bit, low = 0, high = +64.
        case CableType.SLAVE:
            return "Slave"; // frequentie referentie between master and slave
                            // modules
        case CableType.USER1:
            return "User1";
        case CableType.USER2:
            return "User2";
        case CableType.LOOSE:
            return "Loose";
        default:
            return "Wrong type...";
        }
    }

    // Setters

    public void setBeginModule(int i) {
        beginArray[0] = i;
    }

    public void setBeginConnector(int i) {
        beginArray[1] = i;
    }

    public void setBeginConnectorType(int i) {
        beginArray[2] = i;
    }

    public void setEndModule(int i) {
        endArray[0] = i;
    }

    public void setEndConnector(int i) {
        endArray[1] = i;
    }

    public void setEndConnectorType(int i) {
        endArray[2] = i;
    }

    public void setColor(int i) {
        colour = i;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

//        Graphics2D g2d = (Graphics2D) graphics;
//
//        g2d.setColor(Color.DARK_GRAY);
//        g2d.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
    }
}