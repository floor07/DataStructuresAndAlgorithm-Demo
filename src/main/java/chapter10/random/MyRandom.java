package chapter10.random;


public class MyRandom {
	private static final int A=48271;
	private static final int M=2147483647;
	private static final int Q=M/A;
	private static final int R=M%A;
	private int state;
	public MyRandom(){
		state=(int) (System.currentTimeMillis()%Integer.MAX_VALUE);
	}
	
	/**
	 * //防止移除的随机数 Xi+1=A(xi mod Q)-R[xi/Q]+Ms(xi)
	 * @return
	 */
	public int randomInt(){
		int tmpState=A*(state%Q)-R*(state/Q);
		if(tmpState>=0){
			state=tmpState;
		}else{
			state=tmpState+M;
		}
		return state;
	}
	public double random0_1(){
		return (double)randomInt()/M;
	}
public static void main(String[] args) {
	System.out.println(0xBL);
	System.out.println(0x5DEECE66DL);
	System.out.println((1L << 48) - 1);
	//^ 异或
	//>>> 无符号右移动
	System.out.println((16l*0x5DEECE66DL+11)%((1L << 48) - 1));
	System.out.println((16l*0x5DEECE66DL+11)&((1L << 48) - 1));
	MyRandom rand=new MyRandom();
	System.out.println(rand.randomInt());
}
}
