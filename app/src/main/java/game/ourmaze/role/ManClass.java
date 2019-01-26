package game.ourmaze.role;

import game.ourmaze.Data;
import game.ourmaze.Function;
import game.ourmaze.Init;

public class ManClass {

	/*---------------------- Man-------------------------*/
	public static class Man {
		/* charecter */
		private final static int Man1=1201;
		/* members */
		/**
		 *coordination
 		 */
		public int x,y;
		public int view,direct,win,level;
		public int character;
		/**
		 * property
 		 */
		public int wisedom,blood,defence,beat;
		/**
		 * the quantity of each tool
 		 */
		public int man_tool[]=new int[15];
		public Man(int sx,int sy) { 
			x=sx; y=sy;
		    view=3; direct= Data.man_front; win=0; level=0;
		    character=Man1;
		    wisedom=Data.wisedom_t; blood=Data.MaxBlood[level]; beat=Data.beat_t; defence=Data.defence_t;
		    Data.Blood=Data.MaxBlood[level]; 
			// the initial quantity of each tool is 0
			for(int i=0;i<15;i++){
				if(i==7|i==2) {
					man_tool[i] = 0;
				} else {
					man_tool[i]=1;
				}
			}
		}

		public boolean move(int direct){
			if(Data.maze[ x+Data.drct[direct][0] ][ y+Data.drct[direct][1] ]!=0){
				Data.num++;
				x+=Data.drct[direct][0]; y+=Data.drct[direct][1]; this.direct =direct;
				Init.disfog(x,y,view);
				if(Data.mon[x][y]!=-1){
					Data.stop_event=true;
					Function.fight(Data.mon[x][y]);
				}

				return true;
			}
			return false;
		}
	}
	public static Man man=new Man(2,2);

}
