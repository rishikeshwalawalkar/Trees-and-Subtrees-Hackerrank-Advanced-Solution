import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
class SubTree{
	static int[][] tree;
	static String max="";
	static ArrayList<Integer> al;
    public static void main (String[] ar) throws Exception{
		FileInputStream fi =new FileInputStream(ar[0]);
		Scanner sc=new Scanner(fi);
		int N=sc.nextInt();
		tree=new int[N][N];
		for(int i=1;i<N;i++){
			int x=sc.nextInt();
			int y=sc.nextInt();
				tree[y-1][x-1]=-1;                     
				tree[x-1][y-1]=-1;
		}
		al=new ArrayList<Integer>(N);
		al.add(1);                          // this is know if the node exist or is a new node, new node are added to the arraylist
		createTree(1,N);
		
		//System.out.println("Intial Tree in Array");
		//renderTree(tree,N);
		int noCmds = sc.nextInt();sc.nextLine();
		for(int j=0;j<noCmds;j++){
			String[] cmdLine = sc.nextLine().split(" ");
			if(cmdLine[0].equals("max")){
				int node1=Integer.parseInt(cmdLine[1]);
				int node2=Integer.parseInt(cmdLine[2]);
				max="";
				int maxIndex=0;
				if(node1==node2){
					System.out.println(tree[node1-1][node1-1]);
				}else{
					if(al.indexOf(node1)>al.indexOf(node2)){
						maxIndex= al.indexOf(node1);
					}else{
						maxIndex= al.indexOf(node2);	
					}
					//System.out.println(maxIndex);
					//System.out.println(((Integer)al.get(maxIndex)).intValue());
					int currentMax=tree[node1-1][node1-1];
					ArrayList<Integer> tracePath=new ArrayList<Integer>();
					tracePath.add(node1);
					//System.out.println(node1+" -> "+currentMax);
					getMax(node1,node2,currentMax,tracePath,maxIndex);
					System.out.println(max);
				}
		
			}else if(cmdLine[0].equals("add")){
				int node=Integer.parseInt(cmdLine[1]);
				int addInt=Integer.parseInt(cmdLine[2]);
				tree[node-1][node-1]+=addInt;
				addToNodeAndChildren(node,addInt,N);                   // tree[node-1][node-1] is used to store value of that node
				//System.out.println("");                     //to be removed
				//renderTree(tree,N);							//to be removed
			}
		}
		//renderTree(tree,N);
    }
	
	static void createTree(int node, int N){
		for(int i=1;i<=N;i++){
			if(al.indexOf(i)==-1 && tree[node-1][i-1]==-1){
				al.add(i);
				tree[node-1][i-1]=node;
				tree[i-1][node-1]=node;	
				createTree(i,N);
			}
		}
		
	}
	
	
	static void addToNodeAndChildren(int node,int addInt,int N){
		for(int i=1;i<=N;i++){
			if(tree[node-1][i-1]==node && node!=i ){
				tree[i-1][i-1]+=addInt;
				addToNodeAndChildren(i,addInt,N);
			}
		}
		//System.out.print(node+"  ");						//to be removed
	}
	

	static void getMax(int currentNode, int findNode, int currentMax,ArrayList<Integer> tracePath, int maxIndex){
		if(tree[currentNode-1][findNode-1]>0){
			currentMax = currentMax>tree[findNode-1][findNode-1]?currentMax:tree[findNode-1][findNode-1];
			max=""+currentMax;
			//System.out.println(findNode+" => "+tree[findNode-1][findNode-1]);
			//System.out.println("Max => "+currentMax);
			
		}else{		
			for(int i=0;i<maxIndex;i++){
				if(max.equals("")){
					int no = ((Integer)al.get(i)).intValue();
					if( tracePath.indexOf(no)==-1 && tree[currentNode-1][no-1]>0 ){
					    tracePath.add(no);
						currentMax=currentMax>tree[no-1][no-1]?currentMax:tree[no-1][no-1];
				//		System.out.println(no+" -> "+currentMax);
						getMax(no,findNode,currentMax,tracePath,maxIndex);
					}	
				}else{
					break;
				}	
			}
		}
	}
	
	
	
	static void renderTree(int[][] tree, int N){
		for( int i=0;i<N; i++){
			for( int j=0;j<N; j++){
				System.out.print(tree[i][j]+" ");
			}			
			System.out.println("");
		}
		
	}
}