// Name: J4-18
// Date: 9/25/19
  
import java.util.*;
public class Permutations
{
   public static int count = 0;
    
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("\nHow many digits? ");
      int n = sc.nextInt();
      leftRight("", n);          
      oddDigits("", n);
      superprime(n);
      //if(count==0)
          //System.out.println("no superprimes");
      //else
          //System.out.println("Count is "+count);
   }
   
    /**
     * Builds all the permutations of a string of length n containing Ls and Rs
     * @param s A string 
     * @param n An postive int representing the length of the string
     */
   public static void leftRight(String s, int n)
   {
      if(s.length() == n)
      {
         System.out.println(s);
         return;
      }
      leftRight(s + "L", n);
      leftRight(s + "R", n);

   }
   
    /**
     * Builds all the permutations of a string of length n containing odd digits
     * @param s A string 
     * @param n A postive int representing the length of the string
     */
   public static void oddDigits(String s, int n)
   {
      if(s.length() == n)
      {
         System.out.println(s);
         return;
      }
      oddDigits(s + "1", n);
      oddDigits(s + "3", n);
      oddDigits(s + "5", n);
      oddDigits(s + "7", n);
      oddDigits(s + "9", n);
   }
      
    /**
     * Builds all combinations of a n-digit number whose value is a superprime
     * @param n A positive int representing the desired length of superprimes  
     */
   public static void superprime(int n)
   {
      if(n >= 9)
      {
         System.out.println("there are no " + n + "-digit superprimes.");
         return;
      }
      for(int i = (int)(Math.pow(10, n-1)); i < (int)(Math.pow(10, n)); i++)
      {
         recur(i, n);
      }
   }

    /**
     * Recursive helper method for superprime
     * @param k The possible superprime
     * @param n A positive int representing the desired length of superprimes
     */
   private static void recur(int k, int n)
   {
      if(n < 1)
      {
         System.out.println(k);
         count++;
         return;
      }
      if(isPrime(Integer.parseInt((k + "").substring(0, n))))
      {
         recur(k, n-1);
      }
      else
      {
         return;
      }
   }

    /**
     * Determines if the parameter is a prime number.
     * @param n An int.
     * @return true if prime, false otherwise.
     */
   public static boolean isPrime(int n)
   {
      if(n==1)
      {
         return false;
      }
      if ( n > 2 && n%2 == 0 ) 
      {
         return false;
      }
      for(int i=3; i<((int)Math.sqrt(n)+2); i=i+2)
      {
         if((n%i==0))
         {
            return false;
         }
      }
      return true;  
   }
}
