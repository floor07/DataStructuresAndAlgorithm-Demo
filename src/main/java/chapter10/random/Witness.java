package chapter10.random;

import java.util.Random;

/**
 * 一种概率，测试一个数是否是素数
 * 依据
 * 1.费马小定理：如果P是素数，且0<A<P,那么A^(P-1)≡(1 mod P)<br/>
 * 2. 如果P是素数且 0<A<P,那么X^2≡(1 mod P),仅有两个解X=1，P-1<br/>
 * @author Administrator
 */
public class Witness {
    /**
     * A^(P-1)≡(1 mod P)
     * 此处P-1 对应变量n
     */
	private static long witness(long a,long n,long p){
		if(n==0){
			return 1;
		}
		long x=witness(a,n/2,p);
			
		if(x==0){
			return 0;
		}
		//校验定理2
		long y=(x*x)%p;
		if(y==1&&x!=1&&x!=p-1){
			return 0;
		}
		//校验定理2结束
		if(n%2!=0){//奇数,修正A^p-1的解
			y=(a*y)%p;
		}
		return y;
	}


    /**
     * 尝试五次
     */
    public static final int TRIALS = 5;

    /**
     * 素性测试
     */
    public static boolean isPrime( long n ){
        Random r = new Random( );
        for( int counter = 0; counter < TRIALS; counter++ )
            if( witness( r.nextInt( (int) n - 3 ) + 2, n - 1, n ) != 1 )
                return false;

        return true;
    }
    public static void main(String[] args) {
		for(int i=100;i<200;i++){
			if(isPrime(i)){
				//101 103 107 109 113 
				//127 131 137 139 149 151 157 163 167 173 179 181 191 193 197 
				//199
				System.out.println(i);
			}
		}
	}
}
