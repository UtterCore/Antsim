package Simulator;

import UtterEng.*;
import org.jibble.pircbot.IrcException;

import java.io.IOException;

public class Simulator {

    public static void main(String[] args) {

        try {
            new Controller(new Model(), 20 * 80, 20 * 44);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
