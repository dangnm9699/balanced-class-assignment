
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
import library.localsearch.domainspecific.vehiclerouting.vrp.moves.TwoPointsMove;
import library.localsearch.domainspecific.vehiclerouting.vrp.search.ISearch;
import library.localsearch.domainspecific.vehiclerouting.vrp.search.Neighborhood;

public class GreedyTwoPointsMoveExplorer implements INeighborhoodExplorer {
	private VRManager mgr;
	private VarRoutesVR XR;
	private ISearch search;
	private LexMultiFunctions F;
	private LexMultiValues bestValue;
	private boolean firstImprovement = true;
	public GreedyTwoPointsMoveExplorer(VarRoutesVR XR, LexMultiFunctions F) {
		this.XR = XR;
		this.F = F;
		this.mgr = XR.getVRManager();
	}
	public GreedyTwoPointsMoveExplorer(VarRoutesVR XR, LexMultiFunctions F, boolean firstImprovement) {
		this.XR = XR;
		this.F = F;
		this.mgr = XR.getVRManager();
		this.firstImprovement = firstImprovement;
	}
	
	public GreedyTwoPointsMoveExplorer(ISearch search, VRManager mgr, LexMultiFunctions F){
		this.search = search;
		this.mgr = mgr;
		this.XR = mgr.getVarRoutesVR();
		this.F = F;
		this.bestValue = search.getIncumbentValue();
	}
	public String name(){
		return "GreedyTwoPointsMoveExplorer";
	}
	public void exploreNeighborhood(Neighborhood N, LexMultiValues bestEval) {
		// TODO Auto-generated method stub 
		if(firstImprovement && N.hasImprovement()){
			//System.out.println(name() + "::exploreNeighborhood, has improvement --> RETURN");
			return;
		}
		for (Point x : XR.getClientPoints()) {
			for (Point y : XR.getClientPoints()) {
				if (XR.checkPerformTwoPointsMove(x, y)) {
					LexMultiValues eval = F.evaluateTwoPointsMove(x, y);
					if (eval.lt(bestEval)){
						N.clear();
						N.add(new TwoPointsMove(mgr, eval, x, y, this));
						bestEval.set(eval);
					} else if (eval.eq(bestEval)) {
						N.add(new TwoPointsMove(mgr, eval, x, y, this));
					}
					if(firstImprovement){
						if(eval.lt(0)) return;
					}
				}
			}
		}
	}

	
	public void performMove(IVRMove m){
		// DO NOTHING
	}
}
