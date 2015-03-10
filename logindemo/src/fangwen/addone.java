package fangwen;

import java.io.*;

public class addone {

	public addone() {
		try {
			String path = "/";
			File f = new File(path, "counter.txt");

			if (f.exists()) {

				FileReader fr = new FileReader(path + "counter.txt");

				BufferedReader br = new BufferedReader(fr);
				String s = br.readLine();
				int i = Integer.parseInt(s);
				i++;
				System.out.print(i);
				br.close();
				s = Integer.toString(i);

				FileWriter fw1 = new FileWriter(path + "counter.txt");
				PrintWriter pw = new PrintWriter(fw1);
				pw.println(s);
				pw.close();

			}

			else {

				f.createNewFile();
				FileWriter fw = new FileWriter(path + "counter.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("1");
				bw.flush();
				fw.close();

			}

		} catch (IOException e) {
			System.out.print(e.toString());
		}

	}
}
