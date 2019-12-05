using System;
using System.IO;
using System.Net.Sockets;
using System.Collections.Generic;
using System.Threading;
using GameServer.Enumerations;
using Database;
namespace GameServer
{
    public class PlayerSocketController
    {

        public int user;
        public NetworkStream clientInterface;
        private TcpClient _socket;
        PlayerController _player;

        public PlayerSocketController(TcpClient playerSocket, GameController gameController, Database.Database dbService)
        {
            _socket = playerSocket;
            clientInterface = playerSocket.GetStream();
            _player = new PlayerController(this, gameController, dbService);
        }

        /// <summary>
        /// Controls unique player connecitons
        /// Called for thread starts
        /// </summary>
        public void Start()
        {
            //wrap the PlayerController and stream in an IOException
            // in case of failure
            try
            {
                //get the client stream
                //clientInterface = _socket.GetStream();
                _player.ManagePlayer();
            }
            catch (IOException)
            {
                //client invalid disconnect action
            }

            try
            {
                _socket.Close();
            }
            catch (Exception ex)
            {
                //the socket was probably closed already
                Console.WriteLine(ex);
            }
        }
    }
}
