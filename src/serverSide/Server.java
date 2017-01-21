package serverSide;


import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server {

	public static void main (String argv[]) throws RemoteException{
		Registry registry = LocateRegistry.createRegistry(6013);	
		ImpGraphicsObject imp=new ImpGraphicsObject();
		try {
			String ip=JOptionPane.showInputDialog("Introdusca ip");
			Naming.rebind("rmi://"+ip+":6013/Lista",imp );
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		
	}
}
