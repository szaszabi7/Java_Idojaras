package com.company;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Kiszolgalo implements Runnable{
    private HashMap<String, Idojaras> elorejelzesek;

    Socket kapcsolat;

    public Kiszolgalo(Socket kapcsolat) {
        this.kapcsolat = kapcsolat;
        elorejelzesek = new HashMap<>();
        beolvas();
    }

    @Override
    public void run() {
        try {
            DataInputStream ugyfeltol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream ugyfelnek = new DataOutputStream(kapcsolat.getOutputStream());
            while (true) {
                int menu;
                do {
                    menu = ugyfeltol.readInt();
                    System.out.println(menu);
                    switch (menu){
                        case 1: ugyfelnek.writeUTF(kiir()); break;
                        case 2: ugyfelnek.writeUTF(megyekSzama()); break;
                        case 3: ugyfelnek.writeUTF(maiAtlag()); break;
                        case 4: ugyfelnek.writeUTF(holnapiAtlag()); break;
                        case 5: ugyfelnek.writeUTF("Kilépés"); break;
                    }
                } while (menu != 5);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void beolvas() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("weather.txt"));

            br.readLine();
            String sor = br.readLine();
            while (sor != null) {
                Idojaras i = new Idojaras(sor);
                String megye = i.getMegye();
                elorejelzesek.put(megye, i);
                sor = br.readLine();
            }
            /*for (Map.Entry<String, Idojaras> entry: elorejelzesek.entrySet()) {
                System.out.println(entry.getValue());
            }*/

            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private String kiir(){
        String s = "";
        for (Map.Entry<String, Idojaras> entry: elorejelzesek.entrySet()) {
            s += entry.getValue() + "\n";
        }
        return s;
    }

    private String megyekSzama() {
        return "A megyék száma: " + elorejelzesek.size();
    }

    private String maiAtlag(){
        String s = "";
        for (Map.Entry<String, Idojaras> entry: elorejelzesek.entrySet()) {
            s += entry.getValue().getMegye() + " Átlag: " +
                    (entry.getValue().getMai().getMax() + entry.getValue().getMai().getMin()) / 2 + "\n";
        }
        return s;
    }

    private String holnapiAtlag(){
        String s = "";
        for (Map.Entry<String, Idojaras> entry: elorejelzesek.entrySet()) {
            s += entry.getValue().getMegye() + " Átlag: " +
                    (entry.getValue().getHolnapi().getMax() + entry.getValue().getHolnapi().getMin()) / 2 + "\n";
        }
        return s;
    }
}
