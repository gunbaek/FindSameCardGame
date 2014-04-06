package javafinal;

import javax.swing.*;
/*
 * Search 클래스는 게임내 규칙인 동일한 카드가 연속으로 3개 이상 나열되어있을 경우 해당 카드들을 
 * 삭제하는 규칙을 구현하기 위한 클래스로써 게임내 모든 카드를 조사하여 카드가 연결된 상태를 조사하고 삭제한다.
 */
public class Search{
	int level = 0;
	Shape sh;
	int grid;
	int max ;
	//변수 선언
	
	public Search(int lev, Shape shape){
		level = lev;
		sh = shape;
		grid = sh.Grid;
		max = grid *grid;
	}
/*
 	생성자.
 	생성자에서는 인자로 전달받은 래밸을 저장하고  Shape를 레퍼런스한다.
 	그밖에 다른 변수를 초기화한다.
 */
	public void run() {
		int upcount = 0;
		int downcount =0;
		int leftcount = 0;
		int rightcount = 0;
		//변수 초기화
		
		for(int i = 0; i<max; i++){
			//검사
			upcount = upSearch(i);
			downcount = downSearch(i);
			leftcount = leftSearch(i);
			rightcount = rightSearch(i);
			upcount--;
			downcount--;
			leftcount--;
			rightcount--;


//			JOptionPane.showMessageDialog(null, "i:"+ i + 
//                    "\nup:" + upcount +
//                    "\ndown:" + downcount +
//                    "\nleft:" + leftcount +
//                    "\nright:" + rightcount);
//			
			//세로 삭제
			if((upcount + downcount)>=2){                	                                                
				for(int j = 0 ; j <= upcount ; j++){
					sh.shape[i-j*grid] = -1;		//해당 셀 위의 동일 카드 삭제

				}
				for(int j = 0 ; j<= downcount ; j++){
					sh.shape[i+j*grid] = -1;	//해당 셀 아래의 동일 카드 삭제
				}
			}
			
			//가로 삭제
			if((leftcount + rightcount)>=2){
				for(int j = 0 ; j <= leftcount ; j++){
					sh.shape[i-j] = -1;		//해당 셀 왼족의 동일 카드 삭제
				}
				for(int j = 0 ; j<= rightcount ; j++){
					sh.shape[i+j] = -1;	//해당 셀 오른쪽의 동일 카드 삭제
				}
			}
		}
	}
	/*
 	run메소드는 자신과 같은 카드를 각 방향의 4가지 함수를 통해 조사한다.
 	각 방향의 Search메소드는 재귀함수로써 만약 본체와 같은 카드가 Shape 배열의 바운스 안에서
 	연속에서 존재하는경우 그 갯수조사해서 리턴한다.
 	 
 	 이 리턴되는 갯수를 통해 같은 카드가 가로 혹은 세로로 3개이상 연결되어 있을경우 그 갯수만큼 카드를 삭제할 수 있다.
 	 
 */
	
	int upSearch(int i){
		int result = 0;
		int up;		
		up = i - grid;

		if(up>=0){
			if(sh.shape[i] == sh.shape[up]){
				result += upSearch(up);
			}
		}
		//JOptionPane.showMessageDialog(null,i);
		return result+1;
	}
	
	int downSearch(int i){
		int result = 0;
		int down;
		
		down = i + grid;
		if(down<max){
			if(sh.shape[i] == sh.shape[down]){
				result += downSearch(down);
			}
		}

	//	JOptionPane.showMessageDialog(null,i);
		return result+1;
	}
	int leftSearch(int i){
		int result = 0;
		int left;
		
		left = i - 1;
		int mod = i%grid;
		if(mod != 0){
			if(sh.shape[i] == sh.shape[left]){
				result += leftSearch(left);
			}
		}
		//JOptionPane.showMessageDialog(null,i);
		return result+1;
	}
	int rightSearch(int i){
		int result = 0;
		int right;
		
		right = i + 1;
		int mod = right%grid;
		if(mod != 0){
			if(sh.shape[i] == sh.shape[right]){
				result += rightSearch(right);
			}
		}
		//JOptionPane.showMessageDialog(null,i);
		return result+1;
	}
}














