// Name: J4-18
// Date: 9/28/19
  
import java.util.*;
public class Fibonacci
{
   public static void main(String[] args)
   {
       long start, end, fib; //why long?
       int[] fibNumber = {1, 5, 10, 20, 30, 40, 41, 42};
       System.out.println("\tFibonacci\tBy Iteration\tTime\tby Recursion\t Time");
       for(int n = fibNumber[0]; n <= fibNumber[fibNumber.length - 1]; n++)
       { 
           start = System.nanoTime();
           fib = fibIterate(n);
           end = System.nanoTime();
           System.out.print("\t\t" + n + "\t\t" + fib + "\t" + (end-start)/1000.);
           start = System.nanoTime();   	
           fib = fibRecur(n);      
           end = System.nanoTime();
           System.out.println("\t" + fib + "\t\t" + (end-start)/1000.);
       }
   }
   
   /**
    * Calculates the nth Fibonacci number by interation
    * @param n A variable of type int representing which Fibonacci number
    *          to retrieve
    * @returns A long data type representing the Fibonacci number
    */
   public static long fibIterate(int n)
   {
       long n1 = 0;
       long n2 = 1; 
       long nth = 1;
       if(n == 0)
       {
           return n1;
       }
       else if(n == 1)
       {
           return n2;
       }
       else
       {
         for(int i = 2; i <= n; i++)
           {
              nth = n1 + n2;
              n1 = n2;
              n2 = nth;
           }
       }
       return nth;      
   }

   /**
    * Calculates the nth Fibonacci number by recursion
    * @param n A variable of type int representing which Fibonacci number
    *          to retrieve
    * @returns A long data type representing the Fibonacci number
    */
   public static long fibRecur(int n)
   {
      if(n == 0)
      {
         return 0;
      }
      else if(n == 1 || n == 2)
      {
         return 1;
      }
      return fibRecur(n-1) + fibRecur(n-2);
   }
}