package serverSide;

import application.PointCanvas;

import java.awt.geom.Point2D;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImpGraphicsObject extends UnicastRemoteObject implements GraphicsObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    byte[] by=null;

    PointCanvas p2=null;
	protected ImpGraphicsObject() throws RemoteException {

	}

    @Override
    public void put(byte[] b) throws RemoteException {
        by=b;
    }

    @Override
    public byte[] pop() throws RemoteException {
        return by;
    }


    @Override
    public void setPoint(PointCanvas p2) throws RemoteException {
        this.p2=p2;
    }

    @Override
    public PointCanvas putPoint() throws RemoteException {
        return p2;
    }
}
