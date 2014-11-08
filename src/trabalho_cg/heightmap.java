/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_cg;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author seven
 */
public class heightmap {

    public static float[][] gerar_heightmap() {
        float height[][] = new float[4][22500];
        Random k = new Random();
        int count = 0;
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 150; j++) {
                height[0][count] = i;
                height[1][count] = j;
                height[2][count] = k.nextFloat();
                height[3][count] = 1;
                count++;
            }
        }
        return height;
    }

    public static ArrayList<Triangulo> gerar_triangulos_regular() {
        ArrayList<Triangulo> triangulos = new ArrayList<>();
        Triangulo T;
        for (int i = 0; i < 150; i += 30) {
            for (int j = 0; j < 150; j += 30) {
                if (i + 30 < 150 && j + 30 < 150) {
                    triangulos.add(new Triangulo(posicao_vetor(i + 30, j), posicao_vetor(i, j), posicao_vetor(i, j + 30)));
                    triangulos.add(new Triangulo(posicao_vetor(i + 30, j), posicao_vetor(i, j + 30), posicao_vetor(i + 30, j + 30)));
                } else {
                    if (i + 30 == 150 && j + 30 < 150) {
                        triangulos.add(new Triangulo(posicao_vetor(i + 29, j), posicao_vetor(i, j), posicao_vetor(i, j + 30)));
                        triangulos.add(new Triangulo(posicao_vetor(i + 29, j), posicao_vetor(i, j + 30), posicao_vetor(i + 29, j + 30)));
                    } else {
                        if (i + 30 < 150 && j + 30 == 150) {
                            triangulos.add(new Triangulo(posicao_vetor(i + 30, j), posicao_vetor(i, j), posicao_vetor(i, j + 29)));
                            triangulos.add(new Triangulo(posicao_vetor(i + 30, j), posicao_vetor(i, j + 29), posicao_vetor(i + 30, j + 29)));
                        } else {
                            triangulos.add(new Triangulo(posicao_vetor(i + 29, j), posicao_vetor(i, j), posicao_vetor(i, j + 29)));
                            triangulos.add(new Triangulo(posicao_vetor(i + 29, j), posicao_vetor(i, j + 29), posicao_vetor(i + 29, j + 29)));
                        }
                    }
                }
            }
        }

        return triangulos;
    }

    public static int posicao_vetor(int i, int j) {
        return i + j * 150;
    }

    public static float[][] suavizar_heightmap_regular(float inicial[][]) {    
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 150; j++) {
                if (j == 0) {
                    inicial[2][posicao_vetor(i, j)] = (float) ((inicial[2][posicao_vetor(i, j)] + inicial[2][posicao_vetor(i, j + 1)]) / 2.0);                    
                } else {
                    if (j == 149) {
                        inicial[2][posicao_vetor(i, j)] = (float) ((inicial[2][posicao_vetor(i, j)] + inicial[2][posicao_vetor(i, j - 1)]) / 2.0);
                    } else {
                        inicial[2][posicao_vetor(i, j)] = (float) ((inicial[2][posicao_vetor(i, j)] + inicial[2][posicao_vetor(i, j + 1)] + inicial[2][posicao_vetor(i, j - 1)]) / 3.0);
                    }
                }
            }
        }    
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 150; j++) {
                if (j == 0) {
                    inicial[2][posicao_vetor(i, j)] = (float) ((inicial[2][posicao_vetor(i, j)] + inicial[2][posicao_vetor(i+1, j)]) / 2.0);
                } else {
                    if (j == 149) {
                        inicial[2][posicao_vetor(i, j)] = (float) ((inicial[2][posicao_vetor(i, j)] + inicial[2][posicao_vetor(i-1, j)]) / 2.0);
                    } else {
                        inicial[2][posicao_vetor(i, j)] = (float) ((inicial[2][posicao_vetor(i, j)] + inicial[2][posicao_vetor(i+1, j)] + inicial[2][posicao_vetor(i-1, j)]) / 3.0);
                    }
                }
            }
        }   
        return inicial;
    }
}
