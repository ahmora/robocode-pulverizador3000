package pulverizador;

/**
 *
 * @author Alejandro Hernandez Mora
 */

import java.awt.Color;
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
    double [] parametros;
    Random random;

    public Pulverizador3002() {
        super();
        parametros = new double[35];
        readIndividuo();
        llenaAcciones();
        
    }

    public void readIndividuo() {
        Scanner in;
        String line = "";
        try {
            in = new Scanner(new FileReader("individuo2.txt"));
            line = in.nextLine();
            String [] vector=line.split(",");
            individuo= vector[0];
            for (int i = 0; i < parametros.length; i++) {
                parametros[i]= Double.parseDouble(vector[i+1]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pulverizador3000.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(line);

    }

    public void llenaAcciones() {
        String aux = individuo.substring(0);
        
        run = aux.substring(0, 6);

        onScannedRobot = aux.substring(6, 11);

        onHitByBullet = aux.substring(11, 16);

        onHitWall = aux.substring(16, 21);

        onBulletHit = aux.substring(21, 26);

        onBulletMissed = aux.substring(26, 31);

        onHitRobot = aux.substring(31);

    }

    public void realizaAccion(int accion, double parametro) {
        switch (accion) {
            case 0:
                ahead(parametro);
                break;
            case 1:
                back(parametro);
                break;
            case 2:
                fire(parametro);
                break;
            case 3:
                turnLeft(parametro);
                break;
            case 4:
                turnRight(parametro);
                break;
            case 5:
                turnRadarLeft(parametro);
                break;
            case 6:
                turnRadarRight(parametro);
                break;
            case 7:
                turnGunLeft(parametro);
                break;
            case 8:
                turnGunRight(parametro);
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
        setBodyColor(Color.blue);
        while (true) {
            for (int i = 0; i < run.length(); i++) {
                realizaAccion(run.charAt(i) - 48, parametros[i]);
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
            realizaAccion(onScannedRobot.charAt(i) - 48, parametros[i*2]);
        }
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Replace the next line with any behavior you would like
        for (int i = 0; i < onHitByBullet.length(); i++) {
            realizaAccion(onHitByBullet.charAt(i) - 48, parametros[i*3]);
        }
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    @Override
    public void onHitWall(HitWallEvent e) {
        // Replace the next line with any behavior you would like
        for (int i = 0; i < onHitWall.length(); i++) {
            realizaAccion(onHitWall.charAt(i) - 48, parametros[i*4]);
        }
    }
    
    
    
    @Override
    public void onBulletHit(BulletHitEvent e){
        for (int i = 0; i < onBulletHit.length(); i++) {
            realizaAccion(onBulletHit.charAt(i) - 48, parametros[i*5]);
        }
    }
    
     @Override
    public void onBulletMissed(BulletMissedEvent e){
        for (int i = 0; i < onBulletMissed.length(); i++) {
            realizaAccion(onBulletMissed.charAt(i) - 48, parametros[i*6]);
        }
    }
    
     @Override
    public void onHitRobot(HitRobotEvent e){
        for (int i = 0; i < onHitRobot.length(); i++) {
            realizaAccion(onHitRobot.charAt(i) - 48, parametros[i*7]);
        }
    }
    
    
    @Override
    public void onBattleEnded(BattleEndedEvent event){
        BattleResults results= event.getResults();
        int score = results.getScore();
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter("results2.txt"));
            individuo =individuo + "|" + score;
            pw.write(individuo);
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(Pulverizador3000.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
