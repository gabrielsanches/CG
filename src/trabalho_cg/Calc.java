//observacoes
//o pontos devem estar da seguinte forma
// p[0][0]=x;
// p[1][0]=y;
// p[2][0]=z;
// p[3][0]=w;
// p[0][1]=x;
// p[1][1]=y;
// p[2][1]=z;
// p[3][1]=w;
package trabalho_cg;

public class Calc {

    //calcular o vetor n
    public static double[] calcular_vetor_n(double VRP[], double P[]) {
        double resultado[] = new double[3];
        resultado[0] = VRP[0] - P[0];
        resultado[1] = VRP[1] - P[1];
        resultado[2] = VRP[2] - P[2];
        double n = (double) Math.sqrt(Math.pow(resultado[0], 2)
                + Math.pow(resultado[1], 2) + Math.pow(resultado[1], 2));
        resultado[0] /= n;
        resultado[1] /= n;
        resultado[2] /= n;
        return resultado;
    }

    //calcular o vetor v
    public static double[] calcular_vetor_v(double n[], double y[]) {
        double resultado[] = new double[3];
        double yx = (y[0] * n[0]) + (y[1] * n[1]) + (y[2] * n[2]);
        resultado[0] = y[0] - (yx * n[0]);
        resultado[1] = y[1] - (yx * n[1]);
        resultado[2] = y[2] - (yx * n[2]);
        double v = (double) Math.sqrt(Math.pow(resultado[0], 2)
                + Math.pow(resultado[1], 2) + Math.pow(resultado[2], 2));
        resultado[0] /= v;
        resultado[1] /= v;
        resultado[2] /= v;
        return resultado;
    }

    //calcular o vetor u
    public static double[] calcular_vetor_u(double n[], double v[]) {
        double resultado[] = new double[3];
        resultado[0] = (v[1] * n[2]) - (v[2] * n[1]);
        resultado[1] = (v[2] * n[0]) - (v[0] * n[2]);
        resultado[2] = (v[0] * n[1]) - (v[1] * n[0]);
        return resultado;
    }

    //calcular matriz RT
    public static double[][] calcular_matriz_RT(double VRP[], double u[], double v[], double n[]) {
        double T[][] = new double[4][4];
        T[0][0] = 1;
        T[0][1] = 0;
        T[0][2] = 0;
        T[0][3] = -VRP[0];
        T[1][0] = 0;
        T[1][1] = 1;
        T[1][2] = 0;
        T[1][3] = -VRP[1];
        T[2][0] = 0;
        T[2][1] = 0;
        T[2][2] = 1;
        T[2][3] = -VRP[2];
        T[3][0] = 0;
        T[3][1] = 0;
        T[3][2] = 0;
        T[3][3] = 1;

        double R[][] = new double[4][4];
        R[0][0] = u[0];
        R[0][1] = u[1];
        R[0][2] = u[2];
        R[0][3] = 0;
        R[1][0] = v[0];
        R[1][1] = v[1];
        R[1][2] = v[2];
        R[1][3] = 0;
        R[2][0] = n[0];
        R[2][1] = n[1];
        R[2][2] = n[2];
        R[2][3] = 0;
        R[3][0] = 0;
        R[3][1] = 0;
        R[3][2] = 0;
        R[3][3] = 1;

        double resultado[][] = multiplicar_matriz(R, T);

        return resultado;
    }

    //determinar a posicao do observador
    public static double[][] posicao_obseravador(double VRP[], double matriz[][]) {
        double temp[][] = new double[4][1];
        temp[0][0] = VRP[0];
        temp[1][0] = VRP[1];
        temp[2][0] = VRP[2];
        temp[3][0] = 1;
        double res[][] = multiplicar_matriz(matriz, temp);

        return res;
    }

    //determinar a posicao observada no src
    public static double[][] posicao_centro_plano(double Centro_Plano[], double matriz[][]) {
        double temp[][] = new double[4][1];
        temp[0][0] = Centro_Plano[0];
        temp[1][0] = Centro_Plano[1];
        temp[2][0] = Centro_Plano[2];
        temp[3][0] = 1;
        temp = multiplicar_matriz(matriz, temp);
        return temp;
    }

    //determinar a matriz de projecao perspectiva
    public static double[][] matriz_perspectiva(double VRPL[][], double PL[][], double dist_vrp_plano) {    //pelo oq observei na eh necessario a distancia entre vrp e p por isso as duas proximas linhas estao comentadas
        //public static float[][] matriz_perspectiva(float VRP[], float P[], float VRPL[][],float PL[][]){
        //float VRP_P = (float) Math.sqrt(Math.pow(VRP[0]-P[0],2)+Math.pow(VRP[1]-P[1],2)+Math.pow(VRP[2]-P[2],2));

        //VRPL = VRP'
        //PL = P'
        double matriz[][] = new double[4][4];
        matriz[0][0] = 1;
        matriz[1][0] = 0;
        matriz[2][0] = 0;
        matriz[3][0] = 0;
        matriz[0][1] = 0;
        matriz[1][1] = 1;
        matriz[2][1] = 0;
        matriz[3][1] = 0;
        matriz[0][2] = 0;
        matriz[1][2] = 0;
        matriz[2][2] = ((-1) * PL[2][0]) / dist_vrp_plano;
        matriz[3][2] = -1 / dist_vrp_plano;
        matriz[0][3] = 0;
        matriz[1][3] = 0;
        matriz[2][3] = PL[2][0] * (VRPL[2][0] / dist_vrp_plano);
        matriz[3][3] = VRPL[2][0] / dist_vrp_plano;

        return matriz;
    }

    //monta a matriz composta entre a matriz RT e a Matriz de projecao perspectiva
    public static double[][] montar_matriz_composta(double RT[][], double perspectiva[][]) {
        return multiplicar_matriz(perspectiva, RT);
    }

    //multiplica os pontos pela matriz composta
    public static double[][] multiplicar_pontos(double pontos[][], double matriz[][]) {
        return multiplicar_matriz(matriz, pontos);
    }

    //colocar o objeto em perspectiva
    public static double[][] objeto_perspectiva(double pontos[][]) {
        double matriz[][] = new double[3][pontos[0].length];
        for (int i = 0; i < pontos[0].length; i++) {
            matriz[0][i] = pontos[0][i] / pontos[3][i];
            matriz[1][i] = pontos[1][i] / pontos[3][i];
            matriz[2][i] = 1;
        }
        return matriz;
    }

    //monta a maitriz perspetiva para srt
    // a matriz viewport deve ser da seguinter forma
    // umin umax
    // vmin vmax
    public static double[][] montar_perspetiva_srt(int altura, int largura, double PL[][], int[] viewport) {
        double window[][] = new double[4][2];
        window[0][0] = PL[0][0] - largura;
        window[0][1] = PL[0][0] + largura;
        window[1][0] = PL[1][0] - altura;
        window[1][1] = PL[1][0] + altura;
        window[2][0] = PL[2][0];
        window[2][1] = PL[2][0];
        window[3][0] = 1;
        window[3][1] = 1;

        double pers_srt[][] = new double[3][3];
        pers_srt[0][0] = (viewport[2] - viewport[0]) / (window[0][1] - window[0][0]);
        pers_srt[0][1] = 0;
        pers_srt[0][2] = (-1) * window[0][0] * pers_srt[0][0] + viewport[0];
        pers_srt[1][0] = 0;
        pers_srt[1][1] = (viewport[3] - viewport[1]) / (window[1][1] - window[1][0]);
        pers_srt[1][2] = (-1) * window[1][0] * pers_srt[1][1] + viewport[1];
        pers_srt[2][0] = 0;
        pers_srt[2][1] = 0;
        pers_srt[2][2] = 1;

        return pers_srt;
    }

    //colocar o objeto em coordenadas de tela
    public static double[][] objeto_cordenadas_tela(double pers_srt[][], double pontos[][]) {
        return multiplicar_matriz(pers_srt, pontos);
    }

    public static double[][] multiplicar_matriz(double A[][], double[][] B) {
        int mA = A.length;
        int nA = A[0].length;

        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        double[][] C = new double[mA][nB];
        for (int i = 0; i < mA; i++) {
            for (int j = 0; j < nB; j++) {
                for (int k = 0; k < nA; k++) {
                    C[i][j] += (A[i][k] * B[k][j]);
                }
            }
        }
        return C;

    }

}
