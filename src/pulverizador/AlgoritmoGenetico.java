package pulverizador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
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

    public Individuo[] generaPoblacion() {
        Individuo[] poblacion = new Individuo[n];
        for (int i = 0; i < n; i++) {
            poblacion[i] = new Individuo();
        }
        return poblacion;
    }

    public void escribePoblacion(String nombreDelArchivo, Individuo[] poblacion) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(nombreDelArchivo);
        String s = "";
        Arrays.sort(poblacion);
        for (int i = 0; i < n; i++) {
            s += poblacion[i] + "\n";
        }
        pw.write(s);
        pw.close();
    }

    public Individuo[] leePoblacion(String nombreDelArchivo) {
        Individuo[] poblacion = new Individuo[n];
        try {
            Scanner scan = new Scanner(new FileReader(nombreDelArchivo));
            for (int i = 0; i < n; i++) {
                poblacion[i] = new Individuo(scan.nextLine());

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlgoritmoGenetico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return poblacion;
    }

    public Individuo[] conviertePoblacion(String pob) {
        Individuo[] poblacion = new Individuo[n];
        String[] aux = pob.split("\n");
        for (int i = 0; i < n; i++) {
            poblacion[i] = new Individuo(aux[i]);

        }
        return poblacion;
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

    //Inicio de los metodos auxiliares para los de cruza
    /**
     * Metodo que muta a la poblacion se gun su p_mutacion
     *
     * @param poblacion
     */
    public void mutacionUniforme(Individuo[] poblacion) {
        int cuantos = (int) (p_mutacion * n);
        while (cuantos > 0) {
            poblacion[r.nextInt(n)].mutar();
            cuantos--;
        }
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
            if (p < 0 ) {
                nuevos[i] = ind_m;
                m--;

            } else if (m < 0) {
                nuevos[i] = ind_p;
                p--;
            } else {
                if (ind_p.compareTo(ind_m) == 1) {
                    nuevos[i] = ind_p;
                    p--;
                } else {
                    nuevos[i] = ind_m;
                    m--;
                }

            }

        }
        return nuevos;
    }
    //FIN de los metodos auxiliares para los de cruza

    public Individuo[][] agVasconcelos(Individuo[] poblacion, Individuo[] mejores) {
        //System.out.println("Poblacion Inicial:");
        //imprimePoblacion(poblacion, n);
        poblacion = seleccionVasconcelos(poblacion);
        mejores = elitismoK(poblacion, mejores, n);
        mutacionUniforme(poblacion);
        Arrays.sort(poblacion);
        Arrays.sort(mejores);
        //imprimePoblacion((Individuo[]) poblacion, n);
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

}
