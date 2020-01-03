// Name: J4-18
// Date: 10/10/19

import java.util.*;
import java.io.*;

public class MazeMaster
{
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter the maze's filename (no .txt): ");
      Maze m = new Maze(sc.next()); 
      //Maze m = new Maze();    //extension - I set the range from 3 to 22 but can change
      m.display();      
      System.out.println("Options: ");
      System.out.println("1: Mark all dots.");
      System.out.println("2: Mark all dots and display the number of recursive calls.");
      System.out.println("3: Mark only the correct path.");
      System.out.println("4: Mark only the correct path. If no path exists, say so.");
      System.out.println("5: Mark only the correct path and display the number of steps.\n\tIf no path exists, say so.");
      System.out.print("Please make a selection: ");
      m.solve(sc.nextInt());
      m.display();      //display solved maze
   } 
}

class Maze
{
   //constants
   private final char WALL = 'W';
   private final char DOT = '.';
   private final char START = 'S';
   private final char EXIT = 'E';
   private final char TEMP = 'o';
   private final char PATH = '*';
   //instance fields
   private char[][] maze;
   private int startRow, startCol;
  
   //constructors
	
	/* 
	 * EXTENSION 
	 * This is a no-arg constructor that generates a random maze
	 */
   public Maze()
   {
      int j = 0;
      boolean start = false;
      boolean end = false;
      int row = (int)((Math.random() * 20) + 3);
      int col = (int)((Math.random() * 20) + 3);
      maze = new char[row][col];
      
      for(int r = 0; r < maze.length; r++)
      {
         for(int c = 0; c < maze[0].length; c++)
         { 
             j = (int)(Math.random() * 4);
             if(j == 0)
             {
                 maze[r][c] = 'W';
             }
             else if(j == 1)
             {
                 maze[r][c] = '.';
             }
             else if(j == 2 && start == false)
             {
                 maze[r][c] = 'S';
                 start = true;
             }
             else if(j == 3 && end == false)
             {
                 maze[r][c] = 'E';
                 end = true;
             }
         }
      }
      if(start == false)
      {
           maze[(int)(Math.random() * row)][(int)(Math.random() * col)] = 'S';
           start = true;
      }
      if(end == false)
      {
           maze[(int)(Math.random() * row)][(int)(Math.random() * col)] = 'E';
           end = true;
      }
      for(int r = 0; r < maze.length; r++)
      {
         for(int c = 0; c < maze[0].length; c++)
         { 
            if(maze[r][c] == START)      //identify start
            {
               startRow = r;
               startCol = c;
            }
         }
      }

   }
	
	/* 
	 * Copy Constructor  
	 */
   public Maze(char[][] m)  
   {
      maze = m;
      for(int r = 0; r < maze.length; r++)
      {
         for(int c = 0; c < maze[0].length; c++)
         { 
            if(maze[r][c] == START)      //identify start location
            {
               startRow = r;
               startCol = c;
            }
         }
      }
   } 
	
	/* 
	 * Use a try-catch block
	 * Use next(), not nextLine()  
	 */
   public Maze(String filename)    
   {
      Scanner infile = null;
      try
      {
         filename = filename + ".txt";
         infile = new Scanner(new File(filename));  
      }
      catch(IOException e)
      {
         System.out.println("oops");
         System.exit(0);   
      }
      int rows = infile.nextInt();
      int columns = infile.nextInt();
      maze = new char[rows][columns];
      
      String temp = infile.nextLine();
      int rowc = 0;
      
      while (infile.hasNextLine())
      {
         String str = infile.nextLine();
         int i = 0;
         while (i < str.length())
         {
            maze[rowc][i] = str.charAt(i);
            i++;
         }
         rowc ++;
      }
      
      for(int r = 0; r < maze.length; r++)
      {
         for(int c = 0; c < maze[0].length; c++)
         { 
            if(maze[r][c] == START)      //identify start
            {
               startRow = r;
               startCol = c;
            }
         }
      }
   
   }
   
   public char[][] getMaze()
   {
      return maze;
   }
   
   public void display()
   {
      if(maze==null) 
         return;
      for(int a = 0; a<maze.length; a++)
      {
         for(int b = 0; b<maze[0].length; b++)
         {
            System.out.print(maze[a][b]);
         }
         System.out.println();
      }
      System.out.println();
   }
   
   public void solve(int n)
   {
      switch(n)
      {
         case 1:
            {
               markAll(startRow, startCol);
               break;
            }
         case 2:
            {
               int count = markAllAndCountRecursions(startRow, startCol) + 3;
               System.out.println("Number of recursions = " + count);
               break;
            }
         case 3:
            {
               markTheCorrectPath(startRow, startCol);
               break;
            }
         case 4:         //use mazeNoPath.txt 
            {
               if( !markTheCorrectPath(startRow, startCol) )
                  System.out.println("No path exists."); 
               break;
            }
         case 5:
            {
               if( !markCorrectPathAndCountSteps(startRow, startCol, 0) )
                  System.out.println("No path exists."); 
               break;
            }
         default:
            System.out.println("File not found");   
      }
   }
   
	/* 
	 * From handout, #1.
	 * Fill the maze, mark every step.
	 * This is a lot like AreaFill.
	 */ 
   public void markAll(int r, int c)
   {
      int rows = maze.length;
      int columns = maze[0].length;
      if (maze[r][c] == 'S')
      {
         if (r+1 < rows)
            markAll(r + 1, c);
         if (c+1 < columns)
            markAll(r, c + 1);
         if (c > 0) 
            markAll(r, c - 1);
         if  (r > 0)
            markAll(r - 1, c);
         return; 
      }
      else
      {
         if (maze[r][c] != '.')
            return;
         maze[r][c] = '*'; 
         if (r+1 < rows)
            markAll(r + 1, c);
         if (c+1 < columns)
            markAll(r, c + 1);
         if (c > 0) 
            markAll(r, c - 1);
         if  (r > 0)
            markAll(r - 1, c);
      }    
      return; 

   }

	/* 
	 * From handout, #2.
	 * Fill the maze, mark and count every step as you go.
	 * Like AreaFill's counting without a static variable.
	 */ 
   public int markAllAndCountRecursions(int r, int c)
   {
      int count = 0;
      int rows = maze.length;
      int columns = maze[0].length;
   
      if (!(r <= rows))
         return 0;
      if (!(c <= columns))
         return 0;
      if (!(c >= 0))
         return 0;
      if (!(r >= 0))
         return 0;
         
      try
      {
         if (maze[r][c] == 'E')
            return 3;
      }
      catch (ArrayIndexOutOfBoundsException exception)
      {
         return 0;
      }
      try
      {
         if (maze[r][c] == 'S')
         {
            count += 1;
            count += (markAllAndCountRecursions(r + 1, c) + markAllAndCountRecursions(r,c-1) + markAllAndCountRecursions(r, c+1) +  markAllAndCountRecursions(r-1, c));
            return count;
         }
      }
      catch (ArrayIndexOutOfBoundsException exception)
      {
         return 0;
      }
      if (maze[r][c] == 'W' || maze[r][c] == '*')
         return 1;
      maze[r][c] = '*'; 
      count += 1;
      count += (markAllAndCountRecursions(r + 1, c) + markAllAndCountRecursions(r,c-1) + markAllAndCountRecursions(r, c+1) +  markAllAndCountRecursions(r-1, c));
      return count;

   }

   /* 
	 * From handout, #3.
	 * Solve the maze, OR the booleans, and mark the path through it with a “*” 
	 * Recur until you find E, then mark the True path.
	 */ 	
   public boolean markTheCorrectPath(int r, int c)
   {
      int rows = maze.length;
      int columns = maze[0].length;
      if (!(r <= rows))
         return false;
      if (!(c <= columns))
         return false;
      if (!(c >= 0))
         return false;
      if (!(r >= 0))
         return false;
      try
      {
         if (maze[r][c] == 'E')
            return true;
      }
      catch (ArrayIndexOutOfBoundsException exception)
      {
         return false;
      }
      if (maze[r][c] == 'S')
      {  
         if (markTheCorrectPath(r, c + 1) ||markTheCorrectPath(r- 1, c) || markTheCorrectPath(r + 1, c) || markTheCorrectPath(r, c - 1)  )
         {
            //maze[r][c] = 'S';
            return true;
         }
      }
      else if (maze[r][c] == 'W' || maze[r][c] == '*')
         return false;
      maze[r][c] = '*';
      if (markTheCorrectPath(r, c + 1) ||markTheCorrectPath(r- 1, c) || markTheCorrectPath(r + 1, c) || markTheCorrectPath(r, c - 1)  )
         return true;
         
      maze[r][c] = '.';
      return false;
   }
	
	
   /*  4   Mark only the correct path. If no path exists, say so.
           Hint:  the method above returns the boolean that you need.  */
      

   /* 
	 * From handout, #5.
	 * Solve the maze, mark the path, count the steps. 	 
	 * Mark only the correct path and display the number of steps.
	 * If no path exists, say so.
	 */ 	
   public boolean markCorrectPathAndCountSteps(int r, int c, int count)
   {
      int rows = maze.length;
      int columns = maze[0].length;
      int a = count;
      if (!(r <= rows))
         return false;
      if (!(c <= columns))
         return false;
      if (!(c >= 0))
         return false;
      if (!(r >= 0))
         return false;
      try
      {
         if (maze[r][c] == 'E')
         {
            System.out.println("Number of steps = " + count);
            return true;
         }
      }
      catch (ArrayIndexOutOfBoundsException exception)
      {
         return false;
      }
      if (maze[r][c] == 'S')
      {  
         if(markCorrectPathAndCountSteps(r, c + 1, a + 1) ||markCorrectPathAndCountSteps(r- 1, c, a + 1) || markCorrectPathAndCountSteps(r + 1, c, a + 1) || markCorrectPathAndCountSteps(r, c - 1, a + 1)  )
         {
            //maze[r][c] = 'S';
            return true;
         }
      }
      else if (maze[r][c] == 'W' || maze[r][c] == '*')
         return false;
      maze[r][c] = '*';
      if (markCorrectPathAndCountSteps(r, c + 1, a + 1) ||markCorrectPathAndCountSteps(r- 1, c, a + 1) || markCorrectPathAndCountSteps(r + 1, c, a + 1) || markCorrectPathAndCountSteps(r, c - 1, a + 1)  )
         return true;
         
      maze[r][c] = '.';
      return false;

   }
}

/*****************************************
 
 ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 1
 WWWWWWWW
 W****W*W
 WW*WW**W
 W****W*W
 W*W*WW*E
 S*W*WW*W
 WW*****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 2
 Number of recursions = 105
 WWWWWWWW
 W****W*W
 WW*WW**W
 W****W*W
 W*W*WW*E
 S*W*WW*W
 WW*****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 3
 WWWWWWWW
 W....W.W
 WW.WW..W
 W***.W.W
 W*W*WW*E
 S*W*WW*W
 WW.****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
     
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): mazeNoPath
 WWWWWWWW
 W....W.W
 WW.WW..E
 W..WW.WW
 W.W.W..W
 S.W.WW.W
 WWW....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 4
 No path exists.
 WWWWWWWW
 W....W.W
 WW.WW..E
 W..WW.WW
 W.W.W..W
 S.W.WW.W
 WWW....W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 5
 Number of steps = 14
 WWWWWWWW
 W....W.W
 WW.WW..W
 W***.W.W
 W*W*WW*E
 S*W*WW*W
 WW.****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
  ********************************************/