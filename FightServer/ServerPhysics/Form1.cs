using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;



namespace ServerPhysics
{
    public partial class Form1 : Form
    {
        ObjectManager obj_man;
        public System.Net.Sockets.NetworkStream player1_stream;
        public System.Net.Sockets.NetworkStream player2_stream;


        public Form1(System.Net.Sockets.NetworkStream player1_stream, Fighter PlayerOneFighter, System.Net.Sockets.NetworkStream player2_stream, Fighter PlayerTwoFighter)
        {
            InitializeComponent();

            obj_man = new ObjectManager(player1_stream, PlayerOneFighter, player2_stream, PlayerTwoFighter);
        }

        public Form1()
        {
            InitializeComponent();

            obj_man = new ObjectManager();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            System.Drawing.Graphics graphicsObj;
            graphicsObj = this.CreateGraphics();
            System.Drawing.SolidBrush myBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Black);


            obj_man.draw(graphicsObj);
           
        }

        private void Form1_Load_1(object sender, EventArgs e)
        {
            timer1.Start();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            

            System.Drawing.Graphics graphicsObj;
            graphicsObj = this.CreateGraphics();
            //draw event
            obj_man.draw(graphicsObj);

            
            //gametick event
            obj_man.game_tick();
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            this.KeyPreview = true;

            
        }


        private void Form1_KeyPress(object sender, KeyPressEventArgs e)
        {
//            obj_man.key_down(e.KeyChar);
        }

        private void Form1_KeyDown(object sender, KeyEventArgs e)
        {
            KeysConverter kc = new KeysConverter();
            string keyChar = kc.ConvertToString(e.KeyData);

            obj_man.key_down(keyChar[0]);
        }

        private void Form1_KeyUp(object sender, KeyEventArgs e)
        {
            KeysConverter kc = new KeysConverter();
            string keyChar = kc.ConvertToString(e.KeyData);

            obj_man.key_up(keyChar[0]);
        }

    }
}
