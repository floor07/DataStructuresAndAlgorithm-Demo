package chapter10.random;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 仿Java实现 Xi+1=(multiplier*Xi+addend)%mask
 * multiplier=25214903917
 * addend=11
 * mask=2^48-1
 * nextInt截取前32位
 * @author wenl
 */
public class MyRandom2 {
	
	private static final Long multiplier=25214903917l;
	private static final long addend = 11l;
	private static final long mask = (1L << 48) - 1;
	private AtomicLong seed;//保证线程安全
	public MyRandom2(){
		this.seed = new AtomicLong((8682522807148012L*181783497276652981L )^System.nanoTime());
	}

    public int nextInt() {
        return next(32);
    }
    private int next(int bits) {
        long oldseed, nextseed;
        AtomicLong seed = this.seed;
        do {
            oldseed = seed.get();
            nextseed = (oldseed * multiplier + addend) % mask;
        } while (!seed.compareAndSet(oldseed, nextseed));
        return (int)(nextseed >>> (48 - bits));
    }
	
    /**
     * 26与27和53的选择，基于实际经验，这样会产生最好的随机数
     * @return
     */
    public double next0_1() {
        return (((long)(next(26)) << 27) + next(27))
            / (double)(1L << 53);
    }
	
public static void main(String[] args) {
	System.out.println(0xBL);
	System.out.println(0x5DEECE66DL);
	System.out.println((1L << 48) - 1);
	//^ 异或
	//>>> 无符号右移动
	System.out.println((16l*0x5DEECE66DL+11)%((1L << 48) - 1));
	System.out.println((16l*0x5DEECE66DL+11)&((1L << 48) - 1));
	MyRandom2 rand=new MyRandom2();
	for(int i=1;i<10;i++){
	System.out.println(rand.nextInt());
	}

}
}
