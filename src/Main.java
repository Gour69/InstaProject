import java.io.*;
import java.util.HashSet;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;



public class Main {

	public static void main(String[] args) {
		System.out.println("INSTAGRAM FOLLOWERS - UNFOLLOWERS");
//		System.out.println("Give me the followers list: ");
//		Path followersPath = Paths.get("followers.txt");
		
		File followers = new File("followers.txt");
		File following = new File("following.txt");
		File unf = new File("unf.txt");
		
		HashSet<String> followerSet = new HashSet<>();
		
		
		//Followers - Unfollowers List
		//If I follow someone and they dont follow me back
		//They appear in this list 
		try {
			Scanner followerScanner = new Scanner(followers);
			while(followerScanner.hasNextLine()) {
				followerSet.add(followerScanner.nextLine().trim());
			}
			followerScanner.close();
			FileWriter unfWriter = new FileWriter(unf);
			BufferedWriter writer = new BufferedWriter(unfWriter);
			Scanner followingScanner = new Scanner(following);
			
			while(followingScanner.hasNextLine()) {
				String acc = followingScanner.nextLine().trim();
				if(!followerSet.contains(acc)) {
					writer.write("@" + acc + System.lineSeparator());
				}
			}
			followingScanner.close();
			writer.close();
			unfWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
}

}
