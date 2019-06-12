package game;

public class Rule extends Chess{

	public Rule() {}
	
	private static boolean blocked = false;
	
	public static Boolean CanGo(int fx,int fy,int tx,int ty) {
		if (Chess.getName(fx,fy).equals("CANNON")) {
			blocked = false;
			//System.out.println("Cannon");
			//System.out.println("target "+ Chess.getPos(tx,ty));
			if (fx == tx) { //if x coordinate is same as before
				//System.out.println("same x");
				if (fy > ty) { //if from is greater than to position
					//System.out.println("Y down");
					for (int i = fy-1; i > ty-1; i--) {
						if(blocked) { //blocked = true = 1
							if(i == ty && Chess.getPos(tx,ty) != 0 && Chess.getPos(tx,ty) != Chess.getPos(fx,fy)) {
								//System.out.println("from "+Chess.getPos(fx,fy));
								//System.out.println("Can Eat");
								return true;
							}
						}
						System.out.print(Chess.getPos(fx,i)+" "); //to check if some other goti is there or  not , if there we switch the blocked to true.
						if (Chess.getPos(fx,i) != 0) {
							if (blocked == false) {
								blocked = true;
								//System.out.println("Blocked");
							} 
							else {
								//System.out.println("Double Blocked");
								//System.out.println("Can't move");
								return false;
							}
						}
						
					}
				}
				else {
					//System.out.println("Y up");
					for (int i = fy+1; i < ty+1; i++) {
						if(blocked) {  //because can only eat if someone is in btw.
							if(i == ty && Chess.getPos(tx,ty) != 0 && Chess.getPos(tx,ty) != Chess.getPos(fx,fy)) {
								//System.out.println("from "+Chess.getPos(fx,fy));
								//System.out.println("Can Eat");
								return true;
							}
						}
						//System.out.print(Chess.getPos(fx,i)+" ");
						if (Chess.getPos(fx,i) != 0) {
							if (blocked == false) {
								blocked = true;
								//System.out.println("Blocked");
							} 
							else {
								//System.out.println("Double Blocked");
								//System.out.println("Can't move");
								return false;
							}
						}
						
					}
				}
				if(blocked) {
					//System.out.println("Can't move");
					return false;
					}
				if(!blocked) {
					//System.out.println("Can move");
					return true;
					}
			}
			else if (fy == ty) {
				//System.out.println("same y");
				if (fx > tx) {
					//System.out.println("X down");
					for (int i = fx-1; i > tx-1; i--) {
						if(blocked) {
							if(i == tx && Chess.getPos(tx,fy) != 0 && Chess.getPos(tx,ty) != Chess.getPos(fx,fy)) {
								//System.out.println("from "+Chess.getPos(fx,fy));
								//System.out.println("Can Eat");
								return true;
							}
						}
						//System.out.print(Chess.getPos(i,fy)+" ");
						if (Chess.getPos(i,fy) != 0) {
							if (blocked == false) {
								blocked = true;
								//System.out.println("Blocked");
							} 
							else {
								//System.out.println("Double Blocked");
								//System.out.println("Can't move");
								return false;
							}
						}
						
					}
				}
				else {
					//System.out.println("X up");
					for (int i = fx+1; i < tx+1; i++) {
						if(blocked) {
							if(i == tx && Chess.getPos(tx,fy) != 0 && Chess.getPos(tx,ty) != Chess.getPos(fx,fy)) {
								//System.out.println("from "+Chess.getPos(fx,fy));
								//System.out.println("Can Eat");
								return true;
							}
						}
						//System.out.print(Chess.getPos(i,fy)+" ");
						if (Chess.getPos(i,fy) != 0) {
							if (blocked == false) {
								blocked = true;
								//System.out.println("Blocked");
							} 
							else {
								//System.out.println("Double Blocked");
								//System.out.println("Can't move");
								return false;
							}
						}
						
					}
				}
				if(blocked) {
					//System.out.println("Can't move");
					return false;
					}
				if(!blocked) {
					//System.out.println("Can move");
					return true;
					}
			}
			else {
				//System.out.println("Not in same line");
				//System.out.println("Can't move");
				return false;
			}
		}
		//------------------------------------
		else if(Chess.getName(fx, fy)=="KNIGHT") {
			
			
			if(Chess.getPos(tx,ty)!=Chess.getPos(fx, fy)) {
				
				if((ty-fy)==-2) {
					if(Math.abs(tx-fx)==1) {
						if(Chess.getPos(fx, fy-1)==0)
							return true;
					}
					
				}
				else if((ty-fy)==2){
					if(Math.abs(tx-fx)==1) {
						if(Chess.getPos(fx, fy+1)==0)
							return true;
					}
					
				}
				else if((tx-fx)==2){
					if(Math.abs(ty-fy)==1) {
						if(Chess.getPos(fx+1, fy)==0)
							return true;
					}
					
				}
				else if((tx-fx)==-2){
					if(Math.abs(ty-fy)==1) {
						if(Chess.getPos(fx-1, fy)==0)
							return true;
					}
					
				}
				
			}
		}
		
		else if(Chess.getName(fx, fy)=="SOLDIER") {
			
			if(Chess.getPos(tx,ty)!=Chess.getPos(fx, fy)) 
			{
				if(Chess.getPos(fx,fy)==1) {
					if(fy<5 && (fx-tx)==0 && (ty-fy)==1)
						return true;
					else if(fy>=5 &&fy<=9 && Math.abs(fx-tx)==1 && (ty-fy)==0)
						return true;
					else if(fy>=5 &&fy<=9 && Math.abs(fx-tx)==0 && (ty-fy)==1)
						return true;
				}
				else if(Chess.getPos(fx,fy)==2) {
					if(fy>=5 && (fx-tx)==0 && (ty-fy)==-1)
						return true;
					else if(fy<5 && Math.abs(fx-tx)==1 && (ty-fy)==0)
						return true;
					else if(fy<5 && Math.abs(fx-tx)==0 && (ty-fy)==-1)
						return true;
					
				}	
			}
						
		}
		else if (Chess.getName(fx, fy).equals("ROOK")) {
			blocked = false;
			//System.out.println("Rook");
			//System.out.println("target "+Chess.getPos(tx,ty));
			if (fx == tx) {
				//System.out.println("same x");
				if (fy > ty) {
					//System.out.println("Y down");
					for (int i = fy-1; i > ty-1; i--) {
						//System.out.print(Chess.getPos(fx,i)+" ");
						if (Chess.getPos(fx,i) != 0 && blocked == false) {
							blocked = true;
							//System.out.println("Blocked");
							if(i == ty && Chess.getPos(tx,ty) != Chess.getPos(fx,fy)) {
								//System.out.println("from "+Chess.getPos(fx,fy));
								//System.out.println("Can Eat");
								return true;
							}
						}
						
					}
				}
				else {
					//System.out.println("Y up");
					for (int i = fy+1; i < ty+1; i++) {
						
						//System.out.print(Chess.getPos(fx,i)+" ");
						if (Chess.getPos(fx,i) != 0 && blocked == false) {
							blocked = true;
							//System.out.println("Blocked");
							if(i == ty && Chess.getPos(tx,ty) != Chess.getPos(fx,fy)) {
								//System.out.println("from "+Chess.getPos(fx,fy));
								//System.out.println("Can Eat");
								return true;
							}
						}
						
					}
				}
				if(blocked) {
					//System.out.println("Can't move");
					return false;
					}
				if(!blocked) {
					//System.out.println("Can move");
					return true;
					}
			}
			else if (fy == ty) {
				//System.out.println("same y");
				if (fx > tx) {
					//System.out.println("X down");
					for (int i = fx-1; i > tx-1; i--) {
						//System.out.print(Chess.getPos(i,fy)+" ");
						if (Chess.getPos(i,fy) != 0 && blocked == false) {
							blocked = true;
							//System.out.println("Blocked");
							if(i == tx && Chess.getPos(tx,ty) != Chess.getPos(fx,fy)) {
								//System.out.println("from "+Chess.getPos(fx,fy));
								//System.out.println("Can Eat");
								return true;
							}
						}
						
					}
				}
				else {
					//System.out.println("X up");
					for (int i = fx+1; i < tx+1; i++) {
						//System.out.print(Chess.getPos(i,fy)+" ");
						if (Chess.getPos(i,fy) != 0 && blocked == false) {
							blocked = true;
							//System.out.println("Blocked");
							if(i == tx && Chess.getPos(tx,ty) != Chess.getPos(fx,fy)) {
								//System.out.println("from "+Chess.getPos(fx,fy));
								//System.out.println("Can Eat");
								return true;
							}
						}
						
					}
				}
				if(blocked) {
					//System.out.println("Can't move");
					return false;
					}
				if(!blocked) {
					//System.out.println("Can move");
					return true;
					}
			}
			else {
				//System.out.println("Not in same line");
				//System.out.println("Can't move");
				return false;
			}
		}

      //------------------------------------
		else if(Chess.getName(fx, fy)=="ELEPHANT") {	
			if(Chess.getPos(tx,ty)!=Chess.getPos(fx, fy)) 
		{
				  if(Chess.getPos(fx,fy)== 1) { //red
					if(fy < 5 && ty <= 5 && (ty-fy) == 2) { //go forward
						if(tx - fx == - 2) {
							if(Chess.getPos(fx-1 , fy+1) == 0)
								return true;
						}
						
						else if(tx - fx == 2) {
							if(Chess.getPos(fx+1 , fy+1) == 0)
								return true;
						}			
					}
					
					else if(fy < 5 && ty <= 5 && (ty-fy) == -2) { //go backward
						if(tx - fx == - 2) {
							if(Chess.getPos(fx-1 , fy-1) == 0)
								return true;
						}
						
						else if(tx - fx == 2) {
							if(Chess.getPos(fx+1 , fy-1) == 0)
								return true;
						}	
						
					}
				}

							
				else if(Chess.getPos(fx,fy)== 2) { //black
				if(fy >= 5 && ty >=5 && (ty-fy) == -2) { //go forward
					if(tx - fx == - 2) {
						if(Chess.getPos(fx-1 , fy-1) == 0)
							return true;
					}
					
					else if(tx - fx == 2) {
						if(Chess.getPos(fx+1 , fy-1) == 0)
							return true;
					}			
				}

				else if(fy >= 5 && ty >= 5 && (ty-fy) == 2) { //go backward
					if(tx - fx == - 2) {
						if(Chess.getPos(fx-1 , fy+1) == 0)
							return true;
					}
					
					else if(tx - fx == 2) {
						if(Chess.getPos(fx+1 , fy+1) == 0)
							return true;
					}	
					
				}
			}
		}
	}
		
		 //------------------------------------
else if(Chess.getName(fx,fy)=="ADVISOR") {
			
			if(Chess.getPos(tx,ty)!=Chess.getPos(fx, fy)) 
			{
				  if(Chess.getPos(fx,fy)== 1) {
					  if(fy <=2 && fx >=3 && fx <= 5) {
						  if(ty <=2 && tx >=3 && tx <= 5) {
							  if(Math.abs(ty - fy) == 1 && Math.abs(tx -fx) == 1)
							  return true; 
						  }				  
				  }
		       }
						  				  
				  else if(Chess.getPos(fx,fy)== 2) {
					  if(fy >=7 && fx >=3 && fx <= 5) {
						  if(ty >=7 && tx >=3 && tx <= 5) {
							  if(Math.abs(ty - fy) == 1 && Math.abs(tx -fx) == 1)
							  return true; 
						  }				  
				  }
		       }
			}
}
		//------------------------------------	


else if(Chess.getName(fx,fy)=="GENERAL") {
			
			if(Chess.getPos(tx,ty)!=Chess.getPos(fx, fy)) 
			{
				  if(Chess.getPos(fx,fy)== 1) {
					  if(fy <=2 && fx >=3 && fx <= 5) {
						  if(ty <=2 && tx >=3 && tx <= 5) {
							  if((fx-tx)==0 && Math.abs(ty-fy)==1)
									return true;
							  else if((fy-ty)==0 && Math.abs(tx-fx)==1)
								  return true;
						  }				  
				  }
		       }
						  				  
				  else if(Chess.getPos(fx,fy)== 2) {
					  if(fy >=7 && fx >=3 && fx <= 5) {
						  if(ty >=7 && tx >=3 && tx <= 5) {
							  if((fx-tx)==0 && Math.abs(ty-fy)== 1)
									return true;
							  else if((fy-ty)==0 && Math.abs(tx-fx)==1)
								  return true;
						  }				  
				  }
		       }
			}
}

		//------------------------------------	
     	System.out.println("error");
		return false;
	}
}



