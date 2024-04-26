package org.example;

import java.util.*;

public class Test {
    //variables estaticas (variables de toda la clase)
    static Map<String, double[]> tabla;
    static ArrayList<Individuo> individuos;//aqui se guardaran los individuos
    public static void main(String[] args) {
        //la tabla de beneficios segun las ciudades son de la siguiente forma
        tabla = new HashMap<>();
        // Agregar datos a la tabla
        tabla.put("Inversion", new double[]{0,1,2,3,4,5,6,7,8,9,10});
        tabla.put("Beneficio I", new double[]{0, 0.28, 0.45, 0.65, 0.78, 0.90, 1.02, 1.13, 1.23, 1.32, 1.38});
        tabla.put("Beneficio II", new double[]{0, 0.25, 0.41, 0.55, 0.65, 0.75, 0.8, 0.85, 0.88, 0.9, 0.9});
        tabla.put("Beneficio III", new double[]{0, 0.15, 0.25, 0.40, 0.50, 0.62, 0.73, 0.82, 0.90, 0.96, 1.00});
        tabla.put("Beneficio IV", new double[]{0, 0.2, 0.33, 0.42, 0.48, 0.53, 0.56, 0.58, 0.6, 0.6, 0.6});

        // Imprimir el mapa de beneficios
        //imprimirBeneficios(tabla);

        final int individuosPorGenerar = 50;
        final int generaciones = 20;
        final int inversion = 10;
        int inversionRestante = inversion;//dinero restante a invertir (10 M)
        individuos = new ArrayList<>();//aqui se guardaran los individuos

        int invC1 = 0, invC2 = 0, invC3 = 0, invC4 = 0;//invertido en cada ciudad
        double c1 = 0,c2 = 0,c3 = 0,c4 = 0;//beneficio de ciudad
        int ciudadRandom = 0;//selector de ciudad random

        //generacion de individuos
        for (int i = 0; i < individuosPorGenerar; i++) {
            inversionRestante = inversion;
            Random aleatorio = new Random();
            ArrayList<Integer> listaCiudades = new ArrayList<>();//aqui se pondran las ciudades en las que ya se invirtio, para no volver a elegirlas para volver a invertir




            //valores iniciales a invertir
            for (int j = 0; j < 4; j++) {//ciclo que se repite 4 veces
                // Crear un objeto Random para generar números aleatorios

                do {
                    ciudadRandom = aleatorio.nextInt(4) + 1;//obtener un random entre 1 y 4
                } while (listaCiudades.contains(ciudadRandom));//mientras ya se haya usado ese numero, se repíte

                listaCiudades.add(ciudadRandom);//se agrega el numero nuevo al arreglo
                switch (ciudadRandom){
                    case 1:
                        System.out.println("ciudad 1");
                        invC1 = inversionAleatoria(inversionRestante);
                        inversionRestante = inversionRestante-invC1;
                        break;
                    case 2:
                        System.out.println("ciudad 2");
                        invC2 = inversionAleatoria(inversionRestante);
                        inversionRestante = inversionRestante-invC2;
                        break;
                    case 3:
                        System.out.println("ciudad 3");
                        invC3 = inversionAleatoria(inversionRestante);
                        inversionRestante = inversionRestante-invC3;
                        break;
                    case 4:
                        System.out.println("ciudad 4");
                        invC4 = inversionAleatoria(inversionRestante);
                        inversionRestante = inversionRestante-invC4;
                        break;
                    default:
                        System.out.println("ERROR");

                }
            }



            //valores a calcular

            //beneficios de cada ciudad:
            c1 = ((obtenerDato(tabla,"Beneficio I", invC1)));//obtiene el valor de la tabla segun lo invertido
            c2 = ((obtenerDato(tabla,"Beneficio II", invC2)));
            c3 = ((obtenerDato(tabla,"Beneficio III", invC3)));
            c4 = ((obtenerDato(tabla,"Beneficio IV", invC4)));


            int v = Math.abs((invC1+invC2+invC3+invC4)-inversion);//valor absoluto de la diferencia entre la suma invertida y la inversion total (10)
            double formula = (c1+c2+c3+c4)/(500*v+1);//evaluar
            System.out.println("Ciudad 1 beneficio:  " + c1 + " inversion " + invC1 );
            System.out.println("Ciudad 2 beneficio:  " + c2 + " inversion " + invC2 );
            System.out.println("Ciudad 3 beneficio:  " + c3 + " inversion " + invC3 );
            System.out.println("Ciudad 4 beneficio:  " + c4 + " inversion " + invC4 );
            System.out.println("Dinero restante: " + inversionRestante);
            System.out.println("Formula " + formula);

            String cromosoma =  enterosACromosoma(invC1,invC2,invC3,invC4);

            System.out.println("cromosoma:" + cromosoma);
            System.out.println("cromosoma transformado: " + cromosomaAEnteros(cromosoma));

            individuos.add(new Individuo(cromosoma,formula));

        }
        Collections.sort(individuos);//ordenar los individuos
        imprimirIndividuos(individuos);
        //cruzaDeDosPuntos(new Individuo("1000000010110011",0.1),new Individuo("0100010010011100",0.1));
        //cromosomaAFormula("0100010000000010");



        // cruzar los mas altos (80% cruzar) y generar nueva poblacion



    }
    //metodo que selecciona el 80% de los individuos y los cruza todos con todos
    public static void seleccionYCruza(){
        ArrayList<String> cromosomasHijos = new ArrayList<>();
        int cantidadIndividuos = individuos.size();
        int seleccionados = (int)(cantidadIndividuos*0.8);//seleccionar el 80% de los generados
        //en teoria ya estan ordenados los individuos

        for (int i = 0; i < seleccionados; i++) {
            cromosomasHijos = cruzaDeDosPuntos(individuos.get(i),individuos.get(i+1));//obtener los dos hijos
            //ocupo separar el arreglo (los dos hijos en string diferente, y obtener la formula, despues agregarlo a individuo)


        }

    }





    //metodo que reciba dos individuos y cruce con dos puntos sus cromosomas y de como resultado dos nuevos individuod (hijos, ya cruzados)

    public static ArrayList<String> cruzaDeDosPuntos(Individuo individuoPadre1, Individuo individuoPadre2){
        ArrayList<String> hijos = new ArrayList<>();
        String padre1 = individuoPadre1.getCromosoma();
        String padre2 = individuoPadre2.getCromosoma();
        //hace la cruza de dos puntos
        String hijo1 = padre1.substring(0, 5) + padre2.substring(5, 11)
                + padre1.substring(11);

        String hijo2 = padre2.substring(0, 5) + padre1.substring(5, 11)
                + padre2.substring(11);

        System.out.println("hijo1: " + hijo1);
        System.out.println("hijo2: " + hijo2);

        hijos.add(hijo1);
        hijos.add(hijo2);

        return hijos;
    }
    //evalua los cromosomas recibidos
    public static void cromosomaAFormula(String cromosoma){
        ArrayList<Integer> enteros = new ArrayList<>();
        int inversion = 10;
        int inversionRestante = inversion;

        enteros = cromosomaAEnteros(cromosoma);

        System.out.println("inversiones : "+enteros);
        //valores a calcular

        //beneficios de cada ciudad:
        double c1 = ((obtenerDato(tabla,"Beneficio I", enteros.get(0))));//obtiene el valor de la tabla segun lo invertido
        double c2 = ((obtenerDato(tabla,"Beneficio II", enteros.get(1))));
        double  c3 = ((obtenerDato(tabla,"Beneficio III", enteros.get(2))));
        double c4 = ((obtenerDato(tabla,"Beneficio IV", enteros.get(3))));
        inversionRestante = inversionRestante-(enteros.get(0)+enteros.get(1)+enteros.get(2)+enteros.get(3));


        int v = Math.abs((enteros.get(0)+enteros.get(1)+enteros.get(2)+enteros.get(3))-inversion);//valor absoluto de la diferencia entre la suma invertida y la inversion total (10)
        double formula = (c1+c2+c3+c4)/(500*v+1);//evaluar
        System.out.println("Ciudad 1 beneficio:  " + c1 + " inversion " + enteros.get(0) );
        System.out.println("Ciudad 2 beneficio:  " + c2 + " inversion " + enteros.get(1) );
        System.out.println("Ciudad 3 beneficio:  " + c3 + " inversion " + enteros.get(2) );
        System.out.println("Ciudad 4 beneficio:  " + c4 + " inversion " + enteros.get(3) );
        System.out.println("Dinero restante: " + inversionRestante);
        System.out.println("Formula " + formula);

    }
    //por ahora no haria nada
    public static void validarCromosoma(String cromosoma){
        //un cromosoma valido seria de que cada caracter no pase de 10 y la suma siempre igual a 10
        System.out.println("Print de prueba");

    }

    //metodo para imprimir los individuos
    public static void imprimirIndividuos(ArrayList<Individuo> individuos) {
        System.out.println("Individuos:");
        for (Individuo individuo : individuos) {
            System.out.println("Cromosoma: " + individuo.getCromosoma() + ", Formula: " + individuo.getFormula());
        }
    }
    //transforma el cromosoma en enteros
    public static ArrayList<Integer> cromosomaAEnteros(String cromosoma) {
        ArrayList<Integer> enteros = new ArrayList<>();
        if (cromosoma.length() != 16) {
            System.out.println("El cromosoma debe tener exactamente 16 caracteres.");
        }else {
            String cadena1 = cromosoma.substring(0, 4);
            String cadena2 = cromosoma.substring(4, 8);
            String cadena3 = cromosoma.substring(8, 12);
            String cadena4 = cromosoma.substring(12, 16);

            enteros.add(binarioAEntero(cadena1));
            enteros.add(binarioAEntero(cadena2));
            enteros.add(binarioAEntero(cadena3));
            enteros.add(binarioAEntero(cadena4));
        }

        return enteros;
    }
    //binario a entero
    public static int binarioAEntero(String binario) {
        // Verificar que el string tenga exactamente 4 caracteres y sean '0' o '1'
        if (binario.length() != 4 || !binario.matches("[01]+")) {
            System.out.println("El número binario debe tener exactamente 4 caracteres y contener solo 0s y 1s.");
            return -1; // Valor de retorno para indicar un error
        }

        // Convertir el número binario a entero usando Integer.parseInt()
        return Integer.parseInt(binario, 2);
    }
    //4 enteros a cromosoma
    public static String enterosACromosoma(int invC1, int invC2, int invC3, int invC4){
        //cromosomas de ciudad y cromosoma completo
        String cromosoma ;
        String cadena1,cadena2,cadena3,cadena4;

        cadena1 = convertirABinario(invC1);
        cadena2 = convertirABinario(invC2);
        cadena3 = convertirABinario(invC3);
        cadena4 = convertirABinario(invC4);

        cromosoma = cadena1 + cadena2 + cadena3 + cadena4;

        return cromosoma;
    }
    //convierte numero a binario de 4 caracteres
    public static String convertirABinario(int numero) {
        if (numero == 0) {
            return "0000";
        }
        StringBuilder resultado = new StringBuilder();
        while (numero > 0) {
            int residuo = numero % 2;
            resultado.insert(0, residuo); // Insertar el residuo al inicio de la cadena
            numero = numero / 2;
        }
        //agregar 0 para hacerlo de 4 caracteres
        while (resultado.length()<4){
            resultado.insert(0, '0');
        }
        return resultado.toString();
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

class Individuo implements Comparable<Individuo>{
    private String cromosoma;
    private double formula;

    public Individuo(String cromosoma, double formula) {
        this.cromosoma = cromosoma;
        this.formula = formula;
    }

    public String getCromosoma() {
        return cromosoma;
    }

    public double getFormula() {
        return formula;
    }

    @Override
    public int compareTo(Individuo o) {
        return Double.compare(o.getFormula(), this.getFormula());
    }
}