package library.localsearch.domainspecific.vehiclerouting.vrp.online.functions;

import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;
import library.localsearch.domainspecific.vehiclerouting.vrp.functions.AccumulatedEdgeWeightsOnPathVR;
import library.localsearch.domainspecific.vehiclerouting.vrp.online.invariants.OAccumulatedWeightEdges;

public class OAccumulatedEdgeWeightsOnPath extends AccumulatedEdgeWeightsOnPathVR implements OFunctionVR {

	public OAccumulatedEdgeWeightsOnPath(OAccumulatedWeightEdges accWE, Point v){
		super(accWE,v);
		accWE.getVRManager().post(this);
	}
	
	public void updateWhenReachingTimePoint(int t){
		// DO NOTHING, this was done by accWE
	}
	public void addPoint(Point p){
		// DO NOTHING, this was done by accWE
	}
	
}
