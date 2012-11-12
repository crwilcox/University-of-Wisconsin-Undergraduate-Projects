using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Threading;
using System.Windows.Threading;
using System.Text.RegularExpressions;

namespace PongGUI {
	/// <summary>
	/// Interaction logic for MainWindow.xaml
	/// </summary>
	
	public struct demo_mode {
		public int opcode;
		public int arg_count;
		public string name;
		public string tooltip;
	}

	public partial class MainWindow : Window {

		public delegate void colorDeleg(Ellipse act, int color);
		public delegate void scoreDeleg(int data);
		public delegate void controlDeleg(bool connected);
		public delegate void endGameDeleg(int data);
		public delegate void startGameDeleg(string score_text, bool visible);
		public delegate void messageDeleg(string message_text);

		public const int DemoModeOp = 0x03;
		public const int GameModeOp = 0x02;
		public const int SensorOp = 0x00;
		public const int CupOp = 0x01;
		public const int CloseOp = 0x07;
		public const int FullStateOp = 0x04;

		public const int StartGameSubOp = 0x1F;
		public const int Team1OnColorSubOp = 0x03;
		public const int Team1OffColorSubOp = 0x02;
		public const int Team2OnColorSubOp = 0x01;
		public const int Team2OffColorSubOp = 0x00;
		public const int EndGameSubOp = 0x04;

		public const int DemoCycleRainbow = 0x01;
		public const int DemoCycleRainbowBlend = 0x02;
		public const int DemoRainbowWave = 0x03;
		public const int DemoMotionDetectRed = 0x04;
		public const int DemoBreathing = 0x05;
		public const int DemoCycleRainbowSmooth = 0x06;
		public const int DemoSnake = 0x07;
		public const int DemoCountingColors = 0x08;

		public demo_mode[] DemoModes; // Initialized below in a ball of mess.

		public const bool direct = false;
		public const bool dispatch = true;

		SocketLib socket = new SocketLib();  //Socket for communication
		Thread thread;
		Thread waitThread;

		int thread_set_up = 0;

		public MainWindow() {
			InitializeComponent();

			//http://www.codeproject.com/KB/WPF/WPFDropDownColorPicker.aspx
			//This colorpicker was used.  

			//MessageBox.Show("foo");

			/* Initialize DemoModes Array. This is somewhat painful, but
			 * greatly reduces string duplications.
			 */
			DemoModes = new demo_mode[8];
			DemoModes[0].name = "Cycle Rainbow";
			DemoModes[0].opcode = DemoCycleRainbow;
			DemoModes[0].tooltip = "Step all cups through a rainbow of colors.";
			DemoModes[0].arg_count = 0;

			DemoModes[1].name = "Cycle Rainbow Blend";
			DemoModes[1].opcode = DemoCycleRainbowBlend;
			DemoModes[1].tooltip = "Same as cycle rainbow, but with more colors in the pallete.";
			DemoModes[1].arg_count = 0;

			DemoModes[2].name = "Rainbow Wave";
			DemoModes[2].opcode = DemoRainbowWave;
			DemoModes[2].tooltip = "Transitions the colors of the rainbow across the table rows.";
			DemoModes[2].arg_count = 0;

			DemoModes[3].name = "Smooth Rainbow Wave";
			DemoModes[3].opcode = DemoCycleRainbowSmooth;
			DemoModes[3].tooltip = "Same as Rainbow Wave, but the colors transition smoothly.";
			DemoModes[3].arg_count = 0;

			DemoModes[4].name = "Motion Detect Red";
			DemoModes[4].opcode = DemoMotionDetectRed;
			DemoModes[4].tooltip = "Cups turn Red when they are uncovered, Blue when they are covered.";
			DemoModes[4].arg_count = 0;

			DemoModes[5].name = "Breathing";
			DemoModes[5].opcode = DemoBreathing;
			DemoModes[5].tooltip = "Colors fade in and out as the table \"breathes\".";
			DemoModes[5].arg_count = 1;

			DemoModes[6].name = "Snake";
			DemoModes[6].opcode = DemoSnake;
			DemoModes[6].tooltip = "Colored snakes move across the table.";
			DemoModes[6].arg_count = 0;

			DemoModes[7].name = "Counting Colors";
			DemoModes[7].opcode = DemoCountingColors;
			DemoModes[7].tooltip = "Cycles through all possible colors, incrementing the displayed color by 1 each time.";
			DemoModes[7].arg_count = 2;

			

			thread = new Thread(new ThreadStart(() => read_data(this)));
			thread_set_up = 1;

			ComboBoxItem tmp_item;
			for (int i = 0; i < 8; i++) {
				tmp_item = new ComboBoxItem();
				tmp_item.Content = DemoModes[i].name;
				tmp_item.ToolTip = DemoModes[i].tooltip;
				demoModeSelect.Items.Add(tmp_item);
			}
			demoModeSelect.SelectedIndex = 3; // Index of Smooth Rainbow Wave;


			//Events to allow the preview canvases for color selection
			t1_on_cp.SelectedColorChanged += new Action<Color>(t1_on_CP_SelectedColorChanged);
			t1_off_cp.SelectedColorChanged += new Action<Color>(t1_off_CP_SelectedColorChanged);
			t2_on_cp.SelectedColorChanged += new Action<Color>(t2_on_CP_SelectedColorChanged);
			t2_off_cp.SelectedColorChanged += new Action<Color>(t2_off_CP_SelectedColorChanged);
		}
		~MainWindow() {
			thread.Abort();
		}

		private static void read_data(MainWindow instance) {

			bool keep_running = true;

			while (keep_running) {
				//MessageBox.Show("Attempting to get data");
				int data = instance.socket.ReceiveData();
				if (data == -1 || instance.socket.socketClient.Connected == false) {
					keep_running = false;
					continue;
				}
				//string print_data = data.ToString("X");
				//MessageBox.Show("Received: 0x"+print_data);
				// MessageBox.Show("Got data");
				try {
					keep_running = process_command(data, instance);
				}
				catch (ThreadAbortException) {
				}
				catch (Exception e) {
					MessageBox.Show("Error!\n\n" + e.ToString());
					throw new Exception();
				}

			}
			thread_exit(instance);
		}

		private static void thread_exit(MainWindow instance) {
			// Called when the thread exits.
			instance.socket.Close();

			controlDeleg control_handler = instance.update_controls;
			instance.Dispatcher.Invoke(control_handler, DispatcherPriority.Normal, false);
			instance.thread_set_up = 0;
			instance.thread.Abort(); //Stop the listener



		}

		private static bool process_command(int command, MainWindow instance) {
			colorDeleg color_handler = instance.set_color;
			scoreDeleg score_handler = instance.SetScores;
			endGameDeleg end_handler = instance.EndGame;
			startGameDeleg start_handler = instance.SetScoreMessage;
			messageDeleg message_handler = instance.SetMessage;
			byte op1 = (byte)(command >> 29);
			byte op2 = (byte)((command & 0x1F000000) >> 24);
			int data = command & 0x00FFFFFF;

			Ellipse tempCup = null;

			switch (op1) {
				case SensorOp: //SENSORS
					instance.Dispatcher.Invoke(score_handler, DispatcherPriority.Normal, data);
					break;
				case CupOp: //CUPS
					tempCup = instance.getCup(op2);
					if (tempCup == null) {
						return true; // This just ignores this command rather than exiting.
					}
					instance.Dispatcher.BeginInvoke(color_handler, DispatcherPriority.Normal, tempCup, data);
					//instance.set_color(tempCup, data);
					break;
				case DemoModeOp:
					for (int i = 0; i < instance.DemoModes.Length; i++) {
						if (instance.DemoModes[i].opcode == op2) {
							instance.Dispatcher.Invoke(message_handler, DispatcherPriority.Normal, instance.DemoModes[i].name);
						}
					}
					break;
				case GameModeOp:
					if (op2 == EndGameSubOp) {
						instance.Dispatcher.BeginInvoke(end_handler, DispatcherPriority.Normal, data);
					}
					if (op2 == StartGameSubOp) {
						instance.Dispatcher.Invoke(message_handler, DispatcherPriority.Normal, "Game Mode");
						instance.Dispatcher.Invoke(start_handler, DispatcherPriority.Normal, null, true);
						instance.inGame = true;
					}
					break;

				case CloseOp:
					return false;

				default:
					MessageBox.Show("Unknown Message Received:\n" + op1 + "|" + op2 + "|" + data);
					break;
			}
			return true;
		}

		

		private void set_color(Ellipse act, int color) {
			act.Fill = new SolidColorBrush(GetColor(color));
		}

		private Ellipse getCup(byte cup_num) {
			Ellipse change_me = null;
			switch (cup_num) {
				case 0: change_me = Cup0; break;
				case 1: change_me = Cup1; break;
				case 2: change_me = Cup2; break;
				case 3: change_me = Cup3; break;
				case 4: change_me = Cup4; break;
				case 5: change_me = Cup5; break;
				case 6: change_me = Cup6; break;
				case 7: change_me = Cup7; break;
				case 8: change_me = Cup8; break;
				case 9: change_me = Cup9; break;
				case 10: change_me = Cup10; break;
				case 11: change_me = Cup11; break;
				case 12: change_me = Cup12; break;
				case 13: change_me = Cup13; break;
				case 14: change_me = Cup14; break;
				case 15: change_me = Cup15; break;
				case 16: change_me = Cup16; break;
				case 17: change_me = Cup17; break;
				case 18: change_me = Cup18; break;
				case 19: change_me = Cup19; break;
			}

			return change_me;
		}

		#region gameMode
		bool inGame = false;
		private void SetScores(int data) {
			//data will be 20 bits long and will contain all 20 sensors
			int t1Score = 0;
			int t2Score = 0;

			for (int i = 0; i < 20; i++) {
				int currSensor = (data >> i) & 0x1;
				if (currSensor == 1 && i < 10) t1Score++;
				else if (currSensor == 1 && i >= 10) t2Score++;
			}
			if (inGame) SetScoreMessage(t1Score + "   -   " + t2Score);
		}

		private void EndGame(int teamWon) {
			inGame = false;
			socket.SendData(DemoModeOp << 29 | DemoSnake << 24 | 0x000000);
			SetScoreMessage("Team " + teamWon + " has won!");
			SetMessage("Victory Snake");
			// Leave this message up for 10 sec -- we'll do this on a thread.
			waitThread = new Thread(new ThreadStart(() => wait_then_hide_score(this)));
			waitThread.Start();
		}

		private static void wait_then_hide_score(MainWindow instance) {
			Thread.Sleep(10000);
			instance.Dispatcher.Invoke(DispatcherPriority.Normal, new Action(
				delegate()
				{
					instance.Score.Visibility = Visibility.Hidden;
				})
			);
		}

		private void SetScoreMessage(string score) {
			Score.Text = score;
		}

		private void SetScoreMessage(string score, bool visible) {
			Score.Visibility = visible ? Visibility.Visible : Visibility.Hidden;
			if(score != null)
				Score.Text = score;
		}
		#endregion


		private void SetMessage(string msg) {
			Message.Text = msg;
		}
		#region Button Click Methods

		#region Main Tab
		private void connect_Click(object sender, RoutedEventArgs e) {
			if (thread_set_up == 0) {
				thread = new Thread(new ThreadStart(() => read_data(this)));
				socket = new SocketLib();
			}

			if (socket.Connect(server.Text, port.Text)) {
				thread.Start(); //Start the listener


				server.IsEnabled = false;
				port.IsEnabled = false;
				disconnect.Visibility = Visibility.Visible;
				connect.Visibility = Visibility.Hidden;
				update_controls(true);
                PleaseConnect.Visibility = System.Windows.Visibility.Hidden;

				socket.SendData(FullStateOp << 29 | 0x0 << 24 | 0x0);
			}
			else {
				MessageBox.Show("Failed to Connect to Specified Server and Port.  Please verify your settings and try again...");
			}
		}

		private void disconnect_Click(object sender, RoutedEventArgs e) {
			socket.Close();
            thread.Abort(); //Stop the listener
			
            thread_set_up = 1;

			thread = new Thread(new ThreadStart(() => read_data(this)));
			socket = new SocketLib();

            //Put everything to the starting state
            update_controls(false);
		}

		private void StartGame_Click(object sender, RoutedEventArgs e) {
			//3bits 5bits 24bits

			if(waitThread != null)
				waitThread.Abort();
			
			Color t1_on_color = t1_on_cp.HexValue == "" ? t1_on_cp.SelectedColor : GetColorFromHex(t1_on_cp.HexValue);
			Color t1_off_color = t1_off_cp.HexValue == "" ? t1_off_cp.SelectedColor : GetColorFromHex(t1_off_cp.HexValue);
			Color t2_on_color = t2_on_cp.HexValue == "" ? t2_on_cp.SelectedColor : GetColorFromHex(t2_on_cp.HexValue);
			Color t2_off_color = t2_off_cp.HexValue == "" ? t2_off_cp.SelectedColor : GetColorFromHex(t2_off_cp.HexValue);
			inGame = true;

			//write T1 on     0x 2_03_xxxxxx
			int data = (t1_on_color.R << 16 | t1_on_color.G << 8 | t1_on_color.B) & 0xFFFFFF;
			socket.SendData(GameModeOp << 29 | Team1OnColorSubOp << 24 | data);

			//write T1 off    0x 2_02_xxxxxx
			data = (t1_off_color.R << 16 | t1_off_color.G << 8 | t1_off_color.B) & 0xFFFFFF;
			socket.SendData(GameModeOp << 29 | Team1OffColorSubOp << 24 | data);

			//write T2 on     0x 2_01_xxxxxx
			data = (t2_on_color.R << 16 | t2_on_color.G << 8 | t2_on_color.B) & 0xFFFFFF;
			socket.SendData(GameModeOp << 29 | Team2OnColorSubOp << 24 | data);

			//write T2 off    0x 2_00_xxxxxx
			data = (t2_off_color.R << 16 | t2_off_color.G << 8 | t2_off_color.B) & 0xFFFFFF;
			socket.SendData(GameModeOp << 29 | Team2OffColorSubOp << 24 | data);

			//write startgame   0x 2_1F_xxxxxx
			socket.SendData(GameModeOp << 29 | StartGameSubOp << 24 | 0x0);

			Score.Visibility = Visibility.Visible;
			SetMessage("Game Mode");
			SetScores(0x000FFFFF);

		}

		private void StartDemo_Click(object sender, RoutedEventArgs e) {
			int arg = 0;
			int index = demoModeSelect.SelectedIndex;
			if (DemoModes[index].arg_count == 1) {
				// Breathing mode, we'll have to be smarter here if anything else takes a single argument.
				arg = Int32.Parse(breathing_delay.Text) & 0x00FFFFFF;
				//This is validated on change, so we don't need to try / catch
			}
			else if (DemoModes[index].arg_count == 2) {
				// Counting Colors mode, we'll need to be smarter if another mode takes 2 args.
				arg = (Int32.Parse(counting_delay.Text) & 0x7FFFFF) << 1 | (
					counting_single.IsChecked.HasValue ?
					(bool)counting_single.IsChecked ? 1 : 0 :
					0);
			}
			SetMessage(DemoModes[index].name);
			socket.SendData(DemoModeOp << 29 | DemoModes[index].opcode << 24 | arg);
		}

		#endregion //Main Tab

		#region Settings Tab
		void t1_on_CP_SelectedColorChanged(Color obj) {
			t1_on_preview.Background = new SolidColorBrush((Color)obj);
		}
		void t1_off_CP_SelectedColorChanged(Color obj) {
			t1_off_preview.Background = new SolidColorBrush((Color)obj);
		}
		void t2_on_CP_SelectedColorChanged(Color obj) {
			t2_on_preview.Background = new SolidColorBrush((Color)obj);
		}
		void t2_off_CP_SelectedColorChanged(Color obj) {
			t2_off_preview.Background = new SolidColorBrush((Color)obj);
		}

		int good_breathing_delay = 250;
		private void breathing_delay_LostFocus(object sender, RoutedEventArgs e) {
			try {
				good_breathing_delay = Int32.Parse(breathing_delay.Text);
			}
			catch (Exception) {
				breathing_delay.Text = "" + good_breathing_delay;
				tab_control.SelectedIndex = 1;
				MessageBox.Show("Invalid Delay Entered. Reverting to last value.");
			}
		}

		int good_counting_delay = 100;
		private void counting_delay_LostFocus(object sender, RoutedEventArgs e) {
			try {
				good_counting_delay = Int32.Parse(counting_delay.Text);
			}
			catch (Exception) {
				counting_delay.Text = "" + good_counting_delay;
				tab_control.SelectedIndex = 1;
				MessageBox.Show("Invalid Delay Entered. Reverting to last value.");
			}
		}
		#endregion //Settings Tab
		#endregion //Button Click Methods

		private void Window_Closed(object sender, EventArgs e) {
			socket.Close();
			thread.Abort();
		}

		//enables/disables clicking of other buttons of gui now that we are connected/disconnected
		private void update_controls(bool connected) {
			server.IsEnabled = !connected;
			port.IsEnabled = !connected;
			disconnect.Visibility = connected ? Visibility.Visible : Visibility.Hidden;
			connect.Visibility = connected ? Visibility.Hidden : Visibility.Visible;

			demoModeSelect.IsEnabled = connected;
			StartDemo.IsEnabled = connected;
			StartGame.IsEnabled = connected;

            if(!connected) {

                // we are going to wait 1/2 second to make sure that our cups
                // get set last also stops the user from spamming
                // connect/disconnect
                Thread.Sleep(500);

                // Put everything to the starting state
                PleaseConnect.Visibility = Visibility.Visible;
                Score.Visibility = Visibility.Hidden;
                Message.Text = "Welcome To The Electronic Beirut Table (EBT)!";

                // Now set all cups to their default colors
                set_color(Cup0, 0x00FF00);
                set_color(Cup1, 0x00FFFF);
                set_color(Cup2, 0x00FFFF);
                set_color(Cup3, 0x0000FF);
                set_color(Cup4, 0x0000FF);
                set_color(Cup5, 0x0000FF);
                set_color(Cup6, 0x6600FF);
                set_color(Cup7, 0x6600FF);
                set_color(Cup8, 0x6600FF);
                set_color(Cup9, 0x6600FF);

                set_color(Cup10, 0x66FF00);
                set_color(Cup11, 0xFFFF00);
                set_color(Cup12, 0xFFFF00);
                set_color(Cup13, 0xFF6600);
                set_color(Cup14, 0xFF6600);
                set_color(Cup15, 0xFF6600);
                set_color(Cup16, 0xFF0000);
                set_color(Cup17, 0xFF0000);
                set_color(Cup18, 0xFF0000);
                set_color(Cup19, 0xFF0000);
            }
		}

		private static Color GetColor(int hex) {
			Byte R = (Byte)(hex >> 16);
			Byte G = (Byte)(hex >> 8);
			Byte B = (Byte)hex;
			return Color.FromRgb(R, G, B);

		}

		private Color GetColorFromHex(string hexColor) {
			return Color.FromRgb(
					Convert.ToByte(hexColor.Substring(3, 2), 16),
					Convert.ToByte(hexColor.Substring(5, 2), 16),
					Convert.ToByte(hexColor.Substring(7, 2), 16)
					);
		}
	}
}
