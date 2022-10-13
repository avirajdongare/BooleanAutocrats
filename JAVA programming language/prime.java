 import java.util.*;
 public class prime {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("enter the no :");
        int n = sc.nextInt();
        boolean isPrime = true;
        for( int i = 2;i<=n-1;i++){
            if(n%i==0){
                isPrime = false;
            }
        }
        if(isPrime == true){
            System.out.println("prime");
        }
        else{
            System.out.println("notprime");
        }
    }
}
