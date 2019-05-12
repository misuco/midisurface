package org.misucatomisuco.control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.illposed.osc.OSCPacket;

public class MisuOSCPortOut {
	protected InetAddress address;
	protected Socket socket;
	protected int port;

	/**
	 * The port that the SuperCollider <b>synth</b> engine ususally listens to &mdash; 57110.
	 */
	public static int defaultSCOSCPort() {
		return 57110;
	}
	
	/**
	 * The port that the SuperCollider <b>language</b> engine ususally listens to &mdash; 57120.
	 */
	public static int defaultSCLangOSCPort() {
		return 57120;
	}
	
	/**
	 * Create an OSCPort that sends to newAddress, newPort
	 * @param newAddress InetAddress
	 * @param newPort int
	 */
	public MisuOSCPortOut(InetAddress newAddress, int newPort)
		throws SocketException {
		socket = new Socket();
		address = newAddress;
		port = newPort;
	}

	/**
	 * Create an OSCPort that sends to newAddress, on the standard SuperCollider port
	 * @param newAddress InetAddress
	 *
	 * Default the port to the standard one for SuperCollider
	 */
	public MisuOSCPortOut(InetAddress newAddress) throws SocketException {
		this(newAddress, defaultSCOSCPort());
	}

	/**
	 * Create an OSCPort that sends to localhost, on the standard SuperCollider port
	 * Default the address to localhost
	 * Default the port to the standard one for SuperCollider
	 */
	public MisuOSCPortOut() throws UnknownHostException, SocketException {
		this(InetAddress.getLocalHost(), defaultSCOSCPort());
	}
	
	/**
	 * Send an osc packet (message or bundle) to the receiver I am bound to.
	 * @param aPacket OSCPacket
	 */
	public void send(OSCPacket aPacket) throws IOException {
//		byte[] byteArray = aPacket.getByteArray();
//		DatagramPacket packet =
//			new DatagramPacket(byteArray, byteArray.length, address, port);
//		socket.(packet);
	}

}
