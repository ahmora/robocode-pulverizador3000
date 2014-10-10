package pulverizador;

/**
 *
 * @author Alejandro Hernandez Mora
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.*;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html
/**
 * Alex - a robot by (your name here)
 */
public class Pulverizador3002 extends Robot {

    String run, onScannedRobot, onHitByBullet, onHitWall, onBulletHit, onBulletMissed, onHitRobot;
    String individuo;
    Random random;

    public Pulverizador3002() {
        super();
        individuo = readIndividuo();
        llenaAcciones();
    }

    public String readIndividuo() {
        Scanner in;
        String line = "";
        try {
            in = new Scanner(new FileReader("individuo.txt"));
            line = in.nextLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pulverizador3000.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(line);

        return line;
    }

    public void llenaAcciones() {
        String aux = individuo.substring(0);
        int longitud_gen = aux.charAt(0) - 48;
        run = aux.substring(1, longitud_gen + 1);
        aux = aux.substring(longitud_gen + 1);
        longitud_gen = aux.charAt(0) - 48;

        onScannedRobot = aux.substring(1, longitud_gen + 1);
        aux = aux.substring(longitud_gen + 1);
        longitud_gen = aux.charAt(0) - 48;

        onHitByBullet = aux.substring(1, longitud_gen + 1);
        aux = aux.substring(longitud_gen + 1);
        longitud_gen = aux.charAt(0) - 48;

        onHitWall = aux.substring(1, longitud_gen + 1);
        aux = aux.substring(longitud_gen + 1);
        longitud_gen = aux.charAt(0) - 48;

        onBulletHit = aux.substring(1, longitud_gen + 1);
        aux = aux.substring(longitud_gen + 1);
        longitud_gen = aux.charAt(0) - 48;

        onBulletMissed = aux.substring(1, longitud_gen + 1);
        aux = aux.substring(longitud_gen + 1);
        longitud_gen = aux.charAt(0) - 48;

        onHitRobot = aux.substring(1, longitud_gen + 1);

    }

    public void realizaAccion(int accion) {
        switch (accion) {
            case 0:
                ahead(100);
                System.out.println(0);
                break;
            case 1:
                back(100);
                break;
            case 2:
                fire(10);
                break;
            case 3:
                fire(2);
                break;
            case 4:
                turnGunRight(70);
                break;
            case 5:
                turnGunLeft(70);
                break;
            case 6:
                turnRadarLeft(30);
                break;
            case 7:
                turnRadarRight(50);
                break;
            case 8:
                turnLeft(50);
                break;
            case 9:
                turnRight(50);
                break;
            default:
                doNothing();
        }
    }

    /**
     * run: Alex's default behavior
     */
    @Override
    public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
        // and the next line:
		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar
        // Robot main loop
        while (true) {
            for (int i = 0; i < run.length(); i++) {
                realizaAccion(run.charAt(i) - 48);
            }
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        // Replace the next line with any behavior you would like
        for (int i = 0; i < onScannedRobot.length(); i++) {
            realizaAccion(onScannedRobot.charAt(i) - 48);
        }
        //fire(1);
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Replace the next line with any behavior you would like
        for (int i = 0; i < onHitByBullet.length(); i++) {
            realizaAccion(onHitByBullet.charAt(i) - 48);
        }
        //back(10);
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    @Override
    public void onHitWall(HitWallEvent e) {
        // Replace the next line with any behavior you would like
        for (int i = 0; i < onHitWall.length(); i++) {
            realizaAccion(onHitWall.charAt(i) - 48);
        }
    }
    
    @Override
    public void onBattleEnded(BattleEndedEvent event){
        BattleResults results= event.getResults();
        int score = results.getScore();
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter("results.txt"));
            individuo = individuo + "," + score;
            pw.write(individuo);
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(Pulverizador3000.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void onBulletHit(BulletHitEvent e){
        for (int i = 0; i < onBulletHit.length(); i++) {
            realizaAccion(onBulletHit.charAt(i) - 48);
        }
    }
    
     @Override
    public void onBulletMissed(BulletMissedEvent e){
        for (int i = 0; i < onBulletMissed.length(); i++) {
            realizaAccion(onBulletMissed.charAt(i) - 48);
        }
    }
    
     @Override
    public void onHitRobot(HitRobotEvent e){
        for (int i = 0; i < onHitRobot.length(); i++) {
            realizaAccion(onHitRobot.charAt(i) - 48);
        }
    }
    
    
}
