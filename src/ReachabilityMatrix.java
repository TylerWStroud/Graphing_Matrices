// Name: Tyler Stroud
// Class: CS 3305/Section 01
// Term: Fall 2024
// Instructor: Dr. Haddad
// Assignment: 9
// IDE Name: IntelliJ
public class ReachabilityMatrix {
    //private static int[][] Matrix;
    private static int nodeCount;
    private static int[][] A1;
    private static int[][] A2;
    private static int[][] A3;
    private static int[][] A4;
    private static int[][] A5;
    private static int[][] reachabilityMatrix;
    private static int[][] AN;

    // Main body
    public static void main(String[] args) throws Exception{
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int input = 0;
        // Menu
        while(input !=3){
            try{
                System.out.print("\n------MAIN MENU------\n"+
                                 "1. Enter Graph Data\n"+
                                 "2. Print Outputs\n"+
                                 "3. Exit Program\n\n"+
                                 "Enter option number: ");
                input = Integer.parseInt(sc.nextLine());

                switch(input){
                    /* Enter data */
                    case 1:
                        System.out.print("\nHow many nodes will your graph have? (no more than 5): ");
                        nodeCount = Integer.parseInt(sc.nextLine());
                        // checking for appropriate node count
                        while(nodeCount > 5 || nodeCount < 0 ) {
                            if(nodeCount > 5){
                                System.out.print("\nThat's too many nodes. \nPlease enter a node count less than, or equal to 5. : ");
                                nodeCount = Integer.parseInt(sc.nextLine());
                            }
                            else if(nodeCount < 0){
                                System.out.print("\nNot enough nodes.\nPlease enter a node count less than 5, but more than 0. : ");
                                nodeCount = Integer.parseInt(sc.nextLine());
                            }
                        }

                        // Matrix declarations
                        AN = new int[nodeCount][nodeCount];
                        A1 = new int[nodeCount][nodeCount];
                        A2 = new int[nodeCount][nodeCount];
                        A3 = new int[nodeCount][nodeCount];
                        A4 = new int[nodeCount][nodeCount];
                        A5 = new int[nodeCount][nodeCount];
                        reachabilityMatrix = new int[nodeCount][nodeCount];

                        System.out.println();
                            for(int j = 0 ; j < nodeCount; j++){
                                for(int k = 0; k < nodeCount; k++){
                                    System.out.print("Enter A1 ["+j+"]["+k+"]: ");
                                    A1[j][k] = Integer.parseInt(sc.nextLine());
                                }
                            }
                        break;

                    /* Print outputs */
                    case 2:
                        if(nodeCount < 1){
                            System.out.println("\nThere is nothing to print.\nEnter graph data first.\n");
                            break;
                        }
                        printInputMatrix();
                        printReachabilityMatrix();
                        printInDegree();
                        printOutDegree();
                        printLoops();
                        printCycles();
                        printPaths_Length1();
                        printPaths_LengthN();
                        printPaths_Length1ToN();
                        printCycles_Length1ToN();
                        System.out.println("--------------------------------------------------");
                        break;

                    /* Exit */
                    case 3:
                        System.out.println("\nGoodbye!");
                        break;
                    default:
                        System.out.println("\nInvalid menu option. Please select between options 1-3.");
                }
            }catch(NumberFormatException e){
                System.out.println("\nInvalid menu option. Please select between options 1-3.");
            }catch(NullPointerException e){
                System.out.println("\nYou must first input graph data to continue.");
            }catch(Exception e){
                System.out.println("\n"+e.getMessage()+"\n"+ java.util.Arrays.toString(e.getStackTrace())+"\n"+e.getClass()+"\n");
            }
        }
    }

    /** Methods **/

    public static void printInputMatrix(){
        System.out.println("Input Matrix:");
        for(int j = 0 ; j < nodeCount; j++) {
            for(int k = 0; k < nodeCount; k++) {
                System.out.print(A1[j][k]+"\t\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void  printReachabilityMatrix(){
        // calculating/populating matrices
        for(int A=2; A<=nodeCount; A++) {
            for (int i = 0; i < nodeCount; i++) {
                for (int j = 0; j < nodeCount; j++) {
                    for (int k = 0; k < nodeCount; k++) {
                        switch(A){
                            case 2:
                                A2[i][j] += A1[i][k] * A1[k][j];
                                AN[i][j] = A2[i][j];
                                break;
                            case 3:
                                A3[i][j] += A2[i][k] * A1[k][j];
                                AN[i][j] = A3[i][j];
                                break;
                            case 4:
                                A4[i][j] += A3[i][k] * A1[k][j];
                                AN[i][j] = A4[i][j];
                                break;
                            case 5:
                                A5[i][j] += A4[i][k] * A1[k][j];
                                AN[i][j] = A5[i][j];
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }

        // calculating/printing reachability matrix
        System.out.println("Reachability Matrix:");
        for (int j = 0; j < nodeCount; j++) {
            for (int k = 0; k < nodeCount; k++) {
                reachabilityMatrix[j][k] = A1[j][k] + A2[j][k] + A3[j][k] + A4[j][k] + A5[j][k];
                System.out.print(reachabilityMatrix[j][k]+"\t\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void  printInDegree(){
        System.out.println("In-degrees:");
        int inDegree;
        for(int i = 0; i < nodeCount; i++){
            inDegree = 0;
            for(int j = 0; j < nodeCount; j++){
                inDegree += A1[j][i];
            }
            System.out.println("Node "+ (i+1) +" in-degree is "+inDegree);
        }
        System.out.println();
    }

    public static void  printOutDegree(){
        System.out.println("Out-degrees:");
        int outDegree;
        for(int i = 0; i < nodeCount; i++){
            outDegree = 0;
            for(int j = 0; j < nodeCount; j++){
                outDegree += A1[i][j];
            }
            System.out.println("Node "+ (i+1) +" out-degree is "+outDegree);
        }
        System.out.println();
    }

    public static void  printLoops(){
        int loops = 0;
        for(int i = 0; i < nodeCount; i++){
            loops += A1[i][i];
        }
        System.out.println("Total number of self-loops: "+loops);
    }

    public static void  printCycles(){
        int cycles = 0;
        for(int i = 0; i < nodeCount; i++){
            cycles += AN[i][i];
        }
        System.out.println("Total number of cycles of length "+nodeCount+" edges: "+cycles);
    }

    public static void  printPaths_Length1(){
        int pathsL1=0;
        for(int j = 0; j < nodeCount; j++){
            for(int k = 0; k < nodeCount; k++){
                pathsL1 += A1[j][k];
            }
        }
        System.out.println("Total number of paths of length 1 edge: "+pathsL1);
    }

    public static void  printPaths_LengthN(){
        if(nodeCount > 1) {
            int pathsLN = 0;
            for (int j = 0; j < nodeCount; j++) {
                for (int k = 0; k < nodeCount; k++) {
                    pathsLN += AN[j][k];
                }
            }
            System.out.println("Total number of paths of length " + nodeCount + " edges: " + pathsLN);
        }
    }

    public static void  printPaths_Length1ToN(){
        int pathsL1N=0;
        for(int j = 0; j < nodeCount; j++){
            for(int k = 0; k < nodeCount; k++){
                pathsL1N += reachabilityMatrix[j][k];
            }
        }
        System.out.println("Total number of paths of length 1 to "+nodeCount+" edges: "+pathsL1N);
    }

    public static void printCycles_Length1ToN(){
        int cycles = 0;
        for(int i = 0; i < nodeCount; i++){
            cycles += reachabilityMatrix[i][i];
        }
        System.out.println("Total number of cycles of length 1 to "+nodeCount+" edges: "+cycles);
    }
}
