package chapter10.dynamicprogramming;

/**
 * 按照网上思路实现的
 * 背包问题，动态规划
 * 假定背包的最大容量为W，N件物品，每件物品都有自己的价值val和重量wt，将物品放入背包中使得背包内物品的总价值最大（val的和最大）。
 * @author wenl
 *
 */
public class Knapsack {
	
    public static void main(String[] args) throws Exception {
        int val[] = {5, 10, 20, 50};
        int wt[] = {6, 4, 6, 3};
        int W = 10;
 
        System.out.println(knapsack(val, wt, W));
    }
 
    /**
     * @param val 权重数组
     * @param wt  重量数组
     * @param W   总权重
     * @return    背包中使得背包内物品的总价值最大时的重量
     */
    public static int knapsack(int val[], int wt[], int W) {
        //物品数量总和
        int N = wt.length; 
 
        //创建一个二维数组
        //行最多存储N个物品，列最多为总权重W，下边N+1和W+1是保证从1开始
        int[][] V = new int[N + 1][W + 1]; 
 
        
        //将行为 0或者列为0的值，都设置为0
        for (int col = 0; col <= W; col++) {
            V[0][col] = 0;
        }
        for (int row = 0; row <= N; row++) {
            V[row][0] = 0;
        }
        //从1开始遍历N个物品
        for (int item=1;item<=N;item++){
            //一行一行的填充数据
            for (int weight=1;weight<=W;weight++){
              
                if (wt[item-1]<=weight){
                	//选取（当前项值+之前项去掉当前项权重的值）与不取当前项的值得最大者
                    V[item][weight]=Math.max (val[item-1]+V[item-1][weight-wt[item-1]], V[item-1][weight]);
                }else {//不选取当前项，以之前项代替
                    V[item][weight]=V[item-1][weight];
                }
            }
 
        }
 
        //打印最终矩阵
        for (int[] rows : V) {
            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }
        //返回结果
        return V[N][W];
    }
}