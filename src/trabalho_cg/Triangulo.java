/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trabalho_cg;

import java.io.Serializable;

/**
 *
 * @author seven
 */
public class Triangulo implements Serializable{
    int p1,p2,p3;
    
    public Triangulo(int a, int b, int c){
        p1=a;
        p2=b;
        p3=c;
    }
    
    public int[] getTriangulo(){
        int t[]=new int[3];
        t[0]=p1;
        t[1]=p2;
        t[2]=p3;
        return t;
    }
    
    public static boolean verifica_visibilidade_triangulo(Triangulo T,double[][] pontos, double n[]){
        int tri[] = T.getTriangulo();
        double a=((pontos[1][tri[2]]-pontos[1][tri[1]])
                *(pontos[2][tri[0]]-pontos[2][tri[1]]))
                -((pontos[1][tri[0]]-pontos[1][tri[1]])
                *(pontos[2][tri[2]]-pontos[2][tri[1]]));
        
        double b=((pontos[2][tri[2]]-pontos[2][tri[1]])
                *(pontos[0][tri[0]]-pontos[0][tri[1]]))
                -((pontos[2][tri[0]]-pontos[2][tri[1]])
                *(pontos[0][tri[2]]-pontos[0][tri[2]]));
        
        
        double c=((pontos[0][tri[2]]-pontos[0][tri[1]])
                *(pontos[1][tri[0]]-pontos[1][tri[1]]))
                -((pontos[0][tri[0]]-pontos[0][tri[1]])
                *(pontos[1][tri[2]]-pontos[1][tri[1]]));
        
       double r=n[0]*a+n[1]*b+n[2]*c;
       if(r>0){
           return true;
       }
        return false;
    }
    
}
