package library.localsearch.domainspecific.vehiclerouting.vrp;

import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;

public interface IDistanceManager {
	public double getDistance(Point x, Point y);
}
