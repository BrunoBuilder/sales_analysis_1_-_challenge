package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			List<Sale> list = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), 
						Integer.parseInt(fields[1]), 
						fields[2], 
						Integer.parseInt(fields[3]), 
						Double.parseDouble(fields[4])));
				
				line = br.readLine();
			}
			
			System.out.println();
			System.out.println("Top five 2016 sales of highest average price: ");
			List<Sale> top5sales2016 = list.stream()
					.filter(x -> x.getYear() == 2016)
					.sorted((s1,s2) -> s2.averagePrice().compareTo(s1.averagePrice()))
					.limit(5)
					.collect(Collectors.toList());
			
			top5sales2016.forEach(System.out::println);
			
			double totalLogan = list.stream()
					.filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
					.filter(x -> x.getSeller().equals("Logan"))
					.map(x -> x.getTotal())
					.reduce(0.0, (x,y) -> x + y);
			
			System.out.println();
			System.out.printf("Total amount sold by seller Logan in months 1 and 7 = %.2f%n", totalLogan);
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		

		sc.close();
	}

}
