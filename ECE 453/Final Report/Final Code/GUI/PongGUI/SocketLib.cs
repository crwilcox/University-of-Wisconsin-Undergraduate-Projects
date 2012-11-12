using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;

namespace PongGUI
{
    public class SocketLib
    {
        public Socket socketClient;

        public bool Connect(string IP, string Port)
        {
            try
            {
                //create a new client socket ...
                socketClient = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

                int alPort = System.Convert.ToInt16(Port, 10);


                System.Net.IPAddress remoteIPAddress = Dns.GetHostAddresses(IP)[0];
                //System.Net.IPAddress remoteIPAddress = System.Net.IPAddress.Parse(IP);
                System.Net.IPEndPoint remoteEndPoint = new System.Net.IPEndPoint(remoteIPAddress, alPort);
                socketClient.Connect(remoteEndPoint);
                //String szData = "GUI Connected\n";
                //byte[] byData = System.Text.Encoding.ASCII.GetBytes(szData);
                //socketClient.Send(byData);
                return true;

            }
            catch (SocketException se)
            {
                Console.WriteLine(se.Message);
                return false;
            }
        }

        public int ReceiveData()
        {
            try
            {
                byte[] buffer = new byte[4];
                int iRx = socketClient.Receive(buffer, 4, SocketFlags.None);
				if (iRx == 0)
				{
					return -1;
				}
                // Paul vs. C#
                // Paul "wins"
                return (buffer[3] << 24 | buffer[2] << 16 | buffer[1] << 8 | buffer[0]);
            }
            catch (SocketException se)
            {
                Console.WriteLine(se.Message);
                return -1;
            }
        }

        public bool SendData(int data)
        {
            try
            {
                // Are you fucking kidding me C#? I can't send an int? Fine. I win again.
                byte[] buffer = new byte[4];
                buffer[3] = (byte)(data >> 24);
                buffer[2] = (byte)(data >> 16);
                buffer[1] = (byte)(data >> 8);
                buffer[0] = (byte)data;
                socketClient.Send(buffer);
                return true;
            }
            catch (SocketException se)
            {
                Console.WriteLine(se.Message);
                return false;
            }
        }

        public bool Close()
        {
            try
            {
                socketClient.Close();
                return true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return false;
            }
        }

    }
}
