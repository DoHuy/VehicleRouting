package vrp.program;

import java.io.IOException;

public class Start {

	public static void main(String[] args) throws IOException{
                        String filename = "F:\\VRP\\input\\weight.txt";
                        String file = "F:\\VRP\\input\\cost.txt";
                
			
                        Stopwatch time = new Stopwatch();
                        time.start();
                        VRPProgram.readData(file);
                        VRPProgram.readWeight(filename);
                        VRPProgram.clarkWright();
                        time.stop();
                        System.out.println(time.toString());
		//MyUtils.generateRandomNodes(100,300,15);

	}
}
