package library.localsearch.domainspecific.vehiclerouting.vrp.online.invariants;

import library.localsearch.domainspecific.vehiclerouting.vrp.InvariantVR;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;

public interface OInvariantVR extends InvariantVR {
	public void updateWhenReachingTimePoint(int t);
	public void addPoint(Point p);
}
