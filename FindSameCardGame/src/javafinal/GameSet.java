package javafinal;
/*
게임에 이용되는 레벨마다의 카드 상태를 저장하고있는 클래스
현재 코드에는 0레밸 ~ 3래벨의 게임이 저장되어있음
(총4개의 래벨) 
*/
public class GameSet{
	Shape[] shapeset;
	GameSet(){
		shapeset = new Shape[4];
		shapeset[0] = new Shape(4, new int[] {
				0,0,1,0,
				0,1,0,1,
				1,0,1,0,
				0,1,0,1,
		});
		shapeset[1] = new Shape(5, new int[] {
				2,0,1,0,2,
				0,1,0,1,0,
				1,0,2,0,1,
				0,1,0,1,0,
				2,0,1,0,2,
		});
		shapeset[2] = new Shape(7, new int[]{
				1,0,2,0,3,0,4,
				0,2,0,3,0,4,0,
				2,0,3,0,4,0,1,
				0,3,0,4,0,1,0,
				3,0,4,0,1,0,2,
				4,0,1,0,2,0,3,
				0,4,0,1,0,2,0,
		});
		shapeset[3] = new Shape(10, new int[]{
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
	}
}