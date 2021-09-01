
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

public class FirstImprovementOnePointMoveExplorer implements INeighborhoodExplorer {
	private VRManager mgr;
	private VarRoutesVR XR;
	private ISearch search;
	private LexMultiFunctions F;
	private LexMultiValues bestValue;
	
	public FirstImprovementOnePointMoveExplorer(VarRoutesVR XR, LexMultiFunctions F) {
		this.XR = XR;
		this.F = F;
		this.mgr = XR.getVRManager();
	}
	
	public FirstImprovementOnePointMoveExplorer(ISearch search, VRManager mgr, LexMultiFunctions F){
		this.search = search;
		this.mgr = mgr;
		this.XR = mgr.getVarRoutesVR();
		this.F = F;
		this.bestValue = search.getIncumbentValue();
	}
	
	public void exploreNeighborhood(Neighborhood N, LexMultiValues bestEval) {
		// TODO Auto-generated method stub
		for (Point x : XR.getClientPoints()) {
			if(N.size() > 0) break;
			for (Point y : XR.getAllPoints()) {
				if(N.size() > 0) break;
				//System.out.println(name() + "::exploreNeighborhood, consider (" + x.ID + "," + y.ID + " of route " + XR.route(y) + ", index " + XR.index(y) + ")");
				if (XR.checkPerformOnePointMove(x, y)) {
					//System.out.println(name() + "::exploreNeighborhood, accept (" + x.ID + "," + y.ID + " of route " + XR.route(y) + ", index " + XR.index(y) + ")");
					LexMultiValues eval = F.evaluateOnePointMove(x, y);
					//System.out.println(name() + "::exploreNeighborhood, accept (" + x.ID + "," + y.ID + " of route " + XR.route(y) + ", index " + XR.index(y) + ") eval = " + eval.toString());
					if(eval.lt(bestEval)){
						N.add(new OnePointMove(mgr, eval, x, y, this));
					}
				}
			}
		}
		//System.out.println(name() + "::exploreNeighborhood finished");
	}
	public String name(){
		return "FirstImprovementOnePointMoveExplorer";
	}
	public void performMove(IVRMove m){
		// DO NOTHING
	}
}
