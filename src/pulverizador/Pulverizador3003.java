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
public class Pulverizador3003 extends Robot {

    String run, onScannedRobot, onHitByBullet, onHitWall, onBulletHit, onBulletMissed, onHitRobot;
    String individuo;
    double [] parametros;
    Random random;

    public Pulverizador3003() {
        super();
        parametros = new double[35];
        readIndividuo();
        llenaAcciones();
        
    }

    public void readIndividuo() {
        Scanner in;
        String line = "";
        try {
            in = new Scanner(new FileReader("individuo3.txt"));
            line = in.nextLine();
            String [] vector=line.split("&");
            line=vector[0];
            vector=line.split(",");
            individuo= vector[0];
            for (int i = 0; i < parametros.length; i++) {
                parametros[i]= Double.parseDouble(vector[i+1]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pulverizador3000.class.getName()).log(Level.SEVERE, null, ex);
        }
       

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

    public void realizaAccion(int accion, double parametro,Object e,int opcion) {
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
                switch(opcion){
                    
                    case 1:
                        ScannedRobotEvent t1 = (ScannedRobotEvent)e;
                        if(parametro < 0){
                            turnLeft(t1.getBearing());
                        }else{
                            turnLeft(parametro);
                        }
                        break;
                        
                    case 2:
                        HitByBulletEvent t2 = (HitByBulletEvent)e;
                        if(parametro < 0){
                            turnLeft(t2.getBearing());
                        }else{
                            turnLeft(parametro);
                        }
                        break;
                        
                    default:
                        turnLeft(parametro);
                        break;
                }
                //turnLeft(parametro);
                break;
            case 4:
                switch(opcion){
                    
                    case 1:
                        ScannedRobotEvent t1 = (ScannedRobotEvent)e;
                        if(parametro < 0){
                            turnRight(t1.getBearing());
                        }else{
                            turnRight(parametro);
                        }
                        break;
                        
                    case 2:
                        HitByBulletEvent t2 = (HitByBulletEvent)e;
                        if(parametro < 0){
                            turnRight(t2.getBearing());
                        }else{
                            turnRight(parametro);
                        }
                        break;
                        
                    default:
                        turnRight(parametro);
                        break;
                }
                //turnRight(parametro);
                break;
            case 5:
                switch(opcion){
                    
                    case 1:
                        ScannedRobotEvent t1 = (ScannedRobotEvent)e;
                        if(parametro < 0){
                            turnRadarLeft(t1.getBearing());
                        }else{
                            turnRadarLeft(parametro);
                        }
                        break;
                        
                    case 2:
                        HitByBulletEvent t2 = (HitByBulletEvent)e;
                        if(parametro < 0){
                            turnRadarLeft(t2.getBearing());
                        }else{
                            turnRadarLeft(parametro);
                        }
                        break;
                        
                    default:
                        turnRadarLeft(parametro);
                        break;
                }
                //turnRadarLeft(parametro);
                break;
            case 6:
                 switch(opcion){
                    
                    case 1:
                        ScannedRobotEvent t1 = (ScannedRobotEvent)e;
                        if(parametro < 0){
                            turnRadarRight(t1.getBearing());
                        }else{
                            turnRadarRight(parametro);
                        }
                        break;
                        
                    case 2:
                        HitByBulletEvent t2 = (HitByBulletEvent)e;
                        if(parametro < 0){
                            turnRadarRight(t2.getBearing());
                        }else{
                            turnRadarRight(parametro);
                        }
                        break;
                        
                    default:
                        turnRadarRight(parametro);
                        break;
                }
                //turnRadarRight(parametro);
                break;
            case 7:
                switch(opcion){
                    
                    case 1:
                        ScannedRobotEvent t1 = (ScannedRobotEvent)e;
                        if(parametro < 0){
                            turnGunLeft(t1.getBearing());
                        }else{
                            turnGunLeft(parametro);
                        }
                        break;
                        
                    case 2:
                        HitByBulletEvent t2 = (HitByBulletEvent)e;
                        if(parametro < 0){
                            turnGunLeft(t2.getBearing());
                        }else{
                            turnGunLeft(parametro);
                        }
                        break;
                        
                    default:
                        turnGunLeft(parametro);
                        break;
                }
                //turnGunLeft(parametro);
                break;
            case 8:
                switch(opcion){
                    
                    case 1:
                        ScannedRobotEvent t1 = (ScannedRobotEvent)e;
                        if(parametro < 0){
                            turnGunRight(t1.getBearing());
                        }else{
                            turnGunRight(parametro);
                        }
                        break;
                        
                    case 2:
                        HitByBulletEvent t2 = (HitByBulletEvent)e;
                        if(parametro < 0){
                            turnGunRight(t2.getBearing());
                        }else{
                            turnGunRight(parametro);
                        }
                        break;
                        
                    default:
                        turnGunRight(parametro);
                        break;
                }
                //turnGunRight(parametro);
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
        setBodyColor(Color.orange);
        while (true) {
            for (int i = 0; i < run.length(); i++) {
                realizaAccion(run.charAt(i) - 48, parametros[i],null,0);
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
            realizaAccion(onScannedRobot.charAt(i) - 48, parametros[i*2],e,1);
        }
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Replace the next line with any behavior you would like
        for (int i = 0; i < onHitByBullet.length(); i++) {
            realizaAccion(onHitByBullet.charAt(i) - 48, parametros[i*3],e,2);
        }
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    @Override
    public void onHitWall(HitWallEvent e) {
        // Replace the next line with any behavior you would like
        for (int i = 0; i < onHitWall.length(); i++) {
            realizaAccion(onHitWall.charAt(i) - 48, parametros[i*4],e,3);
        }
    }
    
    
    
    @Override
    public void onBulletHit(BulletHitEvent e){
        for (int i = 0; i < onBulletHit.length(); i++) {
            realizaAccion(onBulletHit.charAt(i) - 48, parametros[i*5],e,4);
        }
    }
    
     @Override
    public void onBulletMissed(BulletMissedEvent e){
        for (int i = 0; i < onBulletMissed.length(); i++) {
            realizaAccion(onBulletMissed.charAt(i) - 48, parametros[i*6],e,5);
        }
    }
    
     @Override
    public void onHitRobot(HitRobotEvent e){
        for (int i = 0; i < onHitRobot.length(); i++) {
            realizaAccion(onHitRobot.charAt(i) - 48, parametros[i*7],e,6);
        }
    }
    
    
    @Override
    public void onBattleEnded(BattleEndedEvent event){
        BattleResults results= event.getResults();
        int score = results.getScore();
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter("results3.txt"));
            String s = ",";
            for (int i = 0; i < 35; i++) {
                s += parametros[i];
                s += i == 34 ? "" : ",";
            }
            individuo =individuo +s+ "&" + score;
            pw.write(individuo);
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(Pulverizador3000.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
