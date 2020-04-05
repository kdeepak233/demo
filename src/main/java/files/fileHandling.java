package files;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class fileHandling {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Parent File Location");
		String parentLocation = scanner.nextLine();
		System.out.println("Enter From date in given Format 'yyyy-MM-dd':");
		String fromDate = scanner.nextLine();
		System.out.println("Enter to date in given Format 'yyyy-MM-dd':");
		String toDate = scanner.nextLine();
		list = findModified(new File(parentLocation), fromDate, toDate);
		System.out.println("Enter Location to create new Folder");
		String newLocation = scanner.nextLine();
		copyFolder(parentLocation, list, newLocation);
		System.out.println("Files copied to :" + newLocation);
		scanner.close();
	}

	private static void copyFolder(String parentLocation, ArrayList<String> list, String newLocation) {

		System.out.println(list);
		for (String data : list) {
			try {
				File dest = new File(newLocation + "//" + data);
				FileUtils.copyFile(new File(parentLocation + "//" + data), dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static ArrayList<String> findModified(File parentLocation, String fromDate, String toDate) {
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<String> op = new ArrayList<String>();
		int len = (int) parentLocation.getPath().length();
		System.out.println("len" + len);
		files = listFiles(files, parentLocation);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (String file : files) {
			Date d = new Date(new File(file).lastModified());
			String current = sdf.format(d);
			if (fromDate.compareTo(current) < 0 && toDate.compareTo(current) >= 0)
				op.add(file.substring(len));
		}
		return op;
	}

	private static ArrayList<String> listFiles(ArrayList<String> files, File parentLocation) {
		for (File file : parentLocation.listFiles()) {
			if (file.isDirectory()) {
				listFiles(files, file);
			} else {
				files.add(file.getPath());
			}
		}
		return files;
	}
}
