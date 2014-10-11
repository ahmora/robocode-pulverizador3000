/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pulverizador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.control.*;
import robocode.control.events.*;

/**
 *
 * @author Alejandro Hernandez Mora
 */
public class Practica03 {

    public static void acomodaArchivos(int inicio) throws IOException {
        int i = 0;
        String aux = "";
        PrintWriter pw = new PrintWriter(new FileWriter("individuo.txt"));
        Scanner scan = new Scanner(new FileReader("Generacion.txt"));
        while (i < inicio) {
            scan.nextLine();
            i++;
        }
        scan.nextLine();
        pw.write(aux);
        pw.close();

        pw = new PrintWriter(new FileWriter("individuo2.txt"));
        aux = scan.nextLine();
        pw.write(aux);
        pw.close();

        pw = new PrintWriter(new FileWriter("individuo3.txt"));
        aux = scan.nextLine();
        pw.write(aux);
        pw.close();

        pw = new PrintWriter(new FileWriter("individuo4.txt"));
        aux = scan.nextLine();
        pw.write(aux);
        pw.close();

    }

    public static String getResults() throws FileNotFoundException {
        String resultados = "";
        Scanner scan = new Scanner(new FileReader("individuo.txt"));
        resultados += scan.nextLine();

        scan = new Scanner(new FileReader("individuo2.txt"));
        resultados += scan.nextLine();

        scan = new Scanner(new FileReader("individuo3.txt"));
        resultados += scan.nextLine();

        scan = new Scanner(new FileReader("individuo4.txt"));
        resultados += scan.nextLine();
        return resultados;
    }

    public static void main(String[] args) {
        double p_cruza = 0.3, p_mutacion = 0.05;
        AlgoritmoGenetico ag = new AlgoritmoGenetico(100, p_cruza, p_mutacion);

        // Disable log messages from Robocode
        RobocodeEngine.setLogMessagesEnabled(false);

        // Create the RobocodeEngine
        //   RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
        RobocodeEngine engine = new RobocodeEngine(new java.io.File("/home/alex/robocode")); // Run from C:/Robocode

        // Add our own battle listener to the RobocodeEngine 
        engine.addBattleListener(new BattleObserver());

        // Show the Robocode battle view
        engine.setVisible(false);
        int batallas = 0;
        String resultados = "";
        while (batallas < 26) {
            try {
                    acomodaArchivos((batallas * 4)+1);
                
                // Setup the battle specification
                int numberOfRounds = 5;
                BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
                RobotSpecification[] selectedRobots = engine.getLocalRepository("pulverizador.Pulverizador3002*,pulverizador.Pulverizador3003*,"
                        + "pulverizador.Pulverizador3001*,pulverizador.Pulverizador3000*");

                BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

                // Run our specified battle and let it run till it is over
                engine.runBattle(battleSpec, true); // waits till the battle finishes

                resultados += getResults();
            } catch( IOException ioe){
                Logger.getLogger(Practica03.class.getName()).log(Level.SEVERE, null, ioe);
            }
            batallas++;
        }
        System.out.println(resultados);
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
