package test.java;

import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    public static void main(String[] args) {

        String postfix = null;
        //Entrada de datos
        System.out.println("*Escribe una expresión algebraica: ");
        Scanner leer = new Scanner(System.in);

        //Depurar la expresion algebraica
        String expr = depurar(leer.nextLine());
        String[] arrayInfix = expr.split(" ");

        //Declaración de las pilas
        Stack < String > E = new Stack < String > (); //Pila entrada
        Stack < String > P = new Stack < String > (); //Pila temporal para operadores
        Stack < String > S = new Stack < String > (); //Pila salida

        //Añadir la array a la Pila de entrada (E)
        for (int i = arrayInfix.length - 1; i >= 0; i--) {
            E.push(arrayInfix[i]);
        }

        try {
            //Algoritmo Infijo a Postfijo
            while (!E.isEmpty()) {
                switch (pref(E.peek())){
                    //Asignación de P con el valor que se extrae de E
                    case 1:
                        P.push(E.pop());
                        break;
                        //Ciclo donde se reLiza la asignación del Prefijo en S cuando P es menor o igual que E
                    case 3:
                    case 4:
                        while(pref(P.peek()) >= pref(E.peek())) {
                            S.push(P.pop());
                        }
                        P.push(E.pop());
                        break;
                        //Ciclo donde se almacena S cuando P es diferente a (
                    case 2:
                        while(!P.peek().equals("(")) {
                            S.push(P.pop());
                        }
                        P.pop();
                        E.pop();
                        break;
                    default:
                        S.push(E.pop());
                }
            }
            //Ciclo donde se comparan los operadores con el valor correspondiente a E
            String operadores = "+-*/%";
            while (!E.isEmpty()) {
                if (operadores.contains("" + E.peek())) {
                    P.push(evaluar(E.pop(), P.pop(), P.pop()) + "");
                }else {
                    P.push(E.pop());
                }
            }

            //Eliminacion de `impurezas´ en la expresiones algebraicas
            String infix = expr.replace(" ", "");
            postfix = S.toString().replaceAll("[\\]\\[,]", "");

            //Mostrar resultados:
            System.out.println("Expresion Infija: " + infix);
            System.out.println("Expresion Postfija: " + postfix);

        //En caso de no identificar la expresion algebraica se imprime la siguiente expresión
        }catch(Exception ex){
            System.out.println("Error en la expresión algebraica");
            System.err.println(ex);
        }

        String[] postValidate = postfix.split(" ");

        //Declaración de las pilas
        Stack < String > F = new Stack < String > (); //Pila entrada
        Stack < String > G = new Stack < String > ();
        for (int i = postValidate.length - 1; i >= 0; i--) {
            F.push(postValidate[i]);
        }
        //Ciclo donde se comparan los operadores logicos con el valor de F
        String operadores = "+-*/%";
        while (!F.isEmpty()) {
            if (operadores.contains("" + F.peek())) {
                G.push(evaluar(F.pop(), G.pop(), G.pop()) + "");
            }else {
                G.push(F.pop());
            }
        }

        //Mostrar resultados:
        System.out.println("Expresion: " + expr);
        System.out.println("Resultado: " + G.peek());
    }


    //Depurar expresión algebraica
    private static String depurar(String s) {
        s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco
        s = "(" + s + ")";
        String simbols = "+-*/()";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < s.length(); i++) {
            if (simbols.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            }else str += s.charAt(i);
        }
        return str.replaceAll("\\s+", " ").trim();
    }//Metodo con el cual se asignan las operaciones a evaluar
    private static int evaluar(String op, String n2, String n1) {
        int num1 = Integer.parseInt(n1);
        int num2 = Integer.parseInt(n2);
        if (op.equals("+")) return (num1 + num2);
        if (op.equals("-")) return (num1 - num2);
        if (op.equals("*")) return (num1 * num2);
        if (op.equals("/")) return (num1 / num2);
        if (op.equals("%")) return (num1 % num2);
        return 0;
    }
    //Jerarquia de los operadores busca en orden las operaciones
    private static int pref(String op) {
        int prf = 99;
        if (op.equals("^")) prf = 5;
        if (op.equals("*") || op.equals("/")) prf = 4;
        if (op.equals("+") || op.equals("-")) prf = 3;
        if (op.equals(")")) prf = 2;
        if (op.equals("(")) prf = 1;
        return prf;
    }
}