import java.util.*;
public class oddeven {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int number;
        int choice;
        int evensum = 0;
        int oddsum = 0;
        do{
            System.out.println("enter ur no :");
            number = sc.nextInt();
            
            if(number%2==0){
                evensum += number;
            }
            else{
                oddsum += number;
            }
            System.out.println("1 for yes,0 for no");
            choice = sc.nextInt();
            
        } while(choice==1);
        System.out.println("sum of evensum" + evensum);
        System.out.println("sum of oddsum" + oddsum);


    }
}
