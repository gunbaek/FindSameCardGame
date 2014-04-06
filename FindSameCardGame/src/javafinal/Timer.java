package javafinal;

/*
 Timer클래스는 게임중 표시되는 시간을 표시할때 사용하는 클래스로
 runnable interface를 구현하여 Mainform 안에서 생성되어 ExecuterService객체를 통해 실행되게 된다 
 */
import javax.swing.*;

public class Timer implements Runnable{
	int time = 0;
	int stop = 0;
	private static JLabel output;
	//변수 선언
	
	public Timer(JLabel label){
		output = label;
	}
	public Timer(JLabel label, int t){
		output= label;
		time = t;
	}
/*
 * 생성자는 2가지 종류로 선언하였다.
 * 인자가 1개인 생성자는 결과값을 표시하기 위하여 JLabel객체 전달받아 레퍼런스를 유지하게 된다.
 * 인자가 2개인 생성자는 위의 역할뿐만 아니라 타이머가 실행될 초기 시간(초)를 전달받아
 * 그 시간부터 count 하게된다
 */
	
	public void run() {
		time ++;
		if(stop == 0){
			try {
				output.setText("Time : " + time);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			run();
		}else{		
			time = 0;
		}
	}
/*
  	run메소드는 Timer를 Thread.sleep(1000)메소드를 통해 1초마다 자신을 다시 부름으로써
  	stop변수가 0이 아닐때까지 계속하여 time변수를 count하여 Timer를 구현한다.
  	mainForm에서 쓰레드의 stop변수를 1로 만듦으로써 쓰레드를 멈추는 방법을 사용하였음.
 */
	int getTime(){
		return time;
	}
/*
 * 현재 카운터한 time변수를 리턴하는 함수
 */
}