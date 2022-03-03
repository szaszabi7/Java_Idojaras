package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Kliens {
    public static void main(String[] args) {
        try {
            Socket kapcsolat = new Socket("localhost", 8080);

            DataInputStream szervertol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream szervernek = new DataOutputStream(kapcsolat.getOutputStream());

            Scanner sc = new Scanner(System.in);
            int menu;
            do {
                System.out.println("Válassz!!");
                System.out.println("1. Listázás\n2. Megyék száma\n3. Mai átlag\n4. Holnapi átlag\n5. Kilépés");
                menu = sc.nextInt();
                szervernek.writeInt(menu);
                szervernek.flush();
                System.out.println(szervertol.readUTF());
                System.out.println();
            } while (menu != 5);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
