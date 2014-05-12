package apk.main.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Server
{
	// The server socket.
	private static ServerSocket serverSocket = null;
	// The client socket.
	private static Socket clientSocket = null;
	
	// This chat server can accept up to maxClientsCount clients' connections.
	private static final int maxClientsCount = 3; //z lowered for testing
	private static final clientThread[] threads = new clientThread[maxClientsCount];
	
	public static void main(String args[])
	{
		System.out.println("Starting server..."); //z
		
		// The default port number.
		int portNumber = 2222;
		if (args.length < 1)
		{
			System.out.println("Usage: java MultiThreadChatServerSync <portNumber>\n"
				+ "Now using port number: " + portNumber);
		}
		else
		{
			portNumber = Integer.valueOf(args[0]).intValue();
		}
	
		//open server on default socket (2222)
		try 
		{
			//z
			System.out.println("Opening connection...");
			serverSocket = new ServerSocket(portNumber);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	
		//create thread for client
		while (true)
		{
			try
			{
				
				clientSocket = serverSocket.accept();
				//z
				System.out.print("Client connecting... ");
				System.out.println(clientSocket.toString());
				
				int i = 0;
				for (i = 0; i < maxClientsCount; i++)
				{
					if (threads[i] == null)
					{
						(threads[i] = new clientThread(clientSocket, threads)).start();
						break;
					}
				}
				
				if (i == maxClientsCount)
				{
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					os.println("Server is full (" + maxClientsCount + " maximum clients)."); //z
					os.close();
					clientSocket.close();
				}
			}
			catch (IOException e)
			{
				System.out.println(e);
			}
		}
	}
}

//create thread for client to recieve messages through
class clientThread extends Thread
{	
	private String clientName = null;
	private BufferedReader m_in = null; // z - mine
	private PrintStream os = null;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount;
	
	//private ServerProtocol protocol = null; //z
	private Actor m_ent = null;
	private static Parse m_p = new Parse();
	
	private boolean m_connected = true;
	
	public clientThread(Socket clientSocket, clientThread[] threads)
	{
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}
	
	public void run()
	{
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;

		try
		{
			//start streams
			m_in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //z
				
			os = new PrintStream(clientSocket.getOutputStream());
			String name;
				
			while (true) 
			{
				os.println("Enter your name.");
				name = m_in.readLine().trim(); //z
	
				if (name.indexOf("@") == -1 && name.indexOf(" ") == -1)  //z - added space check too
				{
					break;
				}
				else 
				{
					os.println("Don't use '@' symbols or spaces in your username.");
				}
			}
			
			//hullo
			os.println(name + " has connected."
					+ "\n@quit to disconnect.");

			synchronized (this)
			{
				for (int i = 0; i < maxClientsCount; i++)
				{
					if (threads[i] != null && threads[i] == this)
					{
						clientName = "person_" + name;
					}
				}
				for (int i = 0; i < maxClientsCount; i++)
				{
					if (threads[i] != null && threads[i] != this)
					{
						threads[i].os.println("*** " + name + " has connected! ***");
					}
				}
			}
			
			//connect
			while (m_connected)
			{
				String line = m_in.readLine();
				
				//collapse input first
				String[] msgRecieved  = m_p.parse(m_ent, line);
				String msgFormatted = "";
				
				for (int i = 0; i < msgRecieved.length; i++)
				{
					msgFormatted += msgRecieved[i];
					if (i < msgRecieved.length - 1)
					{
						msgFormatted += "\n";
					}
				}
				
				if(msgRecieved.length > 0 && msgRecieved[0] != null)
				{
					//System.out.println("recieved is GREATER THAN 0 THINGS");
					//System.out.println(msgRecieved[0]);
					
					synchronized (this)
					{
						for (int i = 0; i < threads.length; i++) //max clients -> threads
						{
							/** MESSAGE_TYPE_ROOM */
							if (threads[i] != null 
									&& threads[i].clientName != null)
							{
								threads[i].os.println(msgFormatted);
							}
							
							if (line.equalsIgnoreCase("@quit"))
							{
								m_connected = false;
							}
							if (line.length() > 0)
							{
								//System.out.println(line);
								//System.out.println(line.equalsIgnoreCase("@quit"));
							}
						}
					}
				}
			}//end of while
					
			//System.out.println(msgRecieved[0]);
				
			synchronized (this)
			{
				for (int i = 0; i < threads.length; i++)
				{
					if (threads[i] != null && threads[i] != this && threads[i].clientName != null)
					{
						threads[i].os.println("*** " + name + " disconnected. ***");
						System.out.println("*** Disconected " + name + " ***");
						os.println("*** Disconected " + name + " ***");
					}
				}
			}
			
		
			//closing thread
			synchronized (this)
			{
				for (int i = 0; i < maxClientsCount; i++)
				{
					if (threads[i] == this)
					{
						threads[i] = null;
					}
				}
			}
			
			//is.close();
			m_in.close();
			os.close();
			clientSocket.close();
		}
		catch (IOException e)
		{
			//z something should be here probs
		}
		/*catch (Exception e) //TODO drop client if they get an exception
		{
			for (int i = 0; i < threads.length; i++)
			{
				if (threads[i] != null && threads[i] == this)
				{
					threads[i].os.println("Something went wrong!");
					System.out.println(e.getMessage());
				}
			}
		}*/
	}
}
