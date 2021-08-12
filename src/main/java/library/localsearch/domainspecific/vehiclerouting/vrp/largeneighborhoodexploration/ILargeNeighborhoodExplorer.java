package library.localsearch.domainspecific.vehiclerouting.vrp.largeneighborhoodexploration;

import library.localsearch.domainspecific.vehiclerouting.vrp.moves.IVRMove;
import library.localsearch.domainspecific.vehiclerouting.vrp.search.Neighborhood;

public interface ILargeNeighborhoodExplorer {
	public void exploreLargeNeighborhood(Neighborhood N);
	public void performMove(IVRMove m);
	public String name();
}
