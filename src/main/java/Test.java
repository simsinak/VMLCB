import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.*;

/**
 * Created by sinaaskarnejad on 4/15/16.
 */
public class Test {
    public static void main(String[] args) {
     CreateGraph cg=new CreateGraph();
        //internal costs
        int[] coste=new int[]{7,3,10};
        //external costs
        int[] costi = new int[]{20,4,2};
        //connection matrix
        int[][] interactions=new int[][]{{0,1 ,1},{0,0 ,1},{0,0,0}};
        cg.graphCreator(coste , interactions , costi);
//        Node n1=new Node(12);
//        Node n2=new Node(3);
//      //  n1.testsi.add(true);
//        n1.testsi.add(n2);
//
//       // n1.selected = n1.selected && true;
//        System.out.println(n1.getselected());
//        n2.si = true;
//        System.out.println(n1.getselected());
//      //  MutableBoolean b=new MutableBoolean(true);


    }
}
