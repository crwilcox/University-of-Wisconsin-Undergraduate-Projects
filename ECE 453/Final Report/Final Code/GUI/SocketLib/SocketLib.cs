using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;

namespace PongGUI
{
    public class SocketLib
    {
        Socket socketClient;

        public bool Connect(string IP, string Port)
        {
            try
            {
                //create a new client socket ...
                socketClient = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

                int alPort = System.Convert.ToInt16(Port, 10);

                System.Net.IPAddress remoteIPAddress = System.Net.IPAddress.Parse(IP);
                System.Net.IPEndPoint remoteEndPoint = new System.Net.IPEndPoint(remoteIPAddress, alPort);
                socketClient.Connect(remoteEndPoint);
                
                String szData = "Client Connected";
                byte[] byData = System.Text.Encoding.ASCII.GetBytes(szData);
                socketClient.Send(byData);
                return true;

            }
            catch (SocketException se)
            {
                Console.WriteLine(se.Message);
                return false;
            }
        }


        public bool SendData(string data)
        {
            try
            {
                byte[] byData = System.Text.Encoding.ASCII.GetBytes(data);
                socketClient.Send(byData);
                return true;
            }
            catch (SocketException se)
            {
                Console.WriteLine(se.Message);
                return false;
            }
        }

        public string ReceiveData()
        {
            try
            {
                byte[] buffer = new byte[1024];
                int iRx = socketClient.Receive(buffer);
                char[] chars = new char[iRx];

                System.Text.Decoder d = System.Text.Encoding.UTF8.GetDecoder();
                int charLen = d.GetChars(buffer, 0, iRx, chars, 0);
                System.String szData = new System.String(chars);
                return(szData);
            }
            catch (SocketException se)
            {
                Console.WriteLine(se.Message);
                return "ERROR IN RECEIVE DATA";
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
