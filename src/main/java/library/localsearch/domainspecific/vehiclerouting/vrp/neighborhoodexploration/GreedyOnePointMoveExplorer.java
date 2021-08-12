
/*
 * authors: PHAM Quang Dung (dungkhmt@gmail.com)
 * date: 27/09/2015
 */

package library.localsearch.domainspecific.vehiclerouting.vrp.neighborhoodexploration;

import library.localsearch.domainspecific.vehiclerouting.vrp.VRManager;
import library.localsearch.domainspecific.vehiclerouting.vrp.VarRoutesVR;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.LexMultiValues;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;
import library.localsearch.domainspecific.vehiclerouting.vrp.functions.LexMultiFunctions;
import library.localsearch.domainspecific.vehiclerouting.vrp.moves.IVRMove;
import library.localsearch.domainspecific.vehiclerouting.vrp.moves.OnePointMove;
import library.localsearch.domainspecific.vehiclerouting.vrp.search.ISearch;
import library.localsearch.domainspecific.vehiclerouting.vrp.search.Neighborhood;

public class GreedyOnePointMoveExplorer implements INeighborhoodExplorer {
	private VRManager mgr;
	private VarRoutesVR XR;
	private ISearch search;
	private LexMultiFunctions F;
	private LexMultiValues bestValue;
	private boolean firstImprovement = true;
	public GreedyOnePointMoveExplorer(VarRoutesVR XR, LexMultiFunctions F) {
		this.XR = XR;
		this.F = F;
		this.mgr = XR.getVRManager();
	}
	public GreedyOnePointMoveExplorer(VarRoutesVR XR, LexMultiFunctions F, boolean firstImprovement) {
		this.XR = XR;
		this.F = F;
		this.mgr = XR.getVRManager();
		this.firstImprovement = firstImprovement;
	}
	
	public GreedyOnePointMoveExplorer(ISearch search, VRManager mgr, LexMultiFunctions F){
		this.search = search;
		this.mgr = mgr;
		this.XR = mgr.getVarRoutesVR();
		this.F = F;
		this.bestValue = search.getIncumbentValue();
	}
	
	public void exploreNeighborhood(Neighborhood N, LexMultiValues bestEval) {
		// TODO Auto-generated method stub
		
		if(firstImprovement && N.hasImprovement()){
			System.out.println(name() + "::exploreNeighborhood, has improvement --> RETURN");
			return;
		}

		for (Point x : XR.getClientPoints()) {
			for (Point y : XR.getAllPoints()) {
				//System.out.println(name() + "::exploreNeighborhood, consider (" + x.ID + "," + y.ID + " of route " + XR.route(y) + ", index " + XR.index(y) + ")");
				if (XR.checkPerformOnePointMove(x, y)) {
					//System.out.println(name() + "::exploreNeighborhood, accept (" + x.ID + "," + y.ID + " of route " + XR.route(y) + ", index " + XR.index(y) + ")");
					LexMultiValues eval = F.evaluateOnePointMove(x, y);
					//System.out.println(name() + "::exploreNeighborhood, accept (" + x.ID + "," + y.ID + " of route " + XR.route(y) + ", index " + XR.index(y) + ") eval = " + eval.toString());
					if(eval.lt(bestEval)){
						N.clear();
						N.add(new OnePointMove(mgr, eval, x, y, this));
						bestEval.set(eval);
					} else if (eval.eq(bestEval)) {
						N.add(new OnePointMove(mgr, eval, x, y, this));
					}
					if(firstImprovement){
						if(eval.lt(0)) return;
					}
				}
			}
		}
		//System.out.println(name() + "::exploreNeighborhood finished");
	}
	public String name(){
		return "GreedyOnePointMoveExplorer";
	}
	public void performMove(IVRMove m){
		// DO NOTHING
	}
}
