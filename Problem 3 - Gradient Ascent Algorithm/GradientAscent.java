//Gradient "ascent" metoda

public class Main {

	//Pocetne vrijednosti tacaka
	static double x1 = 500, x2 = 500, x3 = 500, x4 = 500, x5 = 500;
	
	//Broj iteracija
	static int iterations = 10;
	
	//Korak rasta - za koliko povecavati x-ove shodno trenutnoj vrijednosti komponente gradijenta
	//Odredio sam ga posmatrajuci vrijednost gradijenta, priblizavajuci ga 0, kako petlja zavrsava posljednju iteraciju
	static double s = 0.38;
	
	
	public static void main(String[] args) {
		
		for (int i = 0; i < iterations; i++) {
			
			//iskomentarisati da se prati vrijednost gradijenta, pomocu ove vrijednosti odredio sam korak rasta, tako da tezi nuli pri kraju posljednje iteracije
			//System.out.println(Math.pow(Math.pow(((20/(x1+2*x2+3*x3))-2*(x1-6)), 2) + Math.pow((-((5*Math.exp(x2))/(Math.exp(x2)+Math.exp(x5)))+(40/(x1+2*x2+3*x3))-(2*(x2-5))), 2)+ Math.pow(((60/(x1+2*x2+3*x3))-2*(x3-4)), 2)+Math.pow((-2*(x4-3)), 2)+Math.pow((-((5*Math.exp(x5))/(Math.exp(x2)+Math.exp(x5)))-(2*(x5-6))), 2), 0.5));
			
			//korak rasta mnozi trenutnu vrijednost xn komponente gradijenta, te toliko povecavamo vrijednosti xn
			x1 += s*((20/(x1+2*x2+3*x3))-2*(x1-6));
			x2 += s*(-((5*Math.exp(x2))/(Math.exp(x2)+Math.exp(x5)))+(40/(x1+2*x2+3*x3))-(2*(x2-5)));
			x3 += s*((60/(x1+2*x2+3*x3))-2*(x3-4));
			x4 += s*(-2*(x4-3));
			x5 += s*(-((5*Math.exp(x5))/(Math.exp(x2)+Math.exp(x5)))-(2*(x5-6)));
			
		}
		
		System.out.println("x1: "+x1 + "\nx2: " + x2 + "\nx3: " + x3 + "\nx4: " + x4 + "\nx5: " + x5);
		
		double max = 20*Math.log(x1+2*x2+3*x3)-5*Math.log(Math.exp(x2)+Math.exp(x5))-Math.pow((x1-6),2)-Math.pow((x2-5),2)-Math.pow((x3-4),2)-Math.pow((x4-3),2)-Math.pow((x5-6),2);

		System.out.println("\nf: "+max);
		

	}

}
