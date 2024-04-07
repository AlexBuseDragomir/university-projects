package cds.java.project.main;

import cds.java.project.workshop.NorthPoleWorkshop;

/**
 * Main class of the program, place in which we do only a few things in
 * order to get the whole structure of classes up and running
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
public class MainLauncher {

    /**
     * Main entry, the main of the project;
     * we create a new NorthPoleWorkshop instance and start it
     */
    public static void main(String[] args) {

        NorthPoleWorkshop northPoleWorkshop2018 = new NorthPoleWorkshop();
        northPoleWorkshop2018.start();
    }
}
