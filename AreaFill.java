// Name: J4-18
// Date: 10/1/19

import java.util.*;
import java.io.*;

public class AreaFill
{
   private static char[][] grid = null;
   private static String filename = null;

   public static void main(String[] args) 
   {
      Scanner sc = new Scanner(System.in);
      while(true)  // what does this do?
      {
         System.out.print("Fill the Area of (-1 to exit): ");
         filename = sc.next();
         if(filename.equals("-1"))
         {
            sc.close();
            System.out.println("Good-bye");
            //System.exit(0); 
            return;  
         }
         grid = read(filename);
         String theGrid = display(grid);
         System.out.println( theGrid );
         System.out.print( "1-Fill or 2-Fill-and-Count: ");
         int choice = sc.nextInt();
         switch(choice)
         {
            case 1:
               {
                  System.out.print("\nEnter ROW COL to fill from: ");
                  int row = sc.nextInt();
                  int col = sc.nextInt(); 
                  fill(grid, row, col, grid[row][col]);
                  System.out.println( display(grid) );
                  break;
               }
            case 2:
               {
                  System.out.print("\nEnter ROW COL to fill from: ");
                  int row = sc.nextInt();
                  int col = sc.nextInt();
                  int count = fillAndCount(grid, row, col, grid[row][col]);
                  System.out.println( display(grid) );
                  System.out.println("count = " + count);
                  System.out.println();
                  break;
               }
            default:
               System.out.print("\nTry again! ");
         }
      }
   }
   
   /**
    * Reads the contents of the file into a matrix.
    * Uses try-catch.
    * @param filename The string representing the filename.
    * @returns A 2D array of chars.
    */
   public static char[][] read(String filename)
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(filename));
      }
      catch (Exception e)
      {
         System.out.println("File not found");
         return null;
      }
      String read = infile.nextLine();
      String[] array = read.split(" ");
      int r = Integer.parseInt(array[0]);
      int c = Integer.parseInt(array[1]);
      
      char[][] matrix = new char[r][c];
      for(int i = 0; i < r; i++)
      {
         matrix[i] = infile.nextLine().toCharArray();
      }
      
      return matrix;
   }
   
   /**
    * @param g A 2-D array of chars.
    * @returns A string representing the 2D array.
    */
   public static String display(char[][] g)
   {
      String outfile = "";
      for(int i = 0; i < g.length; i++)
      {
         for(int j = 0; j < g[0].length; j++)
         {
            outfile += g[i][j];
         }
         outfile += "\n";
      }
      return outfile;
   }
   
   /**
    * Fills part of the matrix with a different character.
    * @param g A 2D char array.
    * @param r An int representing the row of the cell to be filled.
    * @param c An int representing the column of the cell to be filled.
    * @param ch A char representing the replacement character.
    */
   public static void fill(char[][] g, int r, int c, char ch)
   {
      int rows = g.length;
      int columns = g[0].length;
      if (g[r][c] != ch || g[r][c] == '*')
         return;
      g[r][c] = '*'; 
      if ((c > 0)) 
         fill(g, r, c - 1, ch);
      if ((c < columns - 1))
         fill(g, r, c + 1, ch);
      if  ((r > 0))
         fill(g, r - 1, c, ch);
      if ((r < rows - 1))
         fill(g, r + 1, c, ch);
      return;
   }
   
   /**
    * Fills part of the matrix with a different character.
    * Counts as you go.  Does not use a static variable.
    * @param g A 2D char array.
    * @param r An int representing the row of the cell to be filled.
    * @param c An int representing the column of the cell to be filled.
    * @param ch A char representing the replacement character.
    * @return An int representing the number of characters that were replaced.
    */
   public static int fillAndCount(char[][] g, int r, int c, char ch)
   {
      int count = 0;
      int rows = g.length;
      int columns = g[0].length;
   
      if ((r > rows - 1) || (c > columns - 1) || (c < 0) || (r < 0))
         return 0;
      else if (!(ch == g[r][c]))
         return 0;
      else
      {
         g[r][c] = '*'; 
         count += 1;
         count += fillAndCount(g, r + 1, c, ch);
         count += fillAndCount(g,r,c-1,ch);
         count += fillAndCount(g, r, c+1, ch);
         count += fillAndCount(g, r-1, c, ch);
         return count;
      }
   }
}