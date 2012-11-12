public class SpaceGameLauncher {
	public static void main(String[] args) {
		//create game
		SpaceGame theGame=new SpaceGame(800,800);

		//add UFOs
		theGame.addUFO(UFO.SIMPLE_SAUCER);
		theGame.addUFO(UFO.SIMPLE_SAUCER);
		theGame.addUFO(UFO.SUPER_SAUCER);
		theGame.addUFO(UFO.FAST_SHIP);
		//run game
		theGame.start();
	}
}