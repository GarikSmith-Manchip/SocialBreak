package apk.main.client;

import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable
{

	// The client socket
	private static Socket clientSocket = null;

	// The output stream
	private static PrintStream os = null;

	// The input stream
	//private static DataInputStream is = null;
	
	//z - new input stream because some of DataInputStream's methods are deprecated
	private static BufferedReader m_in = null;

	private static BufferedReader inputLine = null;
	private static boolean closed = false;

	public static void main(String[] args)
	{
		// The default port
		int portNumber = 2222;
		// The default host
		// Can change to IP w/ forwarded ports
		String host = "localhost";

		if (args.length < 2)
		{
			System.out.println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
				+ "Now using host=" + host + ", portNumber=" + portNumber);
		}
		else
		{
			host = args[0];
			portNumber = Integer.valueOf(args[1]).intValue();
		}

		//Open socket
		try
		{
			clientSocket = new Socket(host, portNumber);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			os = new PrintStream(clientSocket.getOutputStream());
			m_in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //z - changed from DataStream to BufferedReader
		}
		catch (UnknownHostException e)
		{
			System.err.println("Don't know about host " + host);
		}
		catch (IOException e)
		{
			System.err.println("Couldn't get I/O for the connection to the host "+ host);
		}

	
		//Open socket
		if (clientSocket != null && os != null && m_in != null)
		{
			try
			{
	
				//thread
				new Thread(new Client()).start();
				while (!closed)
				{
					os.println(inputLine.readLine().trim());
				}
				
				//closing
				os.close();
				m_in.close(); //z
				clientSocket.close();
			}
			catch (IOException e)
			{
				System.err.println("IOException:  " + e);
			}
		}
	}

	//create thread
	public void run()
	{
		//connection
		String responseLine;
		try
		{
		while ((responseLine = m_in.readLine()) != null) //z
		{
			System.out.println(responseLine);
			if (responseLine.indexOf("*** Bye") != -1)
			break;
		}
		closed = true;
		}
		catch (IOException e)
		{
			System.err.println("IOException:  " + e);
		}
	}
}