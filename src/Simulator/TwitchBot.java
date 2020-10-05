package Simulator;

import UtterEng.Controller;
import UtterEng.UModel;
import org.jibble.pircbot.PircBot;

public class TwitchBot extends PircBot {

    private Controller controller;
    private Model model;
    public TwitchBot(Controller controller, Model model) {
        this.setName("AntBot");
        this.controller = controller;
        this.model = model;
    }


    public void onMessage(String channel, String sender,
                          String login, String hostname, String message) {

        //System.out.println("Msg rcv");
        /*if (message.equalsIgnoreCase("time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }
        */

        if (message.equals("!toggledebug")) {
            controller.toggleDebug();
        }
        if (message.equals("!gameinfo")) {
            sendMessage(channel, model.getInfo());
        }

        if (message.equals("!newant")) {
            System.out.println("creating ant!");
            model.createAnt();
        }

        if (message.startsWith("!spawn")) {
            String parts[] = message.split(" ");

            if (parts.length <= 1) {
                return;
            } else {

                int times = 1;

                if (parts.length == 3) {
                    if (Integer.parseInt(parts[2]) > 0) {
                        times = Integer.parseInt(parts[2]);
                    }
                }
                switch (parts[1]) {
                    case "lina": {
                        for (int i = 0; i < times; i++) {
                            model.createLina();
                        }
                        break;
                    }
                    case "ant": {
                        for (int i = 0; i < times; i++) {
                            model.createAnt();
                        }
                        break;
                    }
                    case "wictor": {
                        for (int i = 0; i < times; i++) {
                            model.createWictor();
                        }
                        break;
                    }
                    case "buscar": {
                        for (int i = 0; i < times; i++) {
                            model.createBush();
                        }
                        break;
                    }

                    case "smartant": {
                        for (int i = 0; i < times; i++) {
                            model.createSmartAnt();
                        }
                        break;
                    }
                }
            }
        }

        if (message.startsWith("!changename")) {
            String parts[] = message.split(" ");

            if (parts.length == 3) {
                model.changeName(parts[1], parts[2]);
            }
        }
        if (message.equals("!dance")) {
            model.allDance();
        }
        if (message.equals("!buscar")) {
            System.out.println("creating bush!");
            model.createBush();
        }

        if (message.equals("!lina")) {
            System.out.println("creating lina!");
            model.createLina();
        }

        if (message.equals("!wictor")) {
            System.out.println("creating wictor!");
            model.createWictor();
        }

        if (message.equals("!killall")) {
            model.killAllAnts();
        }

        if (message.equals("!reset")) {
            model.resetAll();
        }
    }

    @Override
    protected void onJoin(String channel, String sender, String login, String hostname) {
        sendMessage(channel, "Welcome " + sender + "!");
    }
}
