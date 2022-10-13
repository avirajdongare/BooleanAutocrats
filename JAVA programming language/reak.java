import java.util.*;
public class reak {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("enter ur no :");
            int n = sc.nextInt();
            if(n%10==0){
                break;
            }
            System.out.println(n);
        } while(true);

    }
    
}
