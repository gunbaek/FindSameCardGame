package javafinal;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.concurrent.*;

import javax.swing.*;

import com.sun.corba.se.spi.orbutil.fsm.*;

@SuppressWarnings("serial")
public class MainForm extends JFrame{
	static boolean game_start = false;
	static int time = 0;
	static JLabel Level_label;
	static JLabel border_label1,border_label2,border_label3;
	static JLabel Time_label;
	static JLabel State_label;
	static JLabel Lock_label;
	JButton Start_button, Reset_button, wait_button;
	@SuppressWarnings("rawtypes")
	JComboBox level_selector;
	GameSet GameInformation = new GameSet();
	static JPanel gamemap_panel = new JPanel();
	static JButton[] game_card;
	static int grid;
	static int level;
	static Shape[] shapestate= new Shape[4];
	static boolean lock = true;
	static boolean wait = false;
	static boolean start = false;
	static boolean init = true;
	static int index;
	static ExecutorService runner = Executors.newFixedThreadPool(100);
	Timer timer = new Timer(Time_label);
	//변수선언
	
	
	public MainForm(){
		super("MainForm");
		init();		
	}
	//메인폼에서 초기화 실행
	
	@SuppressWarnings("unchecked")
	private void init(){
		Container container = getContentPane();
		JPanel main_panel = new JPanel();
		main_panel.setLayout(new BorderLayout());
		//메인패널 생성 및 초기화
		
		JPanel label_panel = new JPanel(new FlowLayout());
		JPanel button_panel = new JPanel(new GridLayout(1,4));
		JPanel game_panel = new JPanel();
		// 서브패널 생성
		
		label_panel.setBackground(Color.yellow);
		button_panel.setBackground(Color.gray);
		game_panel.setBackground(Color.darkGray);
		//서브패널의 초기화
		
		main_panel.add(label_panel, BorderLayout.NORTH);
		main_panel.add(button_panel, BorderLayout.SOUTH);
		main_panel.add(game_panel, BorderLayout.CENTER);
		//메인패널에 서브패널 부착
		
		border_label1 = new JLabel("     ");
		border_label2 = new JLabel("     ");
		border_label3 = new JLabel("     ");
		Level_label = new JLabel("Level : No selected");
		Time_label = new JLabel("Time : 0");
		State_label = new JLabel("State : [None]");
		Lock_label = new JLabel("Lock : [Lock]");
		//라벨의 초기화
		
		Level_label.setFont(new Font("",0,20));
		Time_label.setFont(new Font("",0,20));
		State_label.setFont(new Font("",0,20));
		Lock_label.setFont(new Font("",0,20));
		//라벨의 폰트 지정
		
		Start_button = new JButton("Start");
		Start_button.setFont(new Font("",0,30));
		Reset_button = new JButton("Reset");
		Reset_button.setFont(new Font("",0,30));
		wait_button = new JButton("wait");
		wait_button.setFont(new Font("",0,30));
		//버튼 초기화 & 폰트 지정
		
		Timer timer = new Timer(Time_label);
		//타이머 초기화
		
		int[] list = new int[GameInformation.shapeset.length];
		for(int i = 0; i<GameInformation.shapeset.length; i++){list[i]=i;}
		level_selector = new JComboBox<Integer>();
		for(int i = 0; i<GameInformation.shapeset.length; i++)
			level_selector.addItem("Game Level : " + i);
		//게임 래벨 콤보박스 초기화
		
		ActionListener levelListner = new levelListener(timer);
		ActionListener btnListener = new btnListener(timer); 
		 //버튼 리스너 생성
		
		level_selector.addActionListener(levelListner);
		Start_button.addActionListener(btnListener);
		wait_button.addActionListener(btnListener);
		Reset_button.addActionListener(btnListener);
		//버튼 리스너 등록
		
		label_panel.add(Level_label);
		label_panel.add(border_label1);
		label_panel.add(Time_label);
		label_panel.add(border_label2);
		label_panel.add(State_label);
		label_panel.add(border_label3);
		label_panel.add(Lock_label);		
		//라벨 패널에 래벨과 시간을 표시하는 label을 부착
		
		button_panel.add(level_selector);
		button_panel.add(Start_button);
		button_panel.add(Reset_button);
		button_panel.add(wait_button);
		//버튼 패널에 level, start, reset wait버튼 부착
		
		// gamemap_panel.setSize(400,400);
		gamemap_panel.setBackground(Color.BLACK);
		game_panel.setLayout(null);
		gamemap_panel.setBounds(50, 40, 550, 500);
		game_panel.add(gamemap_panel);
		//게임 패널의 초기화 진행
		
		container.add(main_panel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(680, 700);
		this.setVisible(true);
	
		
	}
	
	private class levelListener implements ActionListener{

		public levelListener(Timer t){
			timer = t;
		}
		public void actionPerformed(ActionEvent e) {
			String str = (String)(level_selector.getSelectedItem());
			index = Integer.parseInt(str.substring(13));
			
			timer.stop = 1; //타이머를 멈춤
			level = index; //선택된 레벨 저장
			
			Level_label.setText("Level : "+index);
			time=0;
			Time_label.setText("Time : "+time);
			//레벨 라벨과 타이머 라벨을 초기화
			
			start = false;
			//게임시작 설정해제
			
			Lock_label.setText("Lock : [Lock]");
			State_label.setText("State : [Level Selected]");
			//라벨 설정
			init = false;
			setGameButton(index);
			//레벨에 맞는 게임의 카드패널 초기화
		}
	}

	private class btnListener implements ActionListener{ //내부 버튼 리스너

		public btnListener(Timer t){
			timer = t;
		}
		public void actionPerformed(ActionEvent e) {
			if(init){
				JOptionPane.showMessageDialog(null,"먼저 게임 래밸을 선택 하세요.");
			}else{
				if(e.getSource() == Start_button){ // start 버튼을 눌렀을 경우
	
					setGameButton(level);					//게임카드 초기화
					time = 0;									//타이머 초기화
					timer.stop = 1;								//이전의 타이머 중단
					timer = new Timer(Time_label);	//새로운 타이머 생성
					runner.execute(timer);					//새로운 타이머 실행
					lock=false;								//lock상태 해제
					wait = false;								//wait상태 초기화
					start = true;								//게임시작 설정
					wait_button.setText("wait");			//wait버튼 초기화
					Lock_label.setText("Lock : [unLock]");		
					State_label.setText("State : [Started]");	
					//라벨 초기화	
					lock(lock);
					JOptionPane.showMessageDialog(null,"게임을 시작합니다.");
					
				}
				if(e.getSource() == wait_button){ // wait 버튼을 눌렀을 경우
						if(start){
							if(!wait){//언락상태 -> 락 상태
								wait_button.setText("resume");
								timer.stop = 1; 						//타이머를 멈추는 변수
								time = timer.getTime(); 			//현재 타이머의 시간을 저장
								wait = true;							//wait를 활성화		
								lock = true;							//게임을 중지한다 lock상태 활성화
								Lock_label.setText("Lock : [Lock]");
								State_label.setText("State : [Wait]");
								lock(lock);								//lock상태를 나타냄
								
								JOptionPane.showMessageDialog(null,"대기 상태가 되었습니다.");
							}else{										// 락 상태 - > 언락 상태
								wait_button.setText("wait");		
								timer = new Timer(Time_label, time);
								wait = false;							//wait를 비활성화
								runner.execute(timer);	
								lock=false	;							//lock상태 비활성화
								lock(lock);								//다시 카드를 표시해줌
								Lock_label.setText("Lock : [unLock]");
								State_label.setText("State : [Resumed]");
							}		
						}else{
							JOptionPane.showMessageDialog(null,"먼저 게임을 시작해야합니다.");
						}
				}
				if(e.getSource() == Reset_button){// reset 버튼을 눌렀을 경우
						timer.stop = 1;										//타이머를 멈춤
						setGameButton(index);						//게임카드 초기화
						time = 0;											//타이머 시간 초기화
						Time_label.setText("Time : "+time);		//시간라벨 초기화
						wait = false;										//wait상태 초기화
						start = false;										//게임시작 설정 해제
						wait_button.setText("wait");					//wait버튼 초기화
						Lock_label.setText("Lock : [Lock]");
						State_label.setText("State : [Reset]");
						JOptionPane.showMessageDialog(null,"게임이 초기화 되었습니다.");
						//라벨 설정
						
				}
			}		
		}
	}
	
	private static class cardListener implements ActionListener{
		public static int priv = -500;
		public void actionPerformed(ActionEvent e) {
			if(!lock){
				for(int j=0; j<game_card.length; j++){
					if(e.getSource() == game_card[j]){
						if(priv == j-1 || priv == j+1 || priv == j-grid || priv == j+grid){
							int temp;
		
							Shape sh = shapestate[level];
							int max = sh.Grid * sh.Grid;
							game_card = new JButton[max];
							grid = sh.Grid;
							gamemap_panel.removeAll();
							gamemap_panel.repaint();
							gamemap_panel.setLayout(new GridLayout(sh.Grid, sh.Grid));
							
							int w = gamemap_panel.getWidth() / sh.Grid;
		
							temp = sh.shape[j];
							sh.shape[j] = sh.shape[priv];
							sh.shape[priv] = temp;
		
							check();
							
							for(int i = 0; i<max; i++){
								game_card[i] = new JButton();
								gamemap_panel.add(game_card[i]);
											
								BufferedImage bi = new BufferedImage(w, w,
										BufferedImage.TYPE_INT_ARGB);
								if(sh.shape[i] < 0){
									game_card[i].setIcon(new ImageIcon(bi));
								}else{
									ImageIcon icon = new ImageIcon(getClass().getResource(sh.shape[i]  + ".jpg"));
									
									Image img = icon.getImage();
									game_card[i].setIcon(new ImageIcon(img));
								};
								ActionListener cardListener = new cardListener();
								game_card[i].addActionListener(cardListener);
							}
							
							//gamemap_panel.repaint();
							gamemap_panel.updateUI();	
							
							priv = -500;
						}else{
							game_card[j].setEnabled(false);
							if(priv > 0){
								game_card[priv].setEnabled(true);
							}
							priv = j;
						}
						break;
					}
				}
			}else{
				JOptionPane.showMessageDialog(null,"게임이 잠겨있습니다.");
			}
		}
	}
	static void check(){

		Shape sh = shapestate[level];
		Search search = new Search(level, sh);
		search.run();
		
	}
	
	void setGameButton(int level){
		Shape sh = GameInformation.shapeset[level];
		int max = sh.Grid * sh.Grid;
		game_card = new JButton[max];
		grid = sh.Grid;
		gamemap_panel.removeAll();
		gamemap_panel.repaint();
		gamemap_panel.setLayout(new GridLayout(sh.Grid, sh.Grid));
		cardListener.priv = -500;
		stateInit();
		lock = true;
		int w = gamemap_panel.getWidth() / sh.Grid;
		
		for(int i = 0; i<max; i++){
			game_card[i] = new JButton();
			gamemap_panel.add(game_card[i]);
			/*
			 * Icon icon = new ImageIcon(sh.shape[i]-1=".jpg");
			 * game_card[i].setIcon(icon);
			 */
			BufferedImage bi = new BufferedImage(w, w,
					BufferedImage.TYPE_INT_ARGB);
			if(sh.shape[i] < 0){
				game_card[i].setIcon(new ImageIcon(bi));
			}else{
				ImageIcon icon = new ImageIcon(getClass().getResource(sh.shape[i]  + ".jpg"));
				
				Image img = icon.getImage();
				game_card[i].setIcon(new ImageIcon(img));
			};			
			ActionListener cardListener = new cardListener();
			game_card[i].addActionListener(cardListener);
			game_card[i].setEnabled(!lock);
		}
		gamemap_panel.updateUI();	
	}
	void stateInit(){
		shapestate[0] = new Shape(4, new int[] {
				0,0,1,0,
				0,1,0,1,
				1,0,1,0,
				0,1,0,1,
		});
		shapestate[1] = new Shape(5, new int[] {
				2,0,1,0,2,
				0,1,0,1,0,
				1,0,2,0,1,
				0,1,0,1,0,
				2,0,1,0,2,
		});
		shapestate[2] = new Shape(7, new int[]{
				1,0,2,0,3,0,4,
				0,2,0,3,0,4,0,
				2,0,3,0,4,0,1,
				0,3,0,4,0,1,0,
				3,0,4,0,1,0,2,
				4,0,1,0,2,0,3,
				0,4,0,1,0,2,0,
		});
		shapestate[3] = new Shape(10, new int[]{
				0,1,2,3,4,0,1,2,3,4,
				4,3,0,1,0,4,3,0,1,0,
				0,1,2,3,4,0,1,2,3,4,
				4,3,0,1,0,4,3,0,1,0,
				0,1,2,3,4,0,1,2,3,4,
				4,3,0,1,0,4,3,4,1,0,
				0,1,2,3,4,0,1,2,3,4,
				4,3,0,1,0,4,3,3,1,0,
				0,1,2,3,4,0,1,2,3,4,
				4,3,0,1,0,4,3,1,1,0,				
		});
	}//게임도중에 변경되는 게임상태를 저장합니다.
	
	void lock(boolean Lock){
		lock = Lock;
		Shape sh = shapestate[level];
		int max = sh.Grid * sh.Grid;
		game_card = new JButton[max];
		grid = sh.Grid;
		gamemap_panel.removeAll();
		gamemap_panel.repaint();
		gamemap_panel.setLayout(new GridLayout(sh.Grid, sh.Grid));
		int w = gamemap_panel.getWidth() / sh.Grid;

		if(lock){//락이 걸려있을때 모든 카드를 X모양 카드로 전환
			for(int i = 0; i<max; i++){
				game_card[i] = new JButton();
				gamemap_panel.add(game_card[i]);

				ImageIcon icon = new ImageIcon(getClass().getResource("5.jpg"));
				Image img = icon.getImage();
				game_card[i].setIcon(new ImageIcon(img));
			
				ActionListener cardListener = new cardListener();
				game_card[i].addActionListener(cardListener);
				game_card[i].setEnabled(false);
			}
			gamemap_panel.updateUI();	
			
		}else{//락이 안걸려있을때 다시 원 카드로 복구함

			for(int i = 0; i<max; i++){
				game_card[i] = new JButton();
				gamemap_panel.add(game_card[i]);
			
			
				BufferedImage bi = new BufferedImage(w, w,
						BufferedImage.TYPE_INT_ARGB);
				if(sh.shape[i] < 0){
					game_card[i].setIcon(new ImageIcon(bi));
				}else{
					ImageIcon icon = new ImageIcon(getClass().getResource(sh.shape[i]  + ".jpg"));
					
					Image img = icon.getImage();
					game_card[i].setIcon(new ImageIcon(img));
				};
				ActionListener cardListener = new cardListener();
				game_card[i].addActionListener(cardListener);
				game_card[i].setEnabled(true);
			}
			gamemap_panel.updateUI();	
		}
	}
}




























