
/*
 * authors: Pham Quang Dung (dungkhmt@gmail.com), Nguyen Thanh Hoang (thnbk56@gmail.com)
 * date: 21/10/2015
 */

package library.localsearch.domainspecific.vehiclerouting.vrp.invariants;

import library.localsearch.domainspecific.vehiclerouting.vrp.*;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*
 * the cost of the path from the depot to a given point v
 */

public class AccumulatedWeightEdgesVR implements InvariantVR {

	protected HashMap<Point, Integer> map; 
	
	protected double[] costRight; 
    protected double[] costLeft; 
    
    protected VarRoutesVR XR;
	protected VRManager mgr;
	//protected ArcWeightsManager awm;
	protected IDistanceManager awm;
	//public AccumulatedWeightEdgesVR(VarRoutesVR XR, ArcWeightsManager awm){
	public AccumulatedWeightEdgesVR(VarRoutesVR XR, IDistanceManager awm){
		this.awm = awm;
		this.XR = XR;
		this.mgr = XR.getVRManager();
		post();
	}
	
	public VarRoutesVR getVarRoutesVR(){
		return this.XR;
	}
	
	protected int getIndex(Point p) {
		//System.out.println(name() + "::getIndex(" + p.ID + ")");
		return map.get(p);
	}
	
	public double getCostRight(Point p){
		return costRight[getIndex(p)];
	}
	
	public double getCostLeft(Point p){
		return costLeft[getIndex(p)];
	}
	
	public double getCost(Point x, Point y){
		return awm.getDistance(x, y);
	}
	
	
	public VRManager getVRManager() {
		// TODO Auto-generated method stub
		return mgr;
	}
	
	private void post() {
		map = new HashMap<Point, Integer>();
		ArrayList<Point> points = XR.getAllPoints();
		for (int i = 0; i < points.size(); i++) {
			map.put(points.get(i), i);
		}
		costLeft = new double[points.size()];
		costRight = new double[points.size()];
		for(int i = 0; i < costRight.length; i++)
			costRight[i] = 0;
		for(int i = 0; i < costLeft.length; i++)
			costLeft[i] = 0;
		mgr.post(this);
	}
	
	
	public String name(){
		return "AccumulatedWeightEdgesVR";
	}
	
	protected void update(int k) {
        Point sp = XR.getStartingPointOfRoute(k);
        Point tp = XR.getTerminatingPointOfRoute(k); 
        //costRight[getIndex(sp)] = 0;
        for (Point u = sp; u != tp; u = XR.next(u)) {
            costRight[getIndex(XR.next(u))] = costRight[getIndex(u)] + awm.getDistance(u, XR.next(u)); 
        }
        //costLeft[getIndex(tp)] = 0;
        for (Point u = tp; u != sp; u = XR.prev(u)) {
            costLeft[getIndex(XR.prev(u))] = costLeft[getIndex(u)] + awm.getDistance(u, XR.prev(u));
        }
    }
	
	public void setAccumulatedWeightStartPoint(int k, double w){
		costRight[getIndex(XR.startPoint(k))] = w;
	}
	public void initPropagation() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= XR.getNbRoutes(); i++) {
            update(i);
        }
	}

	// x is before y on the same route
	// remove (x, next[x]) and (y,next[y])
	// add (x,y) and (next[x],next[y])
	public void propagateTwoOptMoveOneRoute(Point x, Point y) {
		System.out.println(name() + "::propagateTwoOptMoveOneRoute NOT IMPLEMENTED YET");
		System.exit(-1);
	}

	public void propagateOnePointMove(Point x, Point y) {
		// TODO Auto-generated method stub
		HashSet<Integer> oldR = new HashSet<Integer>();
		oldR.add(XR.oldRoute(x)); 
    	oldR.add(XR.oldRoute(y));
    	for (int r : oldR) {
    		update(r);
    	}
	
	}

	
	public void propagateTwoPointsMove(Point x, Point y) {
		// TODO Auto-generated method stub
		if (XR.next(x) == y) {
    		propagateTwoPointsMove(y, x, XR.prev(x), XR.prev(x));
    	} else if (XR.next(y) == x) {
    		propagateTwoPointsMove(x, y, XR.prev(y), XR.prev(y));
    	} else {
    		propagateTwoPointsMove(x, y, XR.prev(y), XR.prev(x));
    	}
	}

	
	public void propagateTwoOptMove1(Point x, Point y) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		update(XR.oldRoute(y));
	}

	
	public void propagateTwoOptMove2(Point x, Point y) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		update(XR.oldRoute(y));
	}

	
	public void propagateTwoOptMove3(Point x, Point y) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		update(XR.oldRoute(y));
	}

	
	public void propagateTwoOptMove4(Point x, Point y) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		update(XR.oldRoute(y));
	}

	
	public void propagateTwoOptMove5(Point x, Point y) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		update(XR.oldRoute(y));
	}

	
	public void propagateTwoOptMove6(Point x, Point y) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		update(XR.oldRoute(y));
	}

	
	public void propagateTwoOptMove7(Point x, Point y) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		update(XR.oldRoute(y));
	}

	
	public void propagateTwoOptMove8(Point x, Point y) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		update(XR.oldRoute(y));
	}

	
	public void propagateOrOptMove1(Point x1, Point x2, Point y) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x1));
		update(XR.oldRoute(y));
	}

	
	public void propagateOrOptMove2(Point x1, Point x2, Point y) {
		// TODO Auto-generated method stub
		//System.out.println(name() + " " + v + "\n" + toString() + getValue());
		update(XR.oldRoute(x1));
		update(XR.oldRoute(y));
		//System.out.println(getValue());
	}

	
	public void propagateThreeOptMove1(Point x, Point y, Point z) {
		// TODO Auto-generated method stub
		update(XR.route(x));
	}

	
	public void propagateThreeOptMove2(Point x, Point y, Point z) {
		// TODO Auto-generated method stub
		update(XR.route(x));
	}

	
	public void propagateThreeOptMove3(Point x, Point y, Point z) {
		// TODO Auto-generated method stub
		update(XR.route(x));
	}

	
	public void propagateThreeOptMove4(Point x, Point y, Point z) {
		// TODO Auto-generated method stub
		update(XR.route(x));
	}

	
	public void propagateThreeOptMove5(Point x, Point y, Point z) {
		// TODO Auto-generated method stub
		update(XR.route(x));
	}

	
	public void propagateThreeOptMove6(Point x, Point y, Point z) {
		// TODO Auto-generated method stub
		update(XR.route(x));
	}

	
	public void propagateThreeOptMove7(Point x, Point y, Point z) {
		// TODO Auto-generated method stub
		update(XR.route(x));
	}

	
	public void propagateThreeOptMove8(Point x, Point y, Point z) {
		// TODO Auto-generated method stub
		update(XR.route(x));
	}

	
	public void propagateCrossExchangeMove(Point x1, Point y1, Point x2, Point y2) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x1));
		update(XR.oldRoute(x2));
	}
	
	
	public void propagateTwoPointsMove(Point x1, Point x2, Point y1, Point y2) {
		// TODO Auto-generated method stub
		//System.out.println(name() + "::propagateTwoPointsMove(" + x1 + "," + x2 + "," + y1 + "," + y2 + ")");
		HashSet<Integer> oldR = new HashSet<Integer>();
		oldR.add(XR.oldRoute(x1)); 
    	oldR.add(XR.oldRoute(y1));
    	oldR.add(XR.oldRoute(x2)); 
    	oldR.add(XR.oldRoute(y2));
    	for (int r : oldR) {
    		update(r);
    	}
	}
	
	public void propagateThreePointsMove(Point x1, Point x2, Point x3, Point y1,
			Point y2, Point y3) {
		// TODO Auto-generated method stub
		HashSet<Integer> oldR = new HashSet<Integer>();
		oldR.add(XR.oldRoute(x1)); 
    	oldR.add(XR.oldRoute(y1));
    	oldR.add(XR.oldRoute(x2)); 
    	oldR.add(XR.oldRoute(y2));
    	oldR.add(XR.oldRoute(x3)); 
    	oldR.add(XR.oldRoute(y3));
    	for (int r : oldR) {
    		update(r);
    	}
	}
	
	public void propagateFourPointsMove(Point x1, Point x2, Point x3, Point x4, Point y1,
			Point y2, Point y3, Point y4) {
		// TODO Auto-generated method stub
		HashSet<Integer> oldR = new HashSet<Integer>();
		oldR.add(XR.oldRoute(x1)); 
    	oldR.add(XR.oldRoute(y1));
    	oldR.add(XR.oldRoute(x2)); 
    	oldR.add(XR.oldRoute(y2));
    	oldR.add(XR.oldRoute(x3)); 
    	oldR.add(XR.oldRoute(y3));
    	oldR.add(XR.oldRoute(x4)); 
    	oldR.add(XR.oldRoute(y4));
    	for (int r : oldR) {
    		update(r);
    	}
	}
	
	public void propagateAddOnePoint(Point x, Point y) {
		// TODO Auto-generated method stub
		//System.out.println(name() + "::propagateAddOnePoint(" + x + "," + y + ")"); System.exit(-1);
		update(XR.route(y));
	}

	
	public void propagateRemoveOnePoint(Point x) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		costRight[getIndex(x)] = costLeft[getIndex(x)] = 0;
	}
	
	public void propagateAddTwoPoints(Point x1, Point y1, Point x2, Point y2) {
		// TODO Auto-generated method stub
		update(XR.route(y1));
	}

	
	public void propagateRemoveTwoPoints(Point x1, Point x2) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x1));
		costRight[getIndex(x1)] = costLeft[getIndex(x1)] = costRight[getIndex(x2)] = costLeft[getIndex(x2)] = 0;
	}
	
	public void propagateAddRemovePoints(Point x, Point y, Point z) {
		// TODO Auto-generated method stub
		update(XR.oldRoute(x));
		costRight[getIndex(x)] = costLeft[getIndex(x)] = 0;
		if (XR.oldRoute(x) != XR.oldRoute(z)) {
			update(XR.oldRoute(z));
		}
	}
	
	
	public void propagateKPointsMove(ArrayList<Point> x, ArrayList<Point> y) {
		// TODO Auto-generated method stub
		HashSet<Integer> oldR = new HashSet<Integer>();
		for (int i = 0; i < x.size(); i++) {
			Point p = x.get(i);
			Point q = y.get(i);
			if (q != CBLSVR.NULL_POINT) {
				oldR.add(XR.oldRoute(p));
				oldR.add(XR.oldRoute(q));
			} else {
				oldR.add(XR.oldRoute(p));
				costRight[getIndex(p)] = costLeft[getIndex(p)] = 0;
			}
		}
		for (int r : oldR) {
			if (r != Constants.NULL_POINT) {
				update(r);
			}
    	}
	}
	
	public String toString() {
    	String s = "";
    	for (int k = 1; k <= XR.getNbRoutes(); k++) {
    		s += "route[" + k + "] : ";
    		for (Point x = XR.getStartingPointOfRoute(k); x != XR.getTerminatingPointOfRoute(k); x = XR.next(x)) {
    			s += x.getID() + " " + getCostRight(x) + " " + getCostLeft(x) + " | " ;
    		}
    		s += "\n";
    	}
    	return s;
    }

	
	
}
