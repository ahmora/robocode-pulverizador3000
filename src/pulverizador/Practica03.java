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
        while (i <= inicio) {
            aux = scan.nextLine();
            i++;
        }
        pw.write(aux);
        pw.close();

        pw = new PrintWriter(new FileWriter("individuo1.txt"));
        aux = scan.nextLine();
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

    }

    public static String getResults() throws FileNotFoundException {
        String resultados = "";
        Scanner scan = new Scanner(new FileReader("results.txt"));
        resultados += scan.nextLine() + "\n";

        scan = new Scanner(new FileReader("results1.txt"));
        resultados += scan.nextLine() + "\n";

        scan = new Scanner(new FileReader("results2.txt"));
        resultados += scan.nextLine() + "\n";

        scan = new Scanner(new FileReader("results3.txt"));
        resultados += scan.nextLine() + "\n";

        scan.close();
        return resultados;
    }

    public static void main(String[] args) {
        double p_cruza = 1, p_mutacion = 0.2;
        AlgoritmoGenetico ag = new AlgoritmoGenetico(100, p_cruza, p_mutacion);
        /*//Codigo para generar y escribir poblacion aleatoria
        Individuo [] poblacion=ag.generaPoblacion();
        try {
            ag.escribePoblacion("Generacion.txt", poblacion);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Practica03.class.getName()).log(Level.SEVERE, null, ex);
        }//Fin de código para generar poblacion aleatoria
        */
        
        Individuo[][] generacion_y_elitismo;
        Individuo[] poblacion, mejores;
        poblacion = ag.leePoblacion("Generacion.txt");
        mejores = ag.leePoblacion("Mejores.txt");

        //Inicio de ciclo de batallas(una generacion)
        int generaciones=0;
        while(generaciones<50){
            generacion_y_elitismo = ag.agVasconcelos(poblacion, mejores);
            poblacion = generacion_y_elitismo[0];
            int batallas = 0;
            String resultados = "";
            while (batallas < 25) {
                // Disable log messages from Robocode
                RobocodeEngine.setLogMessagesEnabled(false);

            // Create the RobocodeEngine
                //   RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
                RobocodeEngine engine = new RobocodeEngine(new java.io.File("/home/alex/robocode")); // Run from C:/Robocode

                // Add our own battle listener to the RobocodeEngine 
                engine.addBattleListener(new BattleObserver());

                // Show the Robocode battle view
                engine.setVisible(false);

                try {
                    acomodaArchivos((batallas * 4));

                    // Setup the battle specification
                    int numberOfRounds = 5;
                    BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
                    RobotSpecification[] selectedRobots = engine.getLocalRepository("pulverizador.Pulverizador3002*,pulverizador.Pulverizador3003*,"
                            + "pulverizador.Pulverizador3001*,pulverizador.Pulverizador3000*");

                    BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

                    // Run our specified battle and let it run till it is over
                    engine.runBattle(battleSpec, true); // waits till the battle finishes

                    resultados += getResults();
                } catch (IOException ioe) {
                    Logger.getLogger(Practica03.class.getName()).log(Level.SEVERE, null, ioe);
                }
                batallas++;

                // Cleanup our RobocodeEngine
                engine.close();
            }
            poblacion = ag.conviertePoblacion(resultados);
            
            mejores = generacion_y_elitismo[1];

            try {
                ag.escribePoblacion("Generacion.txt", poblacion);
                ag.escribePoblacion("Mejores.txt", mejores);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Practica03.class.getName()).log(Level.SEVERE, null, ex);
            }
            generaciones++;
            System.out.println("********************** GENERACION "+generaciones+" TERMINADA **********************");
        }//Fin de un ciclo de batallas(una generacion)
        
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
