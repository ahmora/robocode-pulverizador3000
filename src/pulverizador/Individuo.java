/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pulverizador;

import java.util.Random;

/**
 *
 * @author Alejandro Hernandez Mora
 */
public class Individuo implements Comparable {
    Random r = new Random();
    double fitness;
    String individuo;
    int [] variables;

    public Individuo(){
        this.variables=new int [7];
        for (int i = 0; i < variables.length; i++) {
            variables[i]=r.nextInt(6)+1;
        }
        individuo="";
        for (int i = 0; i < variables.length; i++) {
            for (int j = 0; j < variables[i]; j++) {
                individuo+=(char)(48 +r.nextInt(10));
            }
            individuo+= i==variables.length? ',':"";
        }
    }
    
    public Individuo(String individuo) {
        this.individuo = individuo;
        
    }

    
    public Individuo[] cruzar(Individuo a, double p_cruza){
        Individuo[] hijos = new Individuo[2];
        double p=r.nextDouble();
        if (p < p_cruza) {
            int cruce = r.nextInt(individuo.length()), l = r.nextInt(individuo.length());
            int inicio, fin;
            if (cruce < l) {
                inicio = cruce;
                fin = l;
            } else {
                inicio = l;
                fin = l;
            }
            String h2 = "", h1 = this.individuo.substring(0, inicio);
            h1 += a.individuo.substring(inicio, fin);
            h1 += this.individuo.substring(fin, individuo.length());

            h2 = a.individuo.substring(0, inicio);
            h2 += this.individuo.substring(inicio, fin);
            h2 += a.individuo.substring(fin, individuo.length());

            hijos[0] = new Individuo(h1);
            hijos[1] = new Individuo(h2);
            
             /*System.out.println("inicio: " + inicio+" fin: "+fin);
             System.out.println("Estos son los padres:");
             this.imprime();
             a.imprime();
             System.out.println("Estos son los hijos:");
             hijos[0].imprime();
             hijos[1].imprime();*/
             
        } else {
            hijos[0] = this;
            hijos[1] = a;
        }
        return hijos;

    }
    
    public void mutar(){
        int i=r.nextInt(individuo.length());
        char caracter= (char) (48 + r.nextInt(10));
        while(individuo.charAt(i)==','){
            i = r.nextInt(individuo.length());
        }
        individuo= individuo.substring(0,i)+caracter+individuo.substring(i+1);
    }
    
    @Override 
    public String toString(){
        return individuo;
    }
    public boolean equalsTo(Individuo a){
        int comp=this.individuo.compareTo(a.individuo);
        return comp==0;
    }
    public void imprime(){
        System.out.println(this);
    }
    
    
    @Override
    public int compareTo(Object o) {
        
        Individuo a = (Individuo) this, b = (Individuo) o;
        double aux=Double.compare(a.fitness,b.fitness);
        return aux >0? 1:aux<0? -1:0;

    }
    
     
    
  
    //FIN de las funciones de la suite de De Jong
}
