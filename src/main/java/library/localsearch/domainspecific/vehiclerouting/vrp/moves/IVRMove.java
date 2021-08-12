
package library.localsearch.domainspecific.vehiclerouting.vrp.moves;

import library.localsearch.domainspecific.vehiclerouting.vrp.entities.LexMultiValues;
import library.localsearch.domainspecific.vehiclerouting.vrp.neighborhoodexploration.INeighborhoodExplorer;

/*
 * authors: Pham Quang Dung (dungkhmt@gmail.com)
 * date: 30/08/2015
 */
public interface IVRMove {
	public void move();
	public LexMultiValues evaluation();
	public INeighborhoodExplorer getNeighborhoodExplorer();
	public String name();
}
