package pulverizador;

import java.util.Random;

/**
 *
 * @author Alejandro Hernandez Mora
 */
public class Individuo implements Comparable {
    Random r = new Random();
    double fitness;
    String acciones;
    String [] parametros;
    static int [][] rangos= {{100,700},{100,700},{0,5},{0,180},{0,180},{0,180},{0,180},{0,180},{0,180}};

    public Individuo(){
        acciones="";
        parametros= new String[35];
        int accion;
        int a,b;
        for (int i = 0; i < 35; i++) {
            accion= r.nextInt(9);
            acciones+=accion;
            a=rangos[accion][0];
            b=rangos[accion][1];
            parametros[i]=""+((b-a)*r.nextDouble()+a);
        }
        
    }
    
    public Individuo(String individuo) {
        String [] inicial= individuo.split("&");
        String ind=inicial[0];
        fitness=Double.parseDouble(inicial[1]);
        String[] vector = ind.split(",");
        parametros= new String[35];
        this.acciones = vector[0];
        for (int i = 0; i < parametros.length; i++) {
            parametros[i] = vector[i + 1];
            
        }
    }
    
    public Individuo(String acciones, String [] parametros) {
        this.acciones = acciones;
        this.parametros= parametros;
    }
    
    public String [][] cruzaParametros(int inicio, int fin, String[] params){
        String[][] hijos= new String[2][35];
        for (int i = 0; i < inicio; i++) {
            hijos[0][i]=this.parametros[i];
            hijos[1][i]=params[i];
        }
        for (int i = inicio; i < fin; i++) {
            hijos[0][i]=params[i];
            hijos[1][i]=this.parametros[i];
        }
        for (int i = fin; i < 35; i++) {
            hijos[0][i]=this.parametros[i];
            hijos[1][i]=params[i];
        }
        
        return hijos;
    }
    
    public Individuo[] cruzar(Individuo a, double p_cruza){
        Individuo[] hijos = new Individuo[2];
        double p=r.nextDouble();
        if (p < p_cruza) {
            int cruce = r.nextInt(35), l = r.nextInt(35);
            int inicio, fin;
            if (cruce < l) {
                inicio = cruce;
                fin = l;
            } else {
                inicio = l;
                fin = cruce;
            }
            String[][] nuevos_parametros= cruzaParametros(inicio, fin, a.parametros);
            String h2, h1;
            h1= this.acciones.substring(0, inicio);
            h1 += a.acciones.substring(inicio, fin);
            h1 += this.acciones.substring(fin, acciones.length());
            

            h2 = a.acciones.substring(0, inicio);
            h2 += this.acciones.substring(inicio, fin);
            h2 += a.acciones.substring(fin, acciones.length());

            hijos[0] = new Individuo(h1, nuevos_parametros[0]);
            hijos[1] = new Individuo(h2, nuevos_parametros[1]);
            
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
    
    public void mutar() {
        int i = r.nextInt(35);
        char caracter = (char) (48 + r.nextInt(7));
        acciones = acciones.substring(0, i) + caracter + acciones.substring(i + 1);
        int j=i/7;
        parametros[i]=""+((rangos[j][1]-rangos[j][0])*r.nextDouble()+rangos[j][0]);
    }
    
    @Override 
    public String toString(){
        String s=",";
        for (int i = 0; i < 35; i++) {
            s+=parametros[i];
            s+= i==34? "":",";
        }
        return acciones+s+"&"+fitness;
    }
    public boolean equalsTo(Individuo a){
        int comp=this.acciones.compareTo(a.acciones);
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

    double getFitness() {
        return fitness;
    }
}
