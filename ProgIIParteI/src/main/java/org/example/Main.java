package org.example;


import java.util.Scanner;

public class Main {
    static void main() {

        Scanner scanner = new Scanner(System.in);
        MenuPrincipal menuPrincipal = new MenuPrincipal(scanner);
        menuPrincipal.exibir();
        scanner.close();

    }
}
