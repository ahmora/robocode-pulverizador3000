package pulverizador;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alejandro Hernandez Mora
 */
public class AlgoritmoGenetico {

    double p_mutacion, p_cruza;
    double[] variables;
    int n;
    String resultadosPromedio, resultadosBest;
    Random r = new Random();

    /**
     *
     * @param n tamanio de la poblacion
     * @param p_mutacion Probabilidad de mutacion
     * @param p_cruza Probabilidad de cruza
     */
    public AlgoritmoGenetico(int n, double p_mutacion, double p_cruza) {
        this.n = n;
        this.p_mutacion = p_mutacion;
        this.p_cruza = p_cruza;
        this.resultadosPromedio = this.resultadosBest = "";
    }

    public int calculaLongitud(double a, double b, int precision) {
        double temp = a;
        precision = (int) Math.pow(10, precision);
        if (a > b) {
            a = b;
            b = temp;
        }
        double rango = b - a;
        int valores = (int) (rango * precision);
        int digitos = 0;
        long aux = 1;
        while (aux < valores) {
            aux = aux << 1;
            digitos++;
        }
        return digitos;
    }

    public double[] construyeRangos() {
        double[] rangos = new double[2];
        double rango = r.nextDouble() * 15;
        rangos[0] = -rango;
        rango = r.nextDouble() * 15;
        rangos[1] = rango;
        /*System.out.print(rangos[i][0] + "  ");
         System.out.println(rangos[i][1]);*/

        return rangos;
    }

    /**
     * Calcula el fitness acumulado de cada individuo
     *
     * @param poblacion la poblacion con la que trabajara la funcion
     * @return un arreglo con el tirness acumulado en cada espacio para cada
     * individuo
     */
    public double[] fitnessAcumulado(Individuo[] poblacion) {
        double suma_acumulada = 0;
        double[] fitnessAc = new double[poblacion.length];
        for (int i = 0; i < poblacion.length; i++) {
            suma_acumulada += poblacion[i].getFitness();
            fitnessAc[i] = suma_acumulada;
        }
        return fitnessAc;
    }

    //Inicio de los metodos de seleccion
    /**
     * Metodo que cruza a la poblacion
     *
     * @param poblacion
     * @return poblacion cruzada
     */
    public Individuo[] cruzaPoblacion(Individuo[] poblacion, int tipoCruza) {
        ArrayList elementos = new ArrayList(n);
        Individuo[] nueva_poblacion = new Individuo[n];
        for (int i = 0; i < n; i++) {
            elementos.add(i);
        }
        Collections.shuffle(elementos);
        int i = 0;
        Individuo ind1, ind2;
        Individuo[] hijos;
        while (i < n) {
            ind1 = poblacion[(int) elementos.get(1)];
            elementos.remove(1);
            ind2 = poblacion[(int) elementos.get(0)];
            elementos.remove(0);
            hijos = ind1.cruzar(ind2, p_cruza);
            nueva_poblacion[i++] = hijos[0];
            nueva_poblacion[i++] = hijos[1];

        }
        return nueva_poblacion;

    }

    

    /**
     * Metodo del 4 torneo para la cruza
     *
     * @param poblacion
     * @return
     */
    public Individuo[] seleccionKTorneo(Individuo[] poblacion, int k, int tipoCruza) {
        int i = 0, temp = 0, son_k = 0;
        Individuo[] nueva_poblacion = new Individuo[n], aux = new Individuo[k],
                hijos = new Individuo[2];

        while (i < n) {
            while (son_k < k) {
                temp = r.nextInt(n);
                aux[son_k] = poblacion[temp];
                son_k++;
            }
            son_k = 0;
            Arrays.sort(aux);
            // System.out.println("Los mejores fueron:");
            hijos = aux[k - 1].cruzar(aux[k - 2], p_cruza);
            nueva_poblacion[i++] = hijos[0];
            if (i < n) {
                nueva_poblacion[i++] = hijos[1];
            }
            int j = 0;
        }
        return nueva_poblacion;
    }

    /**
     * Metodo vazconcelos para la cruza
     *
     * @param poblacion
     * @return
     */
    public Individuo[] seleccionVasconcelos(Individuo[] poblacion) {
        Individuo[] nueva_poblacion = new Individuo[n], hijos;
        Arrays.sort(poblacion);
        int agregados = 0;
        for (int i = 0; i < (n / 2); i++) {
            hijos = poblacion[i].cruzar(poblacion[n - i - 1], p_cruza);
            nueva_poblacion[agregados] = hijos[0];
            agregados++;
            if (agregados < n) {
                nueva_poblacion[agregados] = hijos[1];
                agregados++;
            }
        }
        return nueva_poblacion;
    }

    /**
     * Metodo de la ruleta para la cruza
     *
     * @param poblacion
     * @return
     */
    public Individuo[] seleccionRuleta(Individuo[] poblacion) {
        Individuo[] nueva_poblacion = new Individuo[n];
        double[] f_acc = fitnessAcumulado(poblacion);
        double f_acc_total = f_acc[n - 1];
        for (int i = 0; i < n; i++) {
            f_acc[i] /= f_acc_total;
        }

        for (int i = 0; i < n; i++) {
            nueva_poblacion[i] = poblacion[buscaFitnessAcc(r.nextDouble(), f_acc)];
        }

        return nueva_poblacion;
    }

   
    //Inicio de los metodos auxiliares para los de cruza
    /**
     * Metodo que muta a la poblacion se gun su p_mutacion
     *
     * @param poblacion
     */
    public void mutacionUniforme(Individuo[] poblacion) {
        int cuantos = (int) (p_mutacion * 100 * n);
        while (cuantos > 0) {
            poblacion[r.nextInt(n)].mutar();
            cuantos--;
        }
    }

    /**
     * MEtodo que calcula el fitness promedio
     *
     * @param poblacion
     * @return
     */
    public double fitnessPromedio(Individuo[] poblacion) {
        double[] ac = fitnessAcumulado(poblacion);
        return ac[poblacion.length - 1] / poblacion.length;
    }

    /**
     * Metodo que calcula la distancia entre dos indivduos (Segun su evaluacion)
     *
     * @param eval
     * @param eval_optima
     * @return
     */
    public double distancia(double eval, double eval_optima) {
        return Math.abs(eval_optima - eval);
    }

    /**
     * Metodo que calcula la distancia promedio de la poblacion a una evaluacion
     * especifica
     *
     * @param poblacion
     * @param optimo
     * @return
     */
    public double distanciaPromedio(Individuo[] poblacion, double optimo) {
        double promedio = 0;
        for (int i = 0; i < n; i++) {
            promedio += distancia(poblacion[i].getFitness(), optimo);
        }
        promedio /= n;
        return promedio;
    }

    /**
     * Metodo que calcula al iesimo elemento con el fitness acumulado que
     * buscamos
     *
     * @param fitness_goal
     * @param fitness_acc
     * @return
     */
    public int buscaFitnessAcc(double fitness_goal, double[] fitness_acc) {
        for (int i = 0; i < fitness_acc.length - 1; i++) {
            if (fitness_acc[i] < fitness_goal && fitness_goal < fitness_acc[i + 1]) {
                //System.out.println(i + "   " + fitness_acc[i]);
                return i;
            }
        }
        return fitness_acc.length - 1;

    }

    public Individuo[] elitismoK(Individuo[] poblacion, Individuo[] mejores, int k) {
        Arrays.sort(poblacion);
        Arrays.sort(mejores);
        Individuo[] nuevos = new Individuo[k];
        int p = poblacion.length - 1, m = mejores.length - 1;
        Individuo ind_p, ind_m;

        for (int i = k - 1; i >= 0; i--) {
            ind_p = poblacion[p];
            ind_m = mejores[m];
            if (i == k - 1) {
                if (ind_p.compareTo(ind_m) == 1) {
                    nuevos[i] = ind_p;
                    p--;
                } else {
                    nuevos[i] = ind_m;
                    m--;
                }
            } else {
                if (p < 0 || Arrays.binarySearch(nuevos, i + 1, k - 1, ind_m) < 0) {
                    nuevos[i] = ind_m;
                    m--;

                } else if (m < 0 || Arrays.binarySearch(nuevos, i + 1, k - 1, ind_p) < 0) {
                    nuevos[i] = ind_p;
                    p--;
                } else {
                    nuevos[i] = ind_p;
                    p--;
                }
            }
        }

        return nuevos;
    }
    //FIN de los metodos auxiliares para los de cruza


    public Individuo[][] agVasconcelos(Individuo[] poblacion, int generaciones, int tipoCruza) {
        Individuo[] mejores = copiaPoblacion(poblacion, n);
        //System.out.println("Poblacion Inicial:");
        //imprimePoblacion(poblacion, n);
        double fitness_promedio, mejor_fitness, fitness_promedio_elitista, mejor_fitness_elitista;

        for (int i = 0; i < generaciones; i++) {
            poblacion = seleccionVasconcelos(poblacion);
            mejores = elitismoK(poblacion, mejores, n);
            mutacionUniforme(poblacion);

            Arrays.sort(poblacion);
            Arrays.sort(mejores);
            fitness_promedio = fitnessPromedio(poblacion);
            fitness_promedio_elitista = fitnessPromedio(mejores);
            mejor_fitness = poblacion[n - 1].fitness;
            mejor_fitness_elitista = mejores[n - 1].fitness;

            resultadosPromedio += String.format("%.12f", fitness_promedio);
            resultadosPromedio += i == generaciones - 1 ? "" : ",";

            resultadosBest += String.format("%.12f", mejor_fitness);
            resultadosBest += i == generaciones - 1 ? "" : ",";

            //imprimePoblacion((Individuo[]) poblacion, n);
        }

        //System.out.println("Ultima Poblacion");
        //imprimePoblacion(poblacion, n);
        //System.out.println("Mejores: " + n + " individuos");
        //imprimePoblacion(mejores, n);

        //System.out.println(resultados);
        Individuo[][] r = {poblacion, mejores};
        return r;

    }

    //FIN de algoritmos geneticos no convencionales
    /**
     * Metodo que imprime la poblacion
     *
     * @param poblacion
     */
    public void imprimePoblacion(Individuo[] poblacion, int k) {
        if (k > n) {
            k = n;
        }
        for (int i = 0; i < k; i++) {
            poblacion[i].imprime();
        }
    }

    public Individuo[] copiaPoblacion(Individuo[] poblacion, int k) {
        k = k > n ? n : k;
        Individuo[] nueva = new Individuo[k];
        for (int i = 0; i < k; i++) {
            nueva[i] = poblacion[i];
        }

        return nueva;
    }

    public void escribeResultados(String archivo, String resultados) throws FileNotFoundException {
        PrintWriter pw;
        pw = new PrintWriter(archivo);
        pw.write(resultados);
        pw.close();

    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    }
}