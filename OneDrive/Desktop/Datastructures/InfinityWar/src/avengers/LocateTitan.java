package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }
        String locateTitanInput = args[0];
        String locateTitanOutput = args[1];

        StdIn.setFile(locateTitanInput);
    	int g = StdIn.readInt();
        int[] genNumbers = new int[g];
        int[][] matrix = new int[g][g];
        double[] functionality = new double[g];
        for(int i = 0; i<g; i++)
        {
            genNumbers[i] = StdIn.readInt();
            functionality[i] = StdIn.readDouble();
        }
        for(int row = 0; row<matrix.length; row++)
        {
            for(int col = 0; col<matrix[row].length; col++)
            {
                matrix[row][col] = StdIn.readInt();
            }
        }
        StdOut.setFile(locateTitanOutput);
        for(int row = 0; row<matrix.length; row++)
        {
            for(int col = 0; col<matrix[row].length; col++)
            {
                int totalCost = (int)(matrix[row][col]/(functionality[row] * functionality[col]));
                matrix[row][col] = totalCost;
            }
        }
        int minCost[] = new int[g];
        boolean[] dijkstraSet = new boolean[g];
        for(int i = 0; i<minCost.length; i++)
        {
            if(i==0)
                minCost[i] = 0;
            else
                minCost[i] = Integer.MAX_VALUE;
        }
        for(int i = 0; i<minCost.length-1; i++)
        {
            int currentSource = getMinCostNode(minCost, dijkstraSet);
            dijkstraSet[currentSource] = true;

            for(int w = 0; w<minCost.length; w++)
            {
                if(dijkstraSet[w]==false && matrix[currentSource][w]!=0 && minCost[currentSource]!=Integer.MAX_VALUE && minCost[currentSource] + matrix[currentSource][w] < minCost[w])
                {
                    minCost[w] = minCost[currentSource] + matrix[currentSource][w];
                }
            }
        }
        StdOut.print(minCost[minCost.length - 1]);
    }
    private static int getMinCostNode(int[]minCost, boolean[] dijkSet)
    {
        int min = Integer.MAX_VALUE;
        int min_index = -1;
        for(int i = 0; i<minCost.length; i++)
        {
            if(dijkSet[i]==false && minCost[i]<=min)
            {
                min = minCost[i];
                min_index = i;
            }
        }
        return min_index;
    }
}
