import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {

	static ArrayList<Double> udaljenosti = new ArrayList<Double>();
	static int korak = 10;
	static ArrayList<Double> procentiPoRadijusima = new ArrayList<Double>();
	static ArrayList<Double> procentiPoRadijusimaPrilagodjeni = new ArrayList<Double>();
	static double max, maxUdaljenost;
	static Polje[][] polja = new Polje[50][50];
	static ArrayList<Zarazeni> koordinateZarazenih = new ArrayList<Zarazeni>();
	
	
	public static void racunajUdaljenosti() throws FileNotFoundException {
		
		File file = new File("izvor_oboljeli_podaci.txt");
		
		Scanner sc = new Scanner(file);
		
		while(sc.hasNext()) {
			
			double x_izvor = sc.nextDouble();
			double y_izvor = sc.nextDouble();
			double x_oboljeli = sc.nextDouble();
			double y_oboljeli = sc.nextDouble();
			
			double dx = x_izvor-x_oboljeli;
			double dy = y_izvor-y_oboljeli;
			
			double r = Math.sqrt(Math.abs(Math.pow(dx, 2)+Math.pow(dy, 2)));
			
	
			udaljenosti.add(r);

		}
		
		maxUdaljenost = udaljenosti.get(0);
		
		for (int i = 1; i < udaljenosti.size(); i++)
			if(udaljenosti.get(i)>=maxUdaljenost)
				maxUdaljenost = udaljenosti.get(i);
	
		sc.close();
		
	}
	
	
	public static void racunajProcente() throws FileNotFoundException {
		
		
		for (int i = 0; i <= 2000; i+=korak) {
			
			int brojZarazenih = 0;
			
			for (int ii = 0; ii < udaljenosti.size(); ii++)
				if (udaljenosti.get(ii)<=i && udaljenosti.get(ii)>=(i-10))
					brojZarazenih++;
			
			double curr = brojZarazenih;
	
			curr /= udaljenosti.size();
			curr *= 100;
			
			procentiPoRadijusima.add(curr);
			
		}
		
	}
	

	public static void prilagodiProcente() throws FileNotFoundException {
	
		File file = new File("procentiData.txt");
		PrintWriter pw = new PrintWriter(file);
		
		max = procentiPoRadijusima.get(0);
		
		for (Double d: procentiPoRadijusima)
			if(d >= max)
				max = d;
		
		for (Double d: procentiPoRadijusima) 
			procentiPoRadijusimaPrilagodjeni.add(100*d/max);
		
		for (Double d: procentiPoRadijusimaPrilagodjeni)
			pw.write(Double.toString(d)+",\n");
					
		pw.close();
		
	}
	
	

	public static void populirajPolja() {
		
		for (int red = 0; red<50; red++) 
			for (int kolona = 0; kolona<50; kolona++) 
				polja[red][kolona] = new Polje(kolona-20, 49-red-20);
			
	}
	
	public static void procitajZarazene() throws FileNotFoundException {
		
		File file = new File("oboljeli_podaci.txt");
		Scanner sc = new Scanner(file);
		
		
		while(sc.hasNext()) {
			
			double x = sc.nextDouble();
			double y = sc.nextDouble();
			
			koordinateZarazenih.add(new Zarazeni(x, y));
			
			
		}
		
		
		sc.close();
	}
	
	public static void racunajPojedinacneSanse() {
		
		
		for (int red = 0; red < 50; red++)
			for (int kolona = 0; kolona < 50; kolona++) 
				
				for (Zarazeni z: koordinateZarazenih) {
					
					double dx = polja[red][kolona].x-z.x;
					double dy =  polja[red][kolona].y-z.y;
					
					double udaljenost = Math.sqrt(Math.abs(Math.pow(dx, 2)+Math.pow(dy, 2)));
					
					double sansa = udaljenost*max/maxUdaljenost;
					
					polja[red][kolona].sanse.add(sansa);
					
				}
				
			
		
	}
	
	public static void racunajSanse() {
		
		for(int red = 0; red < 50; red++)
			for (int kolona = 0; kolona < 50; kolona++) {
				
				double sanse = 0;
				
				for (Double d: polja[red][kolona].sanse) 
					sanse+=d;
				
				sanse /= (polja[red][kolona].sanse.size());
				polja[red][kolona].sansa=sanse;
				
				
			}
		
	}
	
	public static void zapisiSanse() throws FileNotFoundException {
	
		PrintWriter pw = new PrintWriter(new File("sanse.txt"));
		
		String zapis = "";
		
		for (int i = 0; i < 50; i++) {
		for(int k = 0; k < 50; k++)
			zapis+=polja[i][k].sansa+"  ";
		zapis+="\n";
			
		}
		
		pw.write(zapis);
		pw.close();
			
	
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		
		//metoda koja racuna udaljenosti izmedju izvora i oboljelih te ih zapise u listu udaljenosti
		racunajUdaljenosti();
		
		//metoda koja racuna procente zarazenih po radijusima (varijabla korak odredjuje velicinu radijusa, postavljena na 10)
		racunajProcente();
		
		//metoda koja postavlja proporciju da maksimalna sansa u procentimaPoRadijusu odgovara 100% -> zapise podatke u procentiData, te ce taj fajl biti koristen za crtanje grafa pomocu JaveScript
		prilagodiProcente();
		
		//populira 2D niz poljima, koji su containeri za varijable sadrzane u klasi Polje. Koordinatni pocetak postavljen u centar kvadrata 50x50
		populirajPolja();
		
		//populira listu zarazenih koordinatama x i y koje su sadrzane u fajlu oboljeli_podaci
		procitajZarazene();
		
		//koristeci se proporcijom iz 1. dijela (maxUdaljenosti odgovara max procenat), dodaje sanse da super-kliconosa bude u odredjenom polju, zahvaljujuci pojedinacnim zarazenim
		racunajPojedinacneSanse();
		
		//uzima srednju sansu na svakom polju
		racunajSanse();
		
		//zapisuje sanse u fajl "sanse.txt"
		zapisiSanse();
			
			}
		
		
		

	}
	

