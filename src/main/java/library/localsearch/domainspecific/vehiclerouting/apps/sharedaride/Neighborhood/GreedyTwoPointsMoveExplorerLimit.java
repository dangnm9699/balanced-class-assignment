package library.localsearch.domainspecific.vehiclerouting.apps.sharedaride.Neighborhood;

import library.localsearch.domainspecific.vehiclerouting.apps.sharedaride.Util.RandomUtil;
import library.localsearch.domainspecific.vehiclerouting.vrp.VRManager;
import library.localsearch.domainspecific.vehiclerouting.vrp.VarRoutesVR;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.LexMultiValues;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;
import library.localsearch.domainspecific.vehiclerouting.vrp.functions.LexMultiFunctions;
import library.localsearch.domainspecific.vehiclerouting.vrp.moves.IVRMove;
import library.localsearch.domainspecific.vehiclerouting.vrp.moves.TwoPointsMove;
import library.localsearch.domainspecific.vehiclerouting.vrp.neighborhoodexploration.INeighborhoodExplorer;
import library.localsearch.domainspecific.vehiclerouting.vrp.search.ISearch;
import library.localsearch.domainspecific.vehiclerouting.vrp.search.Neighborhood;

import java.util.ArrayList;

public class GreedyTwoPointsMoveExplorerLimit implements INeighborhoodExplorer {
	private VRManager mgr;
	private VarRoutesVR XR;
	private ISearch search;
	private LexMultiFunctions F;
	private LexMultiValues bestValue;
	int K;
	public GreedyTwoPointsMoveExplorerLimit(VarRoutesVR XR, LexMultiFunctions F, int K) {
		this.XR = XR;
		this.F = F;
		this.mgr = XR.getVRManager();
		this.K = K;
	}
	public String name(){
		return "GreedyTwoPointsMoveExplorerLimit";
	}
	
	@Override
	public void exploreNeighborhood(Neighborhood N, LexMultiValues bestEval) {
		int nRoute = XR.getNbRoutes();
		ArrayList<Integer>listI = RandomUtil.randomKFromN(K, nRoute);
		ArrayList<Integer>listJ = RandomUtil.randomKFromN(K, nRoute);
		for (int i : listI)
		for (int j : listJ) {
			for (Point x = XR.startPoint(i); x != XR.endPoint(i); x = XR.next(x)) {
				for (Point y = XR.startPoint(j); y != XR.endPoint(j); y = XR.next(y)) {
					if (XR.checkPerformTwoPointsMove(x, y)) {
						LexMultiValues eval = F.evaluateTwoPointsMove(x, y);
						if (eval.lt(bestEval)){
							N.clear();
							N.add(new TwoPointsMove(mgr, eval, x, y, this));
							bestEval.set(eval);
						} else if (eval.eq(bestEval)) {
							N.add(new TwoPointsMove(mgr, eval, x, y, this));
						}
					}
				}
			}
		}
	}

	@Override
	public void performMove(IVRMove m){
		// DO NOTHING
	}
}

