import java.util.*;

public class laargest {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        if((A>B) && (A>C)){
            System.out.print("A");
        }
        else if(B>C){
            System.out.print("B");
        }
        else{
            System.out.print("C");
        }

    }
}
