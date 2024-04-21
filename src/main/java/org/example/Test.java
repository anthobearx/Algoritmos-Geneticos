package org.example;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        //la tabla de beneficios segun las ciudades son de la siguiente forma
        Map<String, double[]> tabla = new HashMap<>();
        // Agregar datos a la tabla
        tabla.put("Inversion", new double[]{0,1,2,3,4,5,6,7,8,9,10});
        tabla.put("Beneficio I", new double[]{0, 0.28, 0.45, 0.65, 0.78, 0.90, 1.02, 1.13, 1.23, 1.32, 1.38});
        tabla.put("Beneficio II", new double[]{0, 0.25, 0.41, 0.55, 0.65, 0.75, 0.8, 0.85, 0.88, 0.9, 0.9});
        tabla.put("Beneficio III", new double[]{0, 0.15, 0.25, 0.40, 0.50, 0.62, 0.73, 0.82, 0.90, 0.96, 1.00});
        tabla.put("Beneficio IV", new double[]{0, 0.2, 0.33, 0.42, 0.48, 0.53, 0.56, 0.58, 0.6, 0.6, 0.6});

        // Imprimir el mapa de beneficios
        //imprimirBeneficios(tabla);

        final int generaciones = 20;
        final int inversion = 10;
        int inversionRestante = inversion;//dinero restante a invertir (10 M)
        int invC1 = 0, invC2 = 0, invC3 = 0, invC4 = 0;//invertido en cada ciudad
        double c1 = 0,c2 = 0,c3 = 0,c4 = 0;//ganancia de cada ciudad



        //valores a calcular
        //invertido en cada ciudad
        invC1 = inversionAleatoria(inversionRestante);
        inversionRestante = inversionRestante-invC1;

        invC2 = inversionAleatoria(inversionRestante);
        inversionRestante = inversionRestante-invC2;

        invC3 = inversionAleatoria(inversionRestante);
        inversionRestante = inversionRestante-invC3;

        invC4 = inversionAleatoria(inversionRestante);
        inversionRestante = inversionRestante-invC4;

        //ganancias de cada ciudad:
        c1 = ((obtenerDato(tabla,"Beneficio I", invC1)) * invC1) + invC1;//obtiene el valor de la tabla segun lo invertido y lo multiplica para obtener la ganancia
        c2 = ((obtenerDato(tabla,"Beneficio II", invC2)) * invC2) + invC2;
        c3 = ((obtenerDato(tabla,"Beneficio III", invC3)) * invC3) + invC3;
        c4 = ((obtenerDato(tabla,"Beneficio IV", invC4)) * invC4) + invC4;

        int v = Math.abs((invC1+invC2+invC3+invC4)-inversion);//valor absoluto de la diferencia entre la suma invertida y la inversion total (10)
        double formula = (c1+c2+c3+c4)/(500*v+1);
        System.out.println("Ciudad 1 ganancia:  " + c1 + " inversion " + invC1);
        System.out.println("Ciudad 2 ganancia:  " + c2 + " inversion " + invC2);
        System.out.println("Ciudad 3 ganancia:  " + c3 + " inversion " + invC3);
        System.out.println("Ciudad 4 ganancia:  " + c4 + " inversion " + invC4);
        System.out.println("Dinero restante: " + inversionRestante);
        System.out.println("Formula " + formula);







        //cromosomas de ciudad y cromosoma completo
        String cromosoma ;
        String cadena1,cadena2,cadena3,cadena4;



    }


    // Método para imprimir el mapa de beneficios con tabulaciones alineadas
    public static void imprimirBeneficios(Map<String, double[]> tabla) {
        System.out.println("Inversion \tBeneficio I \tBeneficio II \tBeneficio III \tBeneficio IV");
        // Ordenar las claves de los beneficios en el orden deseado
        List<String> columnas = Arrays.asList("Inversion", "Beneficio I", "Beneficio II", "Beneficio III", "Beneficio VI");

        // Iterar sobre los índices de los arrays de beneficios
        for (int i = 0; i < tabla.get("Inversion").length; i++) {
            // Imprimir valores de cada fila en el orden deseado
            for (String columna : columnas) {
                double[] valores = tabla.get(columna);
                System.out.printf("%.2f\t\t\t", valores[i]);
            }
            System.out.println();
        }
    }

    // Método para obtener un dato específico de la tabla de beneficios
    public static double obtenerDato(Map<String, double[]> beneficios, String columna, int fila) {
        // Verificar si la columna existe en el mapa de beneficios
        if (!beneficios.containsKey(columna)) {
            System.out.println("La columna especificada no existe en la tabla de beneficios.");
            return Double.NaN; // NaN (Not a Number) representa un valor no numérico
        }

        // Obtener el array de valores correspondiente a la columna especificada
        double[] valores = beneficios.get(columna);

        // Verificar si la fila está dentro del rango de índices válidos para la columna
        if (fila < 0 || fila >= valores.length) {
            System.out.println("La fila especificada está fuera del rango de índices para la columna.");
            return Double.NaN; // NaN (Not a Number) representa un valor no numérico
        }

        // Devolver el valor correspondiente en la fila y columna especificadas
        return valores[fila];
    }

    // Método para generar un número aleatorio entre 0 y un número entero dado
    public static int inversionAleatoria(int maximo) {
        int inversion = 0;
        // Verificar si el número máximo es válido
        if (maximo < 0) {
            throw new IllegalArgumentException("El número máximo debe ser 0 o mayor.");
        } else if (maximo == 0) {
            System.out.println("Ya no hay inversion restante");
        } else{
            // Crear un objeto Random para generar números aleatorios
            Random random = new Random();

            // Generar y devolver un número aleatorio entre 0 y el número máximo
            inversion = random.nextInt(maximo + 1); // nextInt devuelve un número entre 0 (inclusive) y el número especificado (exclusivo)
        }

        return inversion;
    }


}
