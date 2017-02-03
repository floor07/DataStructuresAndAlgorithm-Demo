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
     */
    private static long witness( long a, long i, long n )
    {
        if( i == 0 )
            return 1;

        long x = witness( a, i / 2, n );
        if( x == 0 )    // 
            return 0;

        // 
        long y = ( x * x ) % n;
        if( y == 1 && x != 1 && x != n - 1 )
            return 0;

        if( i % 2 != 0 )
            y = ( a * y ) % n;

        return y;
    }

    /**
     * 
     */
    public static final int TRIALS = 5;

    /**
     */
    public static boolean isPrime( long n ){
        Random r = new Random( );
        for( int counter = 0; counter < TRIALS; counter++ )
            if( witness( r.nextInt( (int) n - 3 ) + 2, n - 1, n ) != 1 )
                return false;

        return true;
    }

}
