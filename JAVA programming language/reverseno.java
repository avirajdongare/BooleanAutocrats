public class reverseno {
    public static void main(String args[]){
        
        
        int n = 10899;
        int rev = 0;
        while (n>0){
            int lastdig = n%10;
            rev = (rev*10)+lastdig;
            n = n/10;
        System.out.println(rev);
        
    }
}
}
