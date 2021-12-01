import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * Handles communication between the server and one client, for SketchServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}


	public void run() {
		try {
			System.out.println("someone connected");
			
			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);




			// Tell the client the current state of the world
			for (int id : server.getSketch().getShapes().navigableKeySet()) {
				out.println("add " + id + " " + server.getSketch().getShapes().get(id));
			}


			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				StringParser stringParser = new StringParser(line);
				stringParser.parser(server.getSketch());

				{
					server.broadcast(line);
				}
			}
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
