using System;
using System.IO;
using System.Net.Sockets;

namespace GameServer
{
    public class PlayerSocketController
    {

        public int user;
        public NetworkStream clientInterface;
        private TcpClient _socket;
        PlayerController _player;
        GameController gameControl;

        public PlayerSocketController(TcpClient playerSocket, GameController gameController, Database.Database dbService)
        {
            _socket = playerSocket;
            gameControl = gameController;
            clientInterface = playerSocket.GetStream();
            _player = new PlayerController(this, gameController);
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
                gameControl.RemovePlayer(_player);
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
