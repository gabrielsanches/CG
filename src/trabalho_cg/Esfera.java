/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_cg;

import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class Esfera {

    public static ArrayList<double[]> gerar_esfera(int a, int b, int c, double r, int precisao) {
        ArrayList<double[]> pontos = new ArrayList<>();
        double vec[] = new double[3];
        for (int i = 0; i <= 360; i += precisao) {
            for (int j = 0; j <= 180; j += precisao) {
                vec = new double[3];
                vec[0] = a + r * Math.cos(i) * Math.sin(j);
                vec[2] = b + r * Math.sin(i) * Math.sin(j);
                vec[1] = c + r * Math.cos(j);
                pontos.add(vec);
            }
        }
        return pontos;
    }
}
