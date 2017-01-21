package serverSide;

import application.PointCanvas;

import java.awt.geom.Point2D;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GraphicsObject extends Remote {

	public void put(byte[] b)throws RemoteException;
	
	public byte[] pop()throws RemoteException;

    public void setPoint(PointCanvas p2) throws RemoteException;
    public PointCanvas putPoint() throws RemoteException;

	
}
