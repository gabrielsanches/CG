

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
    public static float[] calcular_vetor_n(float VRP[], float P[]) {
        float resultado[] = new float[3];
        resultado[0] = VRP[0] - P[0];
        resultado[1] = VRP[1] - P[1];
        resultado[2] = VRP[2] - P[2];
        float n = (float) Math.sqrt(Math.pow(resultado[0], 2)
                + Math.pow(resultado[1], 2) + Math.pow(resultado[1], 2));
        resultado[0] /= n;
        resultado[1] /= n;
        resultado[2] /= n;
        return resultado;
    }

    //calcular o vetor v
    public static float[] calcular_vetor_v(float n[], float y[]) {
        float resultado[] = new float[3];
        float yx = (y[0] * n[0]) + (y[1] * n[1]) + (y[2] * n[2]);
        resultado[0] = y[0] - (yx * n[0]);
        resultado[1] = y[1] - (yx * n[1]);
        resultado[2] = y[2] - (yx * n[2]);
        float v = (float) Math.sqrt(Math.pow(resultado[0], 2)
                + Math.pow(resultado[1], 2) + Math.pow(resultado[2], 2));
        resultado[0] /= v;
        resultado[1] /= v;
        resultado[2] /= v;
        return resultado;
    }

    //calcular o vetor u
    public static float[] calcular_vetor_u(float n[], float v[]){
        float resultado[]= new float[3];
        resultado[0]=(v[1]*n[2])-(v[2]*n[1]);
        resultado[1]=(v[2]*n[0])-(v[0]*n[2]);
        resultado[2]=(v[0]*n[1])-(v[1]*n[0]);
        return resultado;
    }
    
    //calcular matriz RT
    public static float[][] calcular_matriz_RT(float VRP[],float u[],float v[], float n[]){
        float T[][] = new float[4][4];
        T[0][0]=1;
        T[0][1]=0;
        T[0][2]=0;
        T[0][3]=-VRP[0];
        T[1][0]=0;
        T[1][1]=1;
        T[1][2]=0;
        T[1][3]=-VRP[1];
        T[2][0]=0;
        T[2][1]=0;
        T[2][2]=1;
        T[2][3]=-VRP[2];
        T[3][0]=0;
        T[3][1]=0;
        T[3][2]=0;
        T[3][3]=1;

        
        float R[][] = new float[4][4];
        R[0][0]=u[0];
        R[0][1]=u[1];
        R[0][2]=u[2];
        R[0][3]=0;
        R[1][0]=v[0];
        R[1][1]=v[1];
        R[1][2]=v[2];
        R[1][3]=0;
        R[2][0]=n[0];
        R[2][1]=n[1];
        R[2][2]=n[2];
        R[2][3]=0;
        R[3][0]=0;
        R[3][1]=0;
        R[3][2]=0;
        R[3][3]=1;
        
        float resultado[][]=multiplicar_matriz(R,T);
        return resultado;
    }
    
    //determinar a posicao do observador
    public static float[][] posicao_obseravador(float VRP[],float matriz[][]){
        float temp[][]=new float[1][4];
        temp[0][0]=VRP[0];
        temp[0][1]=VRP[1];
        temp[0][2]=VRP[2];
        temp[0][3]=1;
        temp=multiplicar_matriz(matriz, temp);
        return temp;
    }
    
    //determinar a posicao observada no src
    public static float[][] posicao_centro_plano(float Centro_Plano[],float matriz[][]){
        float temp[][]=new float[1][4];
        temp[0][0]=Centro_Plano[0];
        temp[0][1]=Centro_Plano[1];
        temp[0][2]=Centro_Plano[2];
        temp[0][3]=1;
        temp=multiplicar_matriz(matriz, temp);
        return temp;
    }
    
    //determinar a matriz de projecao perspectiva
    public static float[][] matriz_perspectiva(float VRPL[][],float PL[][]){    //pelo oq observei na eh necessario a distancia entre vrp e p por isso as duas proximas linhas estao comentadas
    //public static float[][] matriz_perspectiva(float VRP[], float P[], float VRPL[][],float PL[][]){
        //float VRP_P = (float) Math.sqrt(Math.pow(VRP[0]-P[0],2)+Math.pow(VRP[1]-P[1],2)+Math.pow(VRP[2]-P[2],2));
        float VRPL_PL = (float) Math.sqrt(Math.pow(PL[0][0]-VRPL[0][0],2)+Math.pow(PL[1][0]-VRPL[1][0],2)+Math.pow(PL[2][0]-VRPL[2][0],2));
        
        //VRPL = VRP'
        //PL = P'
        //VRPL_PL distancia de VRPL ate PL
        
        float matriz[][]= new float[4][4];
        matriz[0][0]=1;
        matriz[1][0]=0;
        matriz[2][0]=0;
        matriz[3][0]=0;
        matriz[0][1]=0;
        matriz[1][1]=1;
        matriz[2][1]=0;
        matriz[3][1]=0;
        matriz[0][2]=0;
        matriz[1][2]=0;
        matriz[2][2]=PL[2][0]/VRPL_PL;
        matriz[3][2]=PL[2][0]*(VRPL[2][0]/VRPL_PL);
        matriz[0][3]=0;
        matriz[1][3]=0;
        matriz[2][3]=1/VRPL_PL;
        matriz[3][3]=VRPL[2][0]/VRPL_PL;
        
        return matriz;
    }
    
    //monta a matriz composta entre a matriz RT e a Matriz de projecao perspectiva
    public static float[][] montar_matriz_composta(float RT[][], float perspectiva[][]){
        return multiplicar_matriz(perspectiva,RT);
    }
    
    //multiplica os pontos pela matriz composta
    public static float[][] multiplicar_pontos(float pontos[][], float matriz[][]){
        return multiplicar_matriz(matriz, pontos);
    }
    
    //colocar o objeto em perspectiva
    public static float[][] objeto_perspectiva(float pontos[][]){
        float matriz[][]= new float[pontos.length][3];
        for(int i=0;i<pontos.length;i++){
            matriz[i][0]=pontos[i][0]/pontos[i][3];
            matriz[i][1]=pontos[i][1]/pontos[i][3];
            matriz[i][2]=1;
        }
        return matriz;
    }
    
    
    //monta a maitriz perspetiva para srt
    // a matriz viewport deve ser da seguinter forma
    // umin umax
    // vmin vmax
    public static float[][] montar_perspetiva_srt(int altura, int largura, float PL[][], int viewport[][]){
        float window[][]= new float[4][2];
        window[0][0]=PL[0][0]-largura;
        window[0][1]=PL[0][0]+largura;
        window[1][0]=PL[2][0]-altura;
        window[1][1]=PL[2][0]+altura;
        window[2][0]=PL[3][0];
        window[2][1]=PL[3][0];
        window[3][0]=1;
        window[3][0]=1;
        
        float pers_srt[][]= new float [3][3];
        pers_srt[0][0]=(viewport[1][0]-viewport[0][0])/(window[1][0]-window[0][0]);
        pers_srt[0][1]=0;
        pers_srt[0][2]=window[0][0]*pers_srt[0][0]+viewport[0][0];
        pers_srt[1][0]=0;
        pers_srt[1][1]=(viewport[1][1]-viewport[0][1]/(window[1][1]-window[0][1]));
        pers_srt[1][2]=window[0][1]*pers_srt[1][1]+viewport[0][1];
        pers_srt[2][0]=0;
        pers_srt[2][1]=0;
        pers_srt[2][2]=1;
        
        return pers_srt;
    }
    
    //colocar o objeto em coordenadas de tela
    public static float[][] objeto_cordenadas_tela(float pers_srt[][],float pontos[][]){
        return multiplicar_matriz(pers_srt, pontos);
    }
    
    
    private static float[][] multiplicar_matriz(float A[][], float[][] B) {
        int mA = A[0].length;
        int nA = A.length;
        int mB = B[0].length;
        int nB = B.length;
        if (nA != mB) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        float[][] C = new float[mB][nA];
        for (int i = 0; i < nA; i++) {
            for(int j = 0; j < mB; j++){
                for(int k = 0; k < nB; k++){
                    C[j][i] += (A[i][k] * B[k][j]); 
                }
            }
        }
        return C;
    }
    
}
