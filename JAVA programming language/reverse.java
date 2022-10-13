public class reverse {
    public static void main(String args[]){
        
        
        int n = 98765;
        
        while (n>0){
            int lastdig = n%10;
            n = n/10;
        System.out.print(lastdig);
        
    }
}
}
