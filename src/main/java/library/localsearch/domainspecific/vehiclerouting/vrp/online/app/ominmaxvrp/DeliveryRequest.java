package library.localsearch.domainspecific.vehiclerouting.vrp.online.app.ominmaxvrp;

import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Point;
import library.localsearch.domainspecific.vehiclerouting.vrp.entities.Request;

public class DeliveryRequest extends Request{
	public Point delivery;
	public int demand;
	
	public DeliveryRequest(int ID, int arrivalTime, Point delivery, int demand){
		super(ID, arrivalTime);
		this.delivery = delivery;
		this.demand = demand;
	}
}
