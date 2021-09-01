package library.localsearch.domainspecific.vehiclerouting.apps.minmaxvrp.experiments;

import library.localsearch.domainspecific.vehiclerouting.apps.minmaxvrp.MMSearch;
import library.localsearch.domainspecific.vehiclerouting.apps.minmaxvrp.MinMaxCVRP;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;
import library.localsearch.domainspecific.vehiclerouting.vrp.neighborhoodexploration.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class MinMaxCVRPMultiNeighborhoods extends MinMaxCVRP{

	public MinMaxCVRPMultiNeighborhoods(){
		super();
	}
	public void search(int timeLimit) {
		HashSet<Point> mandatory = new HashSet<Point>();
		for(Point p: clientPoints) mandatory.add(p);
		
		ArrayList<INeighborhoodExplorer> NE = new ArrayList<INeighborhoodExplorer>();
		NE.add(new GreedyOnePointMoveExplorer(XR, F));
		NE.add(new GreedyCrossExchangeMoveExplorer(XR, F));
		
		NE.add(new GreedyOrOptMove1Explorer(XR, F));
		NE.add(new GreedyOrOptMove2Explorer(XR, F));
		NE.add(new GreedyThreeOptMove1Explorer(XR, F));
		NE.add(new GreedyThreeOptMove2Explorer(XR, F));
		NE.add(new GreedyThreeOptMove3Explorer(XR, F));
		NE.add(new GreedyThreeOptMove4Explorer(XR, F));
		NE.add(new GreedyThreeOptMove5Explorer(XR, F));
		NE.add(new GreedyThreeOptMove6Explorer(XR, F));
		NE.add(new GreedyThreeOptMove7Explorer(XR, F));
		NE.add(new GreedyThreeOptMove8Explorer(XR, F));
		NE.add(new GreedyTwoOptMove1Explorer(XR, F));
		NE.add(new GreedyTwoOptMove2Explorer(XR, F));
		NE.add(new GreedyTwoOptMove3Explorer(XR, F));
		NE.add(new GreedyTwoOptMove4Explorer(XR, F));
		NE.add(new GreedyTwoOptMove5Explorer(XR, F));
		NE.add(new GreedyTwoOptMove6Explorer(XR, F));
		NE.add(new GreedyTwoOptMove7Explorer(XR, F));
		NE.add(new GreedyTwoOptMove8Explorer(XR, F));
		
		
		
		//NE.add(new GreedyKPointsMoveExplorer(XR, F, 2, mandatory));
		
		MMSearch se = new MMSearch(mgr);
		
		//GenericLocalSearch se = new GenericLocalSearch(mgr);
		se.setNeighborhoodExplorer(NE);
		se.setObjectiveFunction(F);
		se.setMaxStable(50);
		se.adaptNeighborhood = false;
		
		se.initLog("MinMaxCVRPMultiNeighborhoods-log.txt");
		se.search(10000, timeLimit);
		se.finalizeLog();
		best_obj = obj.getValue();
		time_to_best = se.getTimeToBest();
		
		print();

	}

	public static void run(String fi, String fo, int timeLimit){
		try{
			MinMaxCVRPMultiNeighborhoods vrp = new MinMaxCVRPMultiNeighborhoods();
			vrp.readData(fi);
			vrp.mapping();
			vrp.stateModel();
			vrp.search(timeLimit);
			
			PrintWriter out = new PrintWriter(fo);
			out.print(vrp.best_obj + "\t" + vrp.time_to_best);
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MinMaxCVRPMultiNeighborhoods vrp = new MinMaxCVRPMultiNeighborhoods();
		vrp.readData("data/MinMaxVRP/Christophides/std-all/E-n101-k14.vrp");
		vrp.mapping();
		vrp.stateModel();
		vrp.search(300);

	}

}
