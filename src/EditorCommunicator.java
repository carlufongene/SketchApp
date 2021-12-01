import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Handles communication to/from the server for the editor
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}

	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 **/

	public void run() {
		try {

			// continually reads the input from the editor and parses it
			// to determine the next move and update paintings

			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				new StringParser(line).parser(editor.getSketch());
				editor.repaint();
			}

		}


		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			editor = null;

		}

		try {
			in.close();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void recolor(int i, int color) {
		send("recolor" + " " + i + " " + color);
	}
	public void move(int i, int dx, int dy) {
		send("move" + " " + i + " " + dx + " " + dy);
	}
	public void delete(int i) {
		send("delete" + " " +  i);
	}

	public void add(Shape shape) {
		// add the first shape (-1)
		send("add " + "-1 " + shape);
	}

	
}
