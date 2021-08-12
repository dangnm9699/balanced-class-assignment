package library.localsearch.domainspecific.vehiclerouting.vrp.moves;

import library.localsearch.domainspecific.vehiclerouting.vrp.VRManager;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.LexMultiValues;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;
import library.localsearch.domainspecific.vehiclerouting.vrp.neighborhoodexploration.INeighborhoodExplorer;

public class ThreeOptMove2 implements IVRMove {

	private VRManager mgr;
	private Point x;
	private Point y;
	private Point z;
	private LexMultiValues eval;
	private INeighborhoodExplorer NE;
	public ThreeOptMove2(VRManager mgr, LexMultiValues eval, Point x, Point y, Point z, INeighborhoodExplorer NE){
		this.mgr = mgr;
		this.eval = eval;
		this.x = x;
		this.y = y;
		this.z = z;
		this.NE = NE;
	}
	public ThreeOptMove2(VRManager mgr, LexMultiValues eval, Point x, Point y, Point z){
		this.mgr = mgr;
		this.eval = eval;
		this.x = x;
		this.y = y;
		this.z = z;
		this.NE = null;
	}
	
	public String name(){
		return "ThreeOptMove2";
	}
	
	
	public void move() {
		//System.out.println(name() + "::move(" + x + "," + y + "," + z + ") " + eval);
		mgr.performThreeOptMove2(x, y, z);
		if(NE != null) NE.performMove(this);
	}

	
	public LexMultiValues evaluation() {
		return eval;
	}
	
	
	public INeighborhoodExplorer getNeighborhoodExplorer(){
		return this.NE;
	}

}
