import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;


public class Main {

	public static void main(String[] args) {
		System.out.println("INSTAGRAM FOLLOWERS - UNFOLLOWERS");
		System.out.println("Give me the followers list: ");
		Path followersPath = Paths.get("followers.txt");
		
		try {
			List<String> lines = Files.readAllLines(followersPath);
			System.out.println("The file was read with success! Total lines read: "
					+ lines.size());
			for(String line: lines) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.err.println("Error reading the file");
		}
		
		System.out.println("Give me the following list: ");
		Path followingPath = Paths.get("following.txt");
		
		try {
			List<String> lines2 = Files.readAllLines(followingPath);
			System.out.println("The file was read with success! Total lines read: "
					+ lines2.size());
			for (String line: lines2) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.err.println("Error reading the file");
		}

	}

}
