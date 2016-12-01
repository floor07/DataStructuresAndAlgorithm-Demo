package chapter8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends JFrame {

	private static final long serialVersionUID = -8163003438738051480L;
	//画面的大小
	private static int wWidth=700;
	private static int hHeight=700;
	//迷宫尺寸
	int m=10,n=10;
	//迷宫房间大小
	int rSize=10;
	//迷宫窗口据左上的距离
	int d=10;
	private JPanel panel=new JPanel();
	
	public Maze(){
		this.panel.setLayout(null);
		//add
		this.add(panel,BorderLayout.CENTER);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, wWidth, hHeight);
		g.setColor(Color.black);
		//画房间
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				g.drawRect(rSize*(i+d),rSize*(j+d) , rSize, rSize);
			}
		}
		//迷宫的出入口打开
		g.setColor(Color.white);
		//-1是为了不留下小缺口 横墙
		g.drawLine(rSize*d, rSize*d, rSize*(d+1)-1, rSize*d);
		//竖墙
		g.drawLine(rSize*d, rSize*d, rSize*d, rSize*(d+1)-1);
		//出口 横墙
		g.drawLine((n-1+d)*rSize+1, (m+d)*rSize,(n+d)*rSize,  (m+d)*rSize);
		//出口 竖墙
		g.drawLine((n+d)*rSize, (m-1+d)*rSize+1,(n+d)*rSize,  (m+d)*rSize);
		//不相交集定义
		DisjSets dSets=new DisjSets(m*n);
		Random r=new Random();
		//出口0入口m*n-1没有连通
		while(dSets.find(0)!=dSets.find(m*n-1)){
			//随机生成房间号
			int roomNum=r.nextInt(m*n);
			//存入所有的相邻房间
			List<Integer>neighbor=new ArrayList<Integer>();
			//判断上 下 左 右 房间是否存在 存在放入neighbor
			if(roomNum-n>=0){//上边不小于0
				neighbor.add(roomNum-n);
			}
			if(roomNum+n<m*n){//下边 不大于m*n 因为出口是m*n-1
				neighbor.add(roomNum+n);
			}
			if(roomNum-1>=((int)(roomNum/n))*n){//左边 大于等于左边线  边线（(int)（roomNum/n））*n
				neighbor.add(roomNum-1);
			}
			if(roomNum+1<((int)(roomNum/n)+1)*n){//右边 小于下行的左边线
				neighbor.add(roomNum+1);
			}

			//选择随机相邻房间
			int nearRoomIndex=r.nextInt(neighbor.size());
			int nearRoom=neighbor.get(nearRoomIndex);
			//roomNum与nearRoom是否连通
			if(dSets.find(nearRoom)==dSets.find(roomNum)){
				continue;
			}else{
				int root1=dSets.find(roomNum);
				int root2=dSets.find(nearRoom);
				dSets.union(root1, root2);
				//拆墙
				//最小的房间号
				int minRommNum=Math.min(roomNum, nearRoom);
				//墙的坐标
				int x1,y1,x2,y2;
				if(Math.abs(roomNum-nearRoom)==1){//竖墙
					if(minRommNum<n){//在第一行
						x1=(minRommNum+1+d)*rSize;//找到最大房间号定点的横坐标
					}else{
						x1=(minRommNum%n+1+d)*rSize;
					}
					y1=((int)(minRommNum/n)+d)*rSize+1;//最小房间顶点的纵坐标
					x2=x1;
					y2=((int)(minRommNum/n)+d+1)*rSize-1;//最大房间顶点纵坐标
				}else{//横墙
					if(minRommNum<n){//第一排
						x1=(minRommNum+d)*rSize+1;//最小房间的横坐标
						x2=(minRommNum+1+d)*rSize-1;//最大房间的横坐标
					}else{
						x1=(minRommNum%n+d)*rSize+1;
						x2=(minRommNum%n+1+d)*rSize-1;
					}
					y1=((int)(minRommNum/n)+1+d)*rSize;//最大房间顶点纵坐标
					y2=y1;
				}
				g.setColor(Color.white);//画白线 即插墙
				g.drawLine(x1, y1, x2, y2);
			}
		}
	}
	
	public static void main(String[] args) {
		Maze maze=new Maze();
		maze.setTitle("迷宫");
		maze.setSize(wWidth,hHeight);
		maze.setVisible(true);
		maze.setLocationRelativeTo(null);
		maze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
