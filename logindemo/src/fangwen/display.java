package fangwen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class display {
	public String[] img = new String[10];

	// 读取文件
	public void counter() {
		try {
			String path = "";
			File f = new File(path + "counter.txt");

			if (f.exists()) {
				FileReader fr = new FileReader(path + "counter.txt");
				BufferedReader br = new BufferedReader(fr);
				String s = br.readLine();
				int i = Integer.parseInt(s);
				int st = 10;
				int j = 0;
				while (j <= 9) {
					img[j] = Integer.toString(i % st);
					img[j] = img[j] + ".jpg";
					img[j] = "images/digit/" + img[j];
					img[j] = "<img src=" + img[j] + ">";
					img[j] += "</img>";
					i /= 10;
					j++;
				}
			} else {
				System.out.print("counter.txt文件不存在！");
			}

		} catch (IOException e) {
			System.out.print(e.toString());
		}
	}

}
