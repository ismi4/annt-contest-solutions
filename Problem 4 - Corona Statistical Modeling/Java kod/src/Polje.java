import java.util.ArrayList;

public class Polje {
	
	double x;
	double y;
	ArrayList<Double> sanse;
	int brojOboljelih;
	double sansa;
	
	Polje(double x, double y){
		this.x = x;
		this.y = y;
		this.sanse=new ArrayList<Double>();
	}
	
	Polje(){
		
	}
	
	@Override
	public String toString() {
		return("["+x+", "+y+"]");
	}
	

}
