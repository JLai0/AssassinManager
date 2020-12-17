import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
 * Name: Jonathan Lai
 * Due Date: November 5th 2019
 * Class: Data Structures
 * Period: 02
 * Teacher: Ms. Maloney
 * LetterInventory
 */
public class AssassinManager {

	/*
	 * AssassinManager is a class designed to work in conjunction with AssassinMain.
	 * While AssassinMain works to read a file of names, shuffle them, and proceed
	 * to create an AssassinManager object the AssassinManager object is responsible
	 * for the storage of players who are alive and dead as well as carrying out
	 * various tasks involved in playing the game such as killing a player, indicate
	 * when the game is over, stating who won, printing the existing list of players
	 * in the Kill Ring, who is dead, and more. Data fields in this class was
	 * restricted to the usage of linked list only via the provided AssassinNode
	 * method.
	 */

	private AssassinNode firstGraveyard;
	// Stores the first dead person's AssassinNode otherwise it is null
	private AssassinNode firstKillRing;
	// Stores the first person's AssassinNode in the kill ring

	// Pre: Names are nonempty strings. There will be no duplicates (ignoring
	// case).
	// Post: An AssassinManager object will be constructed consisting of all the
	// names in the passed in list in the same order. An
	// IllegalArgumentException will be thrown if the list of names is empty.
	// The data field firstKillRing will store the first AssassinNode and
	// subsequently everything that is linked to it.
	AssassinManager(List<String> names) {
		/*
		 * AssassinManger is the AssassinManager's class' constructor. It takes in a
		 * list of names which will be used to create a linked list of AssassinNodes in
		 * the same order as it appears in the list of names passed in.
		 */
		if (names.isEmpty()) {
			throw new IllegalArgumentException("The List of Names is Empty");
		}
		firstKillRing = new AssassinNode(names.get(0));
		AssassinNode current = firstKillRing;

		for (int x = 1; x < names.size(); x++) {
			AssassinNode newPlayer = new AssassinNode(names.get(x));
			current.next = newPlayer;
			current = current.next;
		}
	}

	// Pre: There will be at least one person in the kill ring
	// Post: Prints out a console statement that is indent 4 to the right in the
	// format: Person1 is stalking Person2. Where Person1 is the AssassinNode
	// whose next data field is Person2. Does not return a value only prints a
	// statement in the console.
	public void printKillRing() {
		/*
		 * printKillRing is a method that is used to print out the currently existing
		 * kill ring of players stating who is stalking who. It will not return a value.
		 * It will only print out the statements in the console.
		 */
		AssassinNode current = firstKillRing;

		while (current.next != null) {
			System.out.println("    " + current.name + " is stalking " + current.next.name);
			current = current.next;
		}
		System.out.println("    " + current.name + " is stalking " + firstKillRing.name);
	}

	// Pre: N/A
	// Post: Prints out a console statement that is indent 4 to the right in the
	// format: Person1 was killed by Person2. Where Person2 is the AssassinNode
	// whose next data field is Person1 after the kill method has been called on
	// Person2. Does not return a value only prints a
	// statement in the console.
	public void printGraveyard() {
		/*
		 * printGraveyard is a method that is used to print out the current existing
		 * list of players who have been killed in a format that indicates who they were
		 * killed by. It will not return a value. It will only print out the statements
		 * in the console.
		 */
		AssassinNode previous = null;
		AssassinNode current = firstGraveyard;

		int graveyardSize = 0;
		if (firstGraveyard != null) {
			graveyardSize = 1;
			while (current.next != null) {
				graveyardSize++;
				current = current.next;
			}
			System.out.println("    " + current.name + " was killed by " + current.killer);
			current = firstGraveyard;
			for (int x = 1; x < graveyardSize; x++) {
				previous = current;
				current = current.next;
				System.out.println("    " + previous.name + " was killed by " + previous.killer);
			}
		}

	}

	// Pre: Passed in string may be capitalized but the method will ignore cases
	// in comparing names
	// Post: A true or false boolean will be returned where true indicates the
	// player is in the kill ring and false indicates they are not
	public boolean killRingContains(String name) {
		/*
		 * killRingContains takes in a name in the form of a string in order to check
		 * the existing kill ring to see if the name given is in it. Where true means
		 * that they are and false means that they are not a player.
		 */
		AssassinNode current = firstKillRing;

		name = name.toLowerCase();
		while (current.next != null) {
			if (current.name.toLowerCase().equals(name)) {
				return true;
			}
			current = current.next;
		}
		if (current.name.toLowerCase().equals(name)) {
			return true;
		}
		return false;
	}

	// Pre: Passed in string may be capitalized but the method will ignore cases
	// in comparing names
	// Post: A true or false boolean will be returned where true indicates the
	// player is in the graveyard and false indicates they are not
	public boolean graveyardContains(String name) {
		/*
		 * graveyardContains takes in a name in the form of a string in order to check
		 * the existing graveyard to see if the name given is in it. Where true means
		 * that they are and false means that they are not a player.
		 */
		AssassinNode current = firstGraveyard;

		name = name.toLowerCase();
		if (firstGraveyard != null) {
			while (current.next != null) {
				if (current.name.toLowerCase().equals(name)) {
					return true;
				}
				current = current.next;
			}
			if (current.name.toLowerCase().equals(name)) {
				return true;
			}
		}
		return false;
	}

	// Pre: At least one name will be in the kill ring
	// Post: A true or false boolean will be returned where true indicates that
	// the game is over and false indicates that the game is ongoing
	public boolean gameOver() {
		/*
		 * gameOver checks whether or not the game is over. If there is only one person
		 * or none in the kill ring then the game is considered to be over and a true or
		 * false boolean will be returned. A true boolean indicates that the game is
		 * over and a false indicates that the game is ongoing.
		 */
		AssassinNode current = firstKillRing;

		if (current.next == null) {
			return true;
		}
		return false;
	}

	// Pre: N/A
	// Post: Returns the last player's name in the kill ring as a string. If the
	// game is not over a null value will be returned.
	public String winner() {
		/*
		 * winner uses the gameOver method to check if the game is completed and if it
		 * is it will return the last remaining player's name.
		 */
		if (gameOver()) {
			return firstKillRing.name;
		}
		return null;
	}

	// Pre: Inputed names may be capitalized by the method will ignored cases
	// when comparing names
	// Post: The killKillRing's order will be maintained only removing the
	// player that was killed and shifting each AssassinNode after the removed
	// player to the left. An IllegalArgumentException will be thrown if the
	// given name is not part of the current kill ring. An IllegalStateException
	// will be thrown if the game is already over.
	public void kill(String name) {
		/*
		 * The kill method is used to "kill" a player in the currently existing kill
		 * ring. The killed player will be removed from the kill ring linked list and
		 * all players following that player will be shifted to the left. The kill
		 * ring's order will be maintained. It will throw two exception if the given
		 * name is not part of the current kill ring or if the game is already over. The
		 * kill method is case insensitive when comparing names.
		 */
		boolean containsPerson = false;
		AssassinNode currentKillRing = firstKillRing;
		AssassinNode currentGraveyard = firstGraveyard;
		AssassinNode previousKillRing = null;
		AssassinNode lastPerson = firstKillRing;

		name = name.toLowerCase();
		while (currentKillRing.next != null) {
			currentKillRing = currentKillRing.next;
		}
		lastPerson = currentKillRing;
		currentKillRing = firstKillRing;
		while (currentKillRing.next != null && !containsPerson) {
			if (currentKillRing.name.toLowerCase().equals(name)) {
				containsPerson = true;
			} else {
				previousKillRing = currentKillRing;
				currentKillRing = currentKillRing.next;
			}
		}
		if (currentKillRing.name.toLowerCase().equals(name)) {
			containsPerson = true;
		}
		if (gameOver()) {
			throw new IllegalStateException("Game is Already Over");
		}
		if (!containsPerson) {
			throw new IllegalArgumentException("Given Name is Not Part of The Current Kill Ring");
		}
		if (firstGraveyard != null) {
			// If graveyard is empty because you cannot use the next field of graveyard if
			// graveyard is null
			while (currentGraveyard.next != null) {
				// Traverse graveyard linked list to store grab the last node
				currentGraveyard = currentGraveyard.next;
			}
			if (currentKillRing.next != null) {
				// If the person being killed is located at the end of the node
				if (previousKillRing == null) {
					// the the person is at the start of the list
					currentKillRing.killer = lastPerson.name;
					firstKillRing = currentKillRing.next;
				} else {
					// If there is a person behind the person being killed
					currentKillRing.killer = previousKillRing.name;
					previousKillRing.next = currentKillRing.next;
				}
				currentKillRing.next = null;
				// remove everything after the AssassinNode being removed
			} else {
				// if there is nothing after the person being killed
				currentKillRing.killer = previousKillRing.name;
				previousKillRing.next = null;
			}
			currentGraveyard.next = currentKillRing;
			// linked end of graveyard to the person killed
		} else {
			// if current graveyard is completely empty next method will
			// not work so an else statement is used to handle this exception
			if (currentKillRing.next != null) {
				// if no one is after the person being killed
				if (previousKillRing == null) {
					// if there is no one before the person being killed
					currentKillRing.killer = lastPerson.name;
					firstKillRing = currentKillRing.next;
				} else {
					// if there is something before thep person being killed
					currentKillRing.killer = previousKillRing.name;
					previousKillRing.next = currentKillRing.next;
				}
				currentKillRing.next = null;
				// remove everything after the AssassinNode being removed
			} else {
				// if no one is after the person being killed
				currentKillRing.killer = previousKillRing.name;
				previousKillRing.next = null;
			}
			firstGraveyard = currentKillRing;
			// firstGraveyard will equal the first person that has died
		}

	}

}
  