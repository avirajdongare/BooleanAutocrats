import java.util.*;

public class sum {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int sum = 0;
        int i = 1;
        while (i<=n){
            sum += i;
            i++;
        }
        System.out.println("sum is " + sum);
        
    }
}
