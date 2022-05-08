package EX_银行家算法;

import java.util.Scanner;

public class EX_银行家算法 {
    int n , m;

    int[] Request = new int[100];
    String[] Name = new String[100];
    int[] Available = new int[100];//可利用资源向量Available。
    //最大需求矩阵Max。
    // 它定义了系统中n个进程中的每一个进程对m类资源的最大需求。
    // 如果Max［i,j］=K，则表示进程i需要Rj类资源的最大数目为K
    int[][] Max = new int[100][100] ;
    //③ 分配矩阵Allocation
    //这也是一个n×m的矩阵，它定义了系统中每一类资源当前已分配给每一进程的资源数。
    // 如果Allocation［i,j］=K，则表示进程i当前已分得Rj类资源的数目为K。
    int[][] Allocation = new int[100][100];
    //④ 需求矩阵Need
    //这也是一个n×m的矩阵，用以表示每一个进程尚需的各类资源数。
    // 如果Need［i,j］=K，则表示进程i还需要Rj类资源K个，方能完成其任务。
    //Need［i,j］=Max［i,j］-Allocation［i,j］
    int[][] Need = new int[100][100];
    Scanner sc = new Scanner(System.in);
    public void Scanne(){


        System.out.println("请输入进程数量：");
        n = sc.nextInt();
        System.out.println("请输入请求资源类个数：");
        m = sc.nextInt();

        System.out.println("请输入进程的Max矩阵：");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Max[i][j] = sc.nextInt();
            }
        }
        System.out.println("请输入进程的Allocation矩阵：");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Allocation[i][j] = sc.nextInt();
            }
        }
        System.out.println("请输入进程的Available资源：");
        for (int i = 0; i < m; i++) {
            Available[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Need[i][j] = Max[i][j] - Allocation[i][j];
            }
        }

    }
    public void ShowDate(){
        System.out.println("==========================================");//40
        System.out.println("系统目前可用的资源[Available]:");
        for (int i = 0; i < n; i++) {
            System.out.println("p" + i);
        }
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("         最大需求矩阵         已分配矩阵        需要矩阵        剩余可用矩阵");
        System.out.println("            Max            Allocation        Need           Available");
        System.out.println("-------------------------------------------------------------------------------");
        for(int i=0;i<m;i++)
        {
            System.out.print("P"+i+"         ");
            for(int j=0;j<n;j++)
            {
                System.out.print(Max[i][j]+"   ");
            }
            System.out.print("         ");
            for(int j=0;j<n;j++)
            {
                System.out.print(Allocation[i][j]+"   ");
            }
            System.out.print("         ");
            for(int j=0;j<n;j++)
            {
                System.out.print(Need[i][j]+"   ");
            }
            System.out.println();
        }

        bank();

    }

    //
    public void bank() {
        boolean flag = true;
        int a;
        System.out.println("请输入请求分配资源的进程号(0~" + (m - 1) + "):");
        a = sc.nextInt();
        System.out.println("请输入进程P" + a + "要申请的资源个数:");
        for (int i = 0; i < n; i++) {
            Request[i] = sc.nextInt();//输入需要申请的资源
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (Request[j] <= Need[i][j]) {
                    Available[j] -= Request[j];
                    Allocation[i][j] += Request[j];
                    Need[i][j] -= Request[j];
                } else {
                    flag = false;
                }
            }
        }
        //调用安全性算法
        safe();
    }

    //安全性算法
    //(1) 设置两个向量：
    //① 工作向量Work: 它表示系统可提供给进程继续运行所需的各类资源数目，
    // 它含有m个元素，在执行安全算法开始时，Work∶=Available；
    //② Finish: 它表示系统是否有足够的资源分配给进程，使之运行完成。开始时先做Finish［i］∶=false;
    // 当有足够资源分配给进程时， 再令Finish［i］∶=true。
    //(2) 从进程集合中找到一个能满足下述条件的进程：
    //① Finish［i］=false；
    //② Need［i,j］≤Work［j］； 若找到， 执行步骤(3)， 否则，执行步骤(4)。
    // 3) 当进程Pi获得资源后，可顺利执行，直至完成，并释放出分配给它的资源，故应执行：
    // Work［j］∶=work［i］+Allocation［i,j］;
    //  Finish［i］∶=;   ture;
    //  go to step （2）；
    //(4) 如果所有进程的Finish［i］=true都满足， 则表示系统处于安全状态；否则，系统处于不安全状态。
    public boolean  safe(){
        boolean flag = true;
        int[] Work = Available;//初始化work

        boolean[] Finish = new boolean[m];//初始化finish
        for (int i = 0; i < m; i++) {
            Finish[i] = false;
        }
        ///存在Finish[i] =false
        //&&Need[i][j]< Available[j]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(Finish[i] == false && Need[i][j] < Available[j]){
                    Finish[i] = true;
                    Work[j] += Allocation[i][j];
                }
            }
        }
        
        return flag;
    }
    public static void main(String[] args) {
        EX_银行家算法 EX = new EX_银行家算法();
        EX.Scanne();
        EX.ShowDate();
    }

}