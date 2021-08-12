
/*
 * authors: PHAM Quang Dung (dungkhmt@gmail.com)
 * date: 27/09/2015
 */

package library.localsearch.domainspecific.vehiclerouting.vrp.search.firstimprovement;

import library.localsearch.domainspecific.vehiclerouting.vrp.VRManager;
import library.localsearch.domainspecific.vehiclerouting.vrp.VarRoutesVR;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.LexMultiValues;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;
import library.localsearch.domainspecific.vehiclerouting.vrp.functions.LexMultiFunctions;
import library.localsearch.domainspecific.vehiclerouting.vrp.moves.IVRMove;
import library.localsearch.domainspecific.vehiclerouting.vrp.neighborhoodexploration.INeighborhoodExplorer;
import library.localsearch.domainspecific.vehiclerouting.vrp.search.ISearch;
import library.localsearch.domainspecific.vehiclerouting.vrp.search.Neighborhood;

public class FirstImprovementThreeOptMove4Explorer implements INeighborhoodExplorer {
	private VRManager mgr;
	private VarRoutesVR XR;
	private ISearch search;
	private LexMultiFunctions F;
	private LexMultiValues bestValue;
	
	public FirstImprovementThreeOptMove4Explorer(VarRoutesVR XR, LexMultiFunctions F) {
		this.XR = XR;
		this.F = F;
		this.mgr = XR.getVRManager();
	}
	
	public FirstImprovementThreeOptMove4Explorer(ISearch search, VRManager mgr, LexMultiFunctions F){
		this.search = search;
		this.mgr = mgr;
		this.XR = mgr.getVarRoutesVR();
		this.F = F;
		this.bestValue = search.getIncumbentValue();
	}
	
	public void exploreNeighborhood(Neighborhood N, LexMultiValues bestEval) {
		// TODO Auto-generated method stub
		for (Point x : XR.getClientPoints()) {
			for (Point y = XR.next(x); y != null && XR.isClientPoint(y); y = XR.next(y)) {
				for (Point z = XR.next(y); XR.isClientPoint(z); z = XR.next(z)) {
					if (XR.checkPerformThreeOptMove(x, y, z)) {
						LexMultiValues eval = F.evaluateThreeOptMove4(x, y, z);
						if (eval.lt(bestEval)){
							mgr.performThreeOptMove4(x, y, z);
							System.out.println(name() + "::exploreNeighborhood, F = " + F.getValues().toString());
						}
					}
				}
			}
		}
	}
	public String name(){
		return "FirstImprovementThreeOptMove5Explorer";
	}

	public void performMove(IVRMove m){
		//DO NOTHING
	}

}
