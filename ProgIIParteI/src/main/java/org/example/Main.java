package org.example;

import org.example.view.MenuPrincipal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MenuPrincipal menuPrincipal = new MenuPrincipal(scanner);
        menuPrincipal.exibir();
        scanner.close();

    }
}
