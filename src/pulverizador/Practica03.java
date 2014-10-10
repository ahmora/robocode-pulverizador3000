/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pulverizador;

import robocode.control.*;
import robocode.control.events.*;

/**
 *
 * @author Alejandro Hernandez Mora
 */
public class Practica03 {

    public static void main(String[] args) {
        Individuo a= new Individuo();
        Individuo b= new Individuo();
        Individuo c= new Individuo();
        Individuo d= new Individuo();
        
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        
        // Disable log messages from Robocode
        RobocodeEngine.setLogMessagesEnabled(false);

         // Create the RobocodeEngine
        //   RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
        RobocodeEngine engine = new RobocodeEngine(new java.io.File("/home/alex/robocode")); // Run from C:/Robocode

        // Add our own battle listener to the RobocodeEngine 
        engine.addBattleListener(new BattleObserver());

        // Show the Robocode battle view
        engine.setVisible(false);
        int generaciones = 0;
        while (generaciones < 3) {
            // Setup the battle specification
            int numberOfRounds = 5;
            BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
            RobotSpecification[] selectedRobots = engine.getLocalRepository("pulverizador.Pulverizador3002*,pulverizador.Pulverizador3003*,"
                    + "pulverizador.Pulverizador3001*,pulverizador.Pulverizador3000*");

            BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

            // Run our specified battle and let it run till it is over
            engine.runBattle(battleSpec, true); // waits till the battle finishes
            generaciones++;
        }
        // Cleanup our RobocodeEngine
        engine.close();


        // Make sure that the Java VM is shut down properly
        System.exit(0);
        
    }
}

 //
// Our private battle listener for handling the battle event we are interested in.
//
class BattleObserver extends BattleAdaptor {

    // Called when the battle is completed successfully with battle results
    public void onBattleCompleted(BattleCompletedEvent e) {
        System.out.println("-- Battle has completed --");

        // Print out the sorted results with the robot names
        System.out.println("Battle results:");
        for (robocode.BattleResults result : e.getSortedResults()) {
            System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
        }
    }

    // Called when the game sends out an information message during the battle
    public void onBattleMessage(BattleMessageEvent e) {
        System.out.println("Msg> " + e.getMessage());
    }

    // Called when the game sends out an error message during the battle
    public void onBattleError(BattleErrorEvent e) {
        System.out.println("Err> " + e.getError());
    }
    
    
    
}
